package com.yhml.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.yhml.core.base.bean.BaseBean;
import com.yhml.core.base.bean.Result;
import com.yhml.core.util.fastjson.NullValueFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {
    public static final SimpleDateFormatSerializer simpleDateFormat = new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss");
    public static final SerializerFeature[] features;
    public static final List<SerializeFilter> filterList = new ArrayList<>();

    private static final SerializeFilter[] EMPTY_FILTER = null;
    private static final SerializeConfig config = SerializeConfig.globalInstance;

    static {
        config.put(java.util.Date.class, simpleDateFormat);
        // config.put(java.sql.Date.class, new DateSerializer()); 		// 使用和json-lib兼容的日期输出格式

        features = getFeatures();

        filterList.add(new NullValueFilter());
    }

    public static SerializerFeature[] getFeatures() {
        List<SerializerFeature> list = new ArrayList<>();
        // list.add(SerializerFeature.WriteMapNullValue);              // 输出空置字段
        list.add(SerializerFeature.WriteNullListAsEmpty);           // list字段如果为null，输出为[]
        // list.add(SerializerFeature.WriteNullNumberAsZero);          // 数值字段如果为null，输出为0
        // list.add(SerializerFeature.WriteNullBooleanAsFalse);        // Boolean字段如果为null，输出为false
        list.add(SerializerFeature.WriteNullStringAsEmpty);         // 字符类型字段如果为null，输出为""
        list.add(SerializerFeature.WriteDateUseDateFormat);         // 全局修改日期格式。JSON.DEFFAULT_DATE_FORMAT = “yyyy-MM-dd”
        list.add(SerializerFeature.DisableCircularReferenceDetect); // 关闭FastJson的引用检测
        // list.add(SerializerFeature.BrowserCompatible);              // 中文转uncode
        list.add(SerializerFeature.SortField);                      // 排序

        return list.toArray(new SerializerFeature[0]);
    }

    /**
     * 包含默认 features , 输出空字段
     *
     * @param o
     * @return
     */
    public static String toJsonString(Object o) {
        if (o == null) {
            return "";
        }
        if (o instanceof String) {
            return (String) o;
        }

        return toJson(o, config, EMPTY_FILTER, features);
    }

    /**
     * 不输出空字段
     *
     * @param o
     * @param feature
     * @return
     */
    public static String toJsonString(Object o, SerializerFeature ... feature) {
        return toJson(o, null, EMPTY_FILTER, feature);
    }

    private static String toJson(Object object, SerializeConfig config, SerializeFilter[] filters, SerializerFeature... features) {
        try {
            if (config == null) {
                config = SerializeConfig.globalInstance;
            }

            return JSON.toJSONString(object, config, filters, features);
        } catch (Exception e) {
            log.error("toJson error:{}, value:{}", e.getMessage(), object);
        }

        return "";
    }

    public static String toJsonString(Object object, SerializeFilter... filters) {
        return toJson(object, config, filters);
    }

    /**
     * 字符串转对象
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        if (StringUtil.isBlank(json)) {
            return null;
        }

        try {
            return JSON.parseObject(json, clazz);
        } catch (Exception e) {
            log.error("parseObject error:{}, value:{}", e.getMessage(), json);
        }

        return null;
    }

    /**
     * 解析 带泛型的对象
     * 调用方法  parseByTypeReference(json, new TypeReference<ResultBean<T>>(){})
     *
     * @param json
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T extends BaseBean> Result<T> parseByTypeReference(String json, TypeReference<Result<T>> typeReference) {
        return JSON.parseObject(json, typeReference);
    }


    /**
     * 解析字符串数组
     *
     * @param json [{ ... }, { ... }]
     */
    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

    /**
     * JSON 对象转换成 java 对象
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T toJavaObject(JSON json, Class<T> clazz) {
        return JSON.toJavaObject(json, clazz);
    }

    /**
     * JSONObject 对象转map
     *
     * @param jsonObject
     * @return
     */
    public static Map<String, Object> parseObject(JSONObject jsonObject) {
        Map<String, Object> map = new HashMap<>();
        parseJSONObject(map, jsonObject);
        return map;
    }

    public static JSONObject parseObject(String json) {
        return JSON.parseObject(json);
    }

    /**
     * JSONArray 对象转map
     *
     * @param jsonArray
     * @return
     */
    public static List<Map<String, Object>> parseObject(JSONArray jsonArray) {
        List<Map<String, Object>> list = new ArrayList<>();

        if (CollectionUtils.isEmpty(jsonArray)) {
            return list;
        }

        for (Object object : jsonArray) {
            if (object instanceof JSONObject) {
                Map<String, Object> m = new HashMap<>();
                parseJSONObject(m, (JSONObject) object);
                list.add(m);
            }
        }


        return list;
    }

    /**
     * JSONObject对象转map
     *
     * @param map
     * @param jsonObject
     */
    private static void parseJSONObject(Map<String, Object> map, JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }

        for (Entry<String, Object> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof String) {
                map.put(key, value);
            } else if (value instanceof JSONObject) {
                Map<String, Object> temp = parseObject((JSONObject) value);
                map.put(key, temp);
            } else if (value instanceof JSONArray) {
                List<Map<String, Object>> list = parseObject((JSONArray) value);
                map.put(key, list);
            }
        }
    }
}
