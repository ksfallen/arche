package com.yhml.bd.bd.es.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Jfeng
 * @date: 2018/7/5
 */
@Getter
@Setter
public class FieldMapping {

    public static final String ik_max_word = "ik_max_word";
    public static final String ik_smart = "ik_smart";
    public static final String ik_pinyin = "pinyin";
    public static final String pinyin_analyzer = "pinyin_analyzer";
    public static final String english = "english";
    public static final String text = "text";

    String name;
    String type;
    Boolean index;
    String analyzer;

    private FieldMapping(String name, String type, boolean index, String analyzer) {
        this.name = name;
        this.type = type;
        this.index = index;
        this.analyzer = analyzer;
    }

    // public void buildSource(XContentBuilder source)  {
    //     try {
    //         source.startObject(name).field("type", type).field("index", index);
    //         if (null != analyzer) {
    //             source.field("analyzer", analyzer);
    //         }
    //         source.endObject();
    //     } catch (IOException e) {
    //         throw new RuntimeException(e);
    //     }
    // }
    //
    // private FieldMapping(String name, String type) {
    //     this(name, type, false, null);
    // }
    //
    // public static FieldMapping keyword(String name) {
    //     return new FieldMapping(name, "keyword");
    // }
    //
    // public static FieldMapping text(String name) {
    //     return new FieldMapping(name, "text", true, ik_smart);
    // }
    //
    // public static FieldMapping textMaxWord(String name) {
    //     return new FieldMapping(name, "text", true, ik_max_word);
    // }
    //
    // public static FieldMapping dateType(String name) {
    //     return new FieldMapping(name, "date");
    // }
    //
    // public static FieldMapping longType(String name) {
    //     return new FieldMapping(name, "long");
    // }
}
