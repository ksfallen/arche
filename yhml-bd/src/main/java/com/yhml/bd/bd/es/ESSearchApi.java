package com.yhml.bd.bd.es;

import java.io.Closeable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import com.simple.common.es.enums.CompareOperation;
import com.simple.common.es.annotation.RangeCompare;
import com.simple.common.es.annotation.SearchInfo;
import com.simple.common.es.model.SearchCondition;
import com.simple.common.es.model.SearchQuery;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import static org.elasticsearch.common.xcontent.XContentType.JSON;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;

/**
 * @author: Jfeng
 * @date: 2018/7/5
 */

@Service
@Slf4j
public class ESSearchApi implements Closeable {

    @Setter
    private ESConfig config;

    private TransportClient client;

    private IndicesAdminClient adminClient;

    public ESSearchApi(ESConfig config) {
        this.config = config;
        this.client = ESClientFactory.build(config).getClient();
        this.adminClient = client.admin().indices();
    }

    private boolean isExists(String index) {
        return adminClient != null && adminClient.prepareExists(index).get().isExists();
    }

    public boolean createIndex(String index, String type, XContentBuilder source) {
        return !isExists(index) && adminClient.prepareCreate(index).addMapping(type, source).setSettings(settings()).get().isAcknowledged();
    }

    /**
     * 删除索引
     *
     * @param index
     * @return
     */
    public boolean deleteIndex(String index) {
        return isExists(index) && adminClient.prepareDelete(index).get().isAcknowledged();
    }

    /**
     * @param index
     * @param type
     * @param map   key:value [id:json]
     * @return
     */
    public int bulkIndex(String index, String type, Map<String, String> map) {
        log.info("bulkIndex total:{}", map.size());

        BulkRequestBuilder builder = client.prepareBulk();
        map.forEach((id, json) -> builder.add(new UpdateRequest(index, type, id).doc(json, JSON).docAsUpsert(true)));

        // ListenableActionFuture<BulkResponse> future = builder.execute();
        // future.addListener(new ActionListener<BulkResponse>() {
        //     @Override
        //     public void onResponse(BulkResponse responses) {
        //         if (responses.hasFailures()) {
        //             log.error("bulkIndex error {}", responses.buildFailureMessage());
        //         }
        //         log.info("bulkIndex total:{} ok", responses.getItems().length);
        //     }
        //
        //     @Override
        //     public void onFailure(Exception e) {
        //
        //     }
        // });

        BulkResponse responses = builder.get();
        if (responses.hasFailures()) {
            log.error("bulkIndex error {}", responses.buildFailureMessage());
        }

        log.info("bulkIndex total:{} ok", responses.getItems().length);

        return responses.getItems().length;
    }

    public void index(String index, String type, String id, String source) {
        client.prepareUpdate(index, type, id).setDoc(source, JSON).setDocAsUpsert(true).execute();
    }

    public String search(String index, String type, String id) {
        GetResponse fields = client.prepareGet(index, type, id).get();
        return fields.isExists() ? fields.getSourceAsString() : "";
    }

    /**
     * 删除文档
     */
    public void delete(String index, String type, String id) {
        client.prepareDelete(index, type, id).execute();
    }

    // @formatter:off
    public SearchResponse search(SearchQuery query, String index, String types) {
        SearchRequestBuilder builder = client.prepareSearch(index)
                .setTypes(types)
                .setQuery(queryBuilder(query.getCondition()))
                .setFrom((query.getPageNo() - 1) * query.getPageSize())
                .setSize(query.getPageSize());

        //添加排序模式
        if (null != query.getFieldSort()) {
            SortOrder sortOrder = query.getSortOrder();
            FieldSortBuilder sortBuilder = SortBuilders.fieldSort(query.getFieldSort().getField()).order(sortOrder).missing("_last");
            builder.addSort(sortBuilder);
        }

        return builder.get();
    }

    public boolean updateMapping(String index, String type, XContentBuilder soruce) {
        return isExists(index) && adminClient.preparePutMapping(index).setType(type).setSource(soruce).get().isAcknowledged();
    }

    public boolean updateMapping(String index, String type, String soruce) {
        return isExists(index) && adminClient.preparePutMapping(index).setType(type).setSource(soruce, JSON).get().isAcknowledged();
    }

    public void updateSettings(String index, String json) {
        if (!isExists(index)) {
            return;
        }
        try {
            adminClient.prepareClose(index).get();
            adminClient.prepareUpdateSettings(index).setSettings(json, JSON).get();
        } finally {
            adminClient.prepareOpen(index).execute();
        }
    }

    protected Settings settings() {
        return Settings.builder().put("number_of_shards", config.getShards()).put("number_of_replicas", config.getReplicas()).build();
    }

    @Override
    public void close() {
        if (client != null) {
            client.close();
        }
    }

    private QueryBuilder queryBuilder(SearchCondition condition) {
        if (condition == null) {
            return QueryBuilders.matchAllQuery();
        }

        BoolQueryBuilder bool = QueryBuilders.boolQuery();

        for (Field field : condition.getClass().getDeclaredFields()) {
            if (!Modifier.isPublic(field.getModifiers())) {
                field.setAccessible(true);
            }

            Object value = null;
            try {
                value = field.get(condition);
            } catch (IllegalAccessException ignored) {
                continue;
            }
            if (null == value) {
                continue;
            }

            SearchInfo searchInfo = field.getAnnotation(SearchInfo.class);
            if (searchInfo == null) {
                continue;
            }

            String queryName =  field.getName();
            QueryBuilder queryBuilder = getQueryBuilder(searchInfo, queryName, value);

            if (queryBuilder != null) {
                if (searchInfo.isFilter()) {
                    bool.filter(queryBuilder);
                } else {
                    bool.must(queryBuilder);
                }
            }
        }

        return bool.hasClauses() ? bool : QueryBuilders.matchAllQuery();
    }

    private QueryBuilder getQueryBuilder(SearchInfo searchInfo, String queryName, Object value) {

        QueryBuilder queryBuilder = null;

        switch (searchInfo.method()) {
            case MATCH:
                queryBuilder = QueryBuilders.matchQuery(queryName, value);
                break;
            case TERM:
                queryBuilder = QueryBuilders.termQuery(queryName, value);
                break;
            case TERMS:
                if (value instanceof Collection) {
                    Collection collection = (Collection) value;
                    if (CollectionUtils.isNotEmpty(collection)) {
                        queryBuilder = QueryBuilders.termsQuery(queryName, collection);
                    }
                } else {
                    queryBuilder = termsQuery(queryName, value);
                }
                break;
            case RANGE:
                BoolQueryBuilder bin = QueryBuilders.boolQuery();

                for (RangeCompare rangeCompare : searchInfo.rangeInfo()) {
                    RangeQueryBuilder rb = QueryBuilders.rangeQuery(rangeCompare.queryName());
                    if (CompareOperation.GREATER == rangeCompare.operation()) {
                        rb.from(value).includeLower(rangeCompare.include());
                    } else {
                        rb.to(value).includeUpper(rangeCompare.include());
                    }
                    bin.filter(rb);
                }

                queryBuilder = bin.hasClauses() ? bin : null;
                break;
            case MATCH_PHRASE:
                queryBuilder = QueryBuilders.matchPhraseQuery(queryName, value);
                break;
        }

        return queryBuilder;
    }

}
