package com.yhml.bd.bd.es;

import java.io.IOException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Before;
import org.junit.Test;

import com.yhml.bd.bd.es.model.MappingsHelper;
import com.yhml.core.util.JsonUtil;


/**
 * @author: Jfeng
 * @date: 2018/7/5
 */
public class ESTest {

    String host = "127.0.0.1:9300";
    String clusterName = "elasticsearch_Jfeng";
    String nodeName = "node-1";
    String indexName = "goods-dev";
    String typeName = "goods";

    private ESConfig config;

    private TransportClient client;

    @Before
    public void before() {
        config = new ESConfig();
        config.setAddressHostPorts(host);
        config.setClusterName(clusterName);
        config.setIndexName(indexName);
        config.setTypeName(typeName);
        config.setUserName("elastic");
        config.setPassword("yhml@2018");

        client = ESClientFactory.build(config).getClient();
    }


    @Test
    public void test() throws Exception {
        Object object = null;
        ESSearchApi searchApi = new ESSearchApi(config);

        // --------------- index
        searchApi.deleteIndex(indexName);
        searchApi.createIndex(indexName, typeName, MappingsHelper.goods());

        String s = setting2();
        searchApi.updateSettings(indexName, s);

        // unpdate mapping
        XContentBuilder builder = getMapping2();
        searchApi.updateMapping(indexName, typeName, builder);

        // ESSearchApi proxy = LogProxy.getInstance().getProxy(api, config);
        // GoodsSearchService service = new GoodsSearchService(searchApi);
        //
        // // ----------- doc
        // GoodsSearchModel o1 = new GoodsSearchModel();
        // o1.setPlatformId("2");
        // o1.setSpuId("001");
        // o1.setShopId("1");
        // o1.setName("苹果");
        //
        // GoodsSearchModel o2 = new GoodsSearchModel();
        // o2.setPlatformId("2");
        // o2.setSpuId("002");
        // o2.setShopId("1");
        // o2.setName("香蕉");
        //
        //
        // service.index(Lists.newArrayList(o1, o2));


        // FieldMapping fm = FieldMapping.text("name");
        // fm.setAnalyzer("pinyin_analyzer");

        // String json = "{\n" + "  \"properties\": {\n" + "    \"name\": {\n" + "      \"fields\": {\n" + "        \"pinyin\": {\n" + "
        //         \"type\": \"text\",\n" + "          \"store\": false,\n" + "          \"term_vector\": \"with_offsets\",\n" + "
        //   \"analyzer\": \"pinyin_analyzer\",\n" + "          \"boost\": 10\n" + "        }\n" + "      }\n" + "    }\n" + "  }\n" + "}";
        // api.updateMapping(indexName, typeName, json);

        // SearchGoodsModel goods = new SearchGoodsModel("3", "1000001");
        // goods.setName("苹果2").setCreateDate(DateUtil.addDay(new Date(), -10));
        //
        // SearchGoodsModel goods3 = new SearchGoodsModel("3", "1000002");
        // goods3.setName("香蕉").setCreateDate(new Date());
        //
        // List list = Lists.newArrayList(goods, goods3);
        // // goodsSearchService.index(list);
        //
        // SearchQuery query = new SearchQuery();
        // GoodsSearchCondition condition = new GoodsSearchCondition();
        // condition.setName("苹果");
        // query.setCondition(condition);
        // object = goodsSearchService.search(query);

        // DeleteModel model = new DeleteModel("3", "1000001");
        // goodsSearchService.delete(model);

        // searchApi.deleteIndex(indexName);

        // FieldMapping keycode = FieldMapping.keyword("keycode");
        // searchApi.updateMapping(indexName, typeName, MappingsHelper.addProperties(keycode));


        // ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> mappings = client.admin().indices()
        //         .preparePutMapping()
        //         .prepareGetMappings
        //         (indexName).get().mappings();


        // client.admin().indices().preparePutMapping(indexName).setSource().get();


        // AnalyzeRequestBuilder builder = client.admin().indices().pprepareAnalyze();
        // builder.setIndex(indexName);
        // builder.

        // System.out.println(mappings);
        System.out.println(JsonUtil.toJsonString(object));
    }

    // @foramtter:off
    private static String setting() {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            builder.startObject("analysis");
            builder.startObject("analyzer").startObject("pinyin_analyzer").field("tokenizer", "my_pinyin").endObject().endObject();
            builder.startObject("tokenizer").startObject("my_pinyin")
                    .field("type", "pinyin")
                    .field("keep_first_letter", "true")
                    .field("keep_separate_first_letter", "true")
                    .field("keep_full_pinyin", "false")
                    .field("keep_original", "false")
                    .field("keep_joined_full_pinyin", "false")
                    .field("keep_none_chinese", "true")
                    .field("none_chinese_pinyin_tokenize", "true")
                    .field("limit_first_letter_length", "16")
                    .field("lowercase", "true")
                    .endObject().endObject();
            builder.endObject().endObject();
            return builder.string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    private static String setting2() {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            builder.startObject("analysis");
            addFilter(builder);
            addAnalyzer(builder);
            builder.endObject().endObject();

            String string = builder.string();
            System.out.println(string);
            return string;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    private static void addFilter(XContentBuilder builder) throws IOException {
        builder.startObject("filter");
        builder.startObject("edge_ngram_filter").field("type", "edge_ngram").field("min_gram", 1).field("max_gram", 50).endObject();

        builder.startObject("pinyin_simple")
                .field("type", "pinyin")
                .field("keep_first_letter", true)
                .field("keep_separate_first_letter", false)
                .field("keep_full_pinyin", false)
                .field("keep_original", false)
                .field("limit_first_letter_length", 50)
                .endObject();

        builder.startObject("pinyin_full")
                .field("type", "pinyin")
                .field("keep_first_letter", false)
                .field("keep_separate_first_letter", false)
                .field("keep_full_pinyin", true)
                .field("none_chinese_pinyin_tokenize", true)
                .field("keep_original", false)
                .field("limit_first_letter_length", 50)
                .endObject();

        builder.endObject();

    }
    private static void addAnalyzer(XContentBuilder builder) throws IOException {
        builder.startObject("analyzer");

        builder.startObject("ngram_index_analyzer")
                .field("type", "custom")
                .field("tokenizer", "keyword")
                .array("filter", new String[]{"edge_ngram_filter", "lowercase"})
                .endObject();

        builder.startObject("ngram_search_analyzer")
                .field("type", "custom")
                .field("tokenizer", "keyword")
                .array("filter", new String[]{"lowercase"})
                .endObject();

        builder.startObject("pinyin_simple_index_analyzer")
                .field("type", "custom")
                .field("tokenizer", "keyword")
                .array("filter", new String[]{"pinyin_simple","edge_ngram_filter","lowercase"})
                .endObject();

        builder.startObject("pinyin_simple_search_analyzer")
                .field("type", "custom")
                .field("tokenizer", "keyword")
                .array("filter", new String[]{"pinyin_simple","lowercase"})
                .endObject();

        builder.startObject("pinyin_full_analyzer")
                .field("type", "custom")
                .field("tokenizer", "keyword")
                .array("filter", new String[]{"pinyin_full","lowercase"})
                .endObject();

        builder.endObject();
    }

    private static XContentBuilder getMapping() {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            builder.startObject("properties").startObject("name")
                    .field("type", "text").field("index","true").field("analyzer", "ik_max_word")
                    .startObject("fields").startObject("pinyin")
                    .field("type", "text")
                    .field("store", "false")
                    .field("term_vector", "with_offsets")
                    .field("analyzer", "pinyin_analyzer")
                    .field("boost", "10");
            builder.endObject().endObject().endObject().endObject().endObject();
            System.out.println(builder.string());

            return builder;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static XContentBuilder getMapping2() {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            builder.startObject("properties").startObject("name").field("type", "text").field("index","true").field("analyzer", "ik_max_word")
                    .startObject("fields")
                    .startObject("word").field("type", "text").field("analyzer", "ngram_index_analyzer").endObject()
                    .startObject("spy").field("type", "text").field("analyzer", "pinyin_simple_index_analyzer").endObject()
                    .startObject("fpy").field("type", "text").field("analyzer", "pinyin_full_analyzer").endObject()
                    .endObject();
            builder.endObject().endObject().endObject();
            System.out.println(builder.string());
            return builder;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        getMapping2();
    }


}
