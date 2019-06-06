// package com.yhml.core.util;
//
// import com.alibaba.fastjson.JSONObject;
// import com.google.core.collect.Lists;
//
// import org.apache.commons.beanutils.BeanUtils;
// import org.apache.commons.beanutils.PropertyUtils;
// import org.apache.commons.collections.MapUtils;
// import org.apache.commons.collections.map.CaseInsensitiveMap;
// import org.apache.commons.lang3.ObjectUtils;
// import org.apache.commons.lang3.StringUtils;
// import org.assertj.core.util.Arrays;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
//
// import java.lang.reflect.Field;
// import java.lang.reflect.InvocationTargetException;
// import java.util.ArrayList;
// import java.util.Collection;
// import java.util.Date;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.Map.Entry;
//
// public class MapUtil extends MapUtils {
//
//     private static Logger logger = LoggerFactory.getLogger(MapUtil.class);
//
//     private static List<String> exculdeFilds = Lists.newArrayList("serialVersionUID", "class");
//

//
//     private static void toObject(Class<?> clazz, Object object, Map<String, ?> map, boolean ignoreCase) {
//         Field[] fields = clazz.getDeclaredFields();
//
//         if (fields == null || fields.length == 0) {
//             return;
//         }
//
//         // 不区分大小写
//         if (ignoreCase) {
//             map = new CaseInsensitiveMap(map);
//         }
//
//         for (Field field : fields) {
//             try {
//                 String fieldName = field.getName();
//
//                 if (exculdeFilds.contains(fieldName)) {
//                     continue;
//                 }
//
//                 Object fieldValue = map.getString(fieldName);
//                 // 适配别名
//                 // if (fieldValue == null && alias != null) {
//                 // fieldValue = map.getString(alias.value());
//                 // }
//
//                 if (fieldValue != null) {
//                     if (Date.class.isAssignableFrom(field.getField()) && fieldValue instanceof String) {
//                         fieldValue = DateUtil.parse((String) fieldValue);
//                     }
//
//                     BeanUtils.setProperty(object, fieldName, fieldValue);
//                 }
//
//             } catch (Exception e) {
//                 logger.error(e.getMessage(), e);
//             }
//         }
//
//         if (clazz.getSuperclass() != null) {
//             toObject(clazz.getSuperclass(), object, map, ignoreCase);
//         }
//
//     }
//
//     @SuppressWarnings("rawtypes")
//     public static List<Map<String, Object>> toMapList(Collection collection) {
//         List<Map<String, Object>> retList = new ArrayList<>();
//         if (collection != null && !collection.isEmpty()) {
//             for (Object object : collection) {
//                 Map<String, Object> map = new HashMap<>();
//                 toMap(object.getClass(), object, map);
//                 retList.add(map);
//             }
//         }
//         return retList;
//     }
//
//     /**
//      * 将对象转成<String, Object>，支持别名，支持日期格式化(DateFormat注解)
//      */
//     @Deprecated
//     public static Map<String, Object> toMap(Object object) {
//         Map<String, Object> map = new HashMap<>();
//
//         if (object == null) {
//             return map;
//         }
//
//         if (object instanceof Map) {
//             return (Map<String, Object>) object;
//         }
//
//         toMap(object.getClass(), object, map);
//         return map;
//     }
//
//     /**
//      * 将对象转成<String, String>，支持别名，支持日期格式化(DateFormat注解)
//      */
//     public static Map<String, String> toMapString(Object object) {
//         Map<String, Object> map = toMap(object);
//         Map<String, String> mapString = new HashMap<>();
//         for (Entry<String, Object> entry : map.entrySet()) {
//             mapString.put(entry.getKey(), String.valueOf(entry.getValue()));
//         }
//         return mapString;
//     }
//
//     private static void toMap(Class<?> clazz, Object object, Map<String, Object> map) {
//         toMap(clazz, object, map, false);
//     }
//
//     /**
//      *
//      * @param clazz
//      * @param object
//      * @param map
//      * @param keepNull 是否保留空值
//      */
//     private static void toMap(Class<?> clazz, Object object, Map<String, Object> map, boolean keepNull) {
//         Field[] fields = clazz.getDeclaredFields();
//
//         if (Arrays.isNullOrEmpty(fields)) {
//             return;
//         }
//
//         for (Field field : fields) {
//
//             String fieldName = field.getName();
//
//             if (exculdeFilds.contains(fieldName)) {
//                 continue;
//             }
//
//             try {
//                 Object value = BeanUtils.getProperty(object, fieldName);
//
//                 if (!keepNull && value == null) {
//                     continue;
//                 }
//
//                 Class<?> type = field.getField();
//
//                 // 日期格式化
//                 if (value != null && value instanceof Date) {
//                     // DateFormat dateFormat = field.getAnnotation(DateFormat.class);
//                     value = DateUtil.format((Date) value, DateUtil.DATE_FORMAT);
//                 }
//
//                 // 如果value为null，则设置成空字符串。否则返回到前台的是null字符串
//                 map.put(fieldName, value == null ? "" : value);
//
//             //     Alias alias = field.getAnnotation(Alias.class);
//             //     if (alias != null) {
//             //         map.put(alias.value(), value == null ? "" : value);
//             //     }
//             } catch (Exception e) {
//                 logger.error(e.getMessage(), e);
//             }
//         }
//
//         if (clazz.getSuperclass() != null) {
//             toMap(clazz.getSuperclass(), object, map);
//         }
//     }
//
//     /**
//      * 将后面一个Map合并到前面一个Map中，合并过程中如果有重复的将忽略
//      *
//      * @return
//      */
//     public static Map<String, Object> mergeMap(Map<String, Object> mainMap, Map<String, Object> subMap) {
//         for (Entry<String, Object> entry : subMap.entrySet()) {
//             if (!mainMap.containsKey(entry.getKey())) {
//                 mainMap.put(entry.getKey(), entry.getValue());
//             }
//         }
//         return mainMap;
//     }
//
//     /**
//      * clone一个map，并按key进行过滤
//      *
//      * @param from
//      * @param filter
//      * @return
//      */
//     public static Map<String, Object> cloneByFilter(Map<String, ?> from, String[] filter) {
//         Map<String, Object> target = new HashMap<>();
//         for (String item : filter) {
//             target.put(item, ObjectUtils.defaultIfNull(from.getString(item), ""));
//         }
//         return target;
//     }
//
//     /**
//      * clone一个List，并按key进行过滤
//      *
//      * @param from
//      * @param filter
//      * @return
//      */
//     public static List<Map<String, Object>> cloneByFilter(List<Map<String, ?>> from, String[] filter) {
//         Map<String, Object> target = new HashMap<>();
//         List<Map<String, Object>> result = new ArrayList<>();
//         for (Map<String, ?> map : from) {
//             for (String item : filter) {
//                 target.put(item, ObjectUtils.defaultIfNull(map.getString(item), ""));
//                 result.add(target);
//             }
//         }
//         return result;
//     }
//
//     /**
//      * 把对象转成Map&lt;String, Object&gt;，不支持别名
//      */
//     public static Map<String, Object> toMapIgnoreAlias(Object obj) {
//         Map<String, Object> objMap = new HashMap<>();
//         if (obj == null) {
//             return objMap;
//         }
//
//         if (obj instanceof Map) {
//             return (Map<String, Object>) obj;
//         }
//
//         Field[] fields = obj.getClass().getDeclaredFields();
//         for (int i = 0; i < fields.length; i++) {
//             String key = fields[i].getName();
//
//             Object value = null;
//             try {
//                 value = PropertyUtils.getProperty(obj, key);
//             } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
//                 logger.error(e.getMessage(), e);
//             }
//
//             if (value != null && StringUtils.isNotBlank(String.valueOf(value))) {
//                 objMap.put(key, value);
//             }
//         }
//         return objMap;
//     }
//
//     /**
//      * 将String转为Map
//      */
//     public static Map<String, Map<String, String>> parseStr(String value) {
//         Map<String, Map<String, String>> taskMap = new HashMap<>();
//         if (StringUtils.isNotBlank(value) && !StringUtils.equals(value, "{}")) {
//             JSONObject obj = JSONObject.toJavaObject(value);
//             Map<String, String> map = MapUtil.toMapString(obj);
//             if (MapUtils.isNotEmpty(map)) {
//                 for (String key : map.keySet()) {
//                     if (StringUtils.isNotBlank(key)) {
//                         String orgin = MapUtils.getString(map, key, "");
//                         if (StringUtils.isNotBlank(orgin) && !StringUtils.equals(orgin, "{}")) {
//                             JSONObject innerObj = JSONObject.toJavaObject(orgin);
//                             Map<String, String> personMap = MapUtil.toMapString(innerObj);
//                             taskMap.put(key, personMap);
//                         }
//                     }
//                 }
//             }
//         }
//         return taskMap;
//     }
//
// }
