package com.yhml.bd.bd.es.model;

import java.io.IOException;
import java.lang.reflect.Field;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import com.simple.common.es.annotation.ESField;
import com.simple.common.es.goods.GoodsSearchModel;
import com.simple.common.util.BeanUtil;
import com.simple.common.util.StringUtil;

/**
 * @author: Jfeng
 * @date: 2018/8/16
 */
public class MappingsHelper {

    public static void main(String[] args) throws IOException {
        System.out.println(goods().string());
    }

    public static XContentBuilder goods() {
        try {
            XContentBuilder source = XContentFactory.jsonBuilder().startObject();
            source.startObject("_all").field("enabled", "false").endObject();
            source.startObject("properties");
            MappingsHelper.buildSource(source, GoodsSearchModel.class);
            return source.endObject().endObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void buildSource(XContentBuilder source, Class clazz) throws IOException {
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            ESField esField = field.getAnnotation(ESField.class);
            if (esField == null && !BeanUtil.isSimpleProperty(field.getType())) {
                continue;
            }

            String type = field.getType().getSimpleName().toLowerCase();
            String analyzer = esField != null ? esField.analyzer() : null;

            if (field.getType() == String.class) {
                type = "keyword";
            }

            source.startObject(field.getName());
            source.field("type", esField != null ? esField.type(): type);
            source.field("index", esField != null && esField.index());

            if (StringUtil.isNotBlank(analyzer)) {
                source.field("analyzer", analyzer);
            }

            source.endObject();
        }
    }

    // public static XContentBuilder addProperties(XContentBuilder builder, List<FieldMapping> list) {
    //     return addProperties(builder, list.toArray(new FieldMapping[0]));
    // }
    //
    // public static XContentBuilder addProperties(XContentBuilder builder, FieldMapping... fm) {
    //     try {
    //         builder.startObject("properties");
    //         Arrays.stream(fm).forEach(mapping -> mapping.buildSource(builder));
    //         return builder.endObject();
    //     } catch (IOException e) {
    //         throw new RuntimeException(e);
    //     }
    // }
}
