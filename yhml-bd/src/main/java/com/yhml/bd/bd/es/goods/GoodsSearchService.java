package com.yhml.bd.bd.es.goods;

import com.yhml.bd.bd.es.ESSearchApi;
import com.yhml.bd.bd.es.model.SearchQuery;
import com.yhml.core.base.bean.PageResult;
import com.yhml.core.util.JsonUtil;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toMap;

/**
 * @author: Jfeng
 * @date: 2018/7/6
 */
@Slf4j
public class GoodsSearchService {

    private ESSearchApi api;

    private String index = "xg";
    private String type = "goods";

    public GoodsSearchService(ESSearchApi service) {
        this.api = service;
    }

    public void index(GoodsSearchModel goods) {
        log.info("索引商品：{}", goods);
        api.index(index, type, getDocumentId(goods), JsonUtil.toJson(goods));
    }

    public void index(List<GoodsSearchModel> list) {
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, String> map = list.stream().collect(toMap(model -> getDocumentId(model), model -> JsonUtil.toJson(model)));
            api.bulkIndex(index, type, map);
        }
    }

    public void delete(DeleteModel deleteModel) {
        log.info("删除商品：{}", deleteModel);
        api.delete(index, type, getDocumentId(deleteModel.getPlatformId(), deleteModel.getSpuId(), deleteModel.getShopId()));
    }

    public PageResult<GoodsSearchModel> search(SearchQuery query) {
        log.info("搜索商品：{}", query);

        SearchResponse response = api.search(query, index, type);

        List<GoodsSearchModel> list = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            String source = hit.getSourceAsString();
            GoodsSearchModel model = JsonUtil.parse(source, GoodsSearchModel.class);
            list.add(model);
        }

        return new PageResult<>(query.getPageNo(), query.getPageSize(), (int) response.getHits().getTotalHits(), list);
    }


    private String getDocumentId(GoodsSearchModel goods) {
        return getDocumentId(goods.getPlatformId(), goods.getSpuId(), goods.getShopId());
    }

    private String getDocumentId(String platformId, String spuId, String shopId) {
        return String.join("-", platformId, spuId, shopId);
    }

}
