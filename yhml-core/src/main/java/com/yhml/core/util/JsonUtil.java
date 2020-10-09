package com.yhml.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final static DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);      // 忽略不存在的属性
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);         // 禁止 date 转毫秒
        mapper.disable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);          // map的key的自然排序
        // mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);        // 属性为空或者为 NULL 都不序列化
        mapper.registerModule(getJavaTimeModule());
    }

    public static JavaTimeModule getJavaTimeModule() {
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(LocalTime.class, new LocalTimeSerializer(formatter2));
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
        module.addDeserializer(LocalTime.class, new LocalTimeDeserializer(formatter2));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
        return module;
    }


    public static void main(String[] args) {
        TestBean bean = new TestBean();
        String json = toJson(bean);
        System.out.println(json);
        System.out.println(toPrettyJson(bean));
        System.out.println(parse(json, TestBean.class));

        Map<String, Object> map = toMap(json);
        System.out.println(map);

        List list = toList("[" + json + "]");
        System.out.println(list);
    }

    public static String toPrettyJson(Object object) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("toJson error:{}, value:{}", e.getMessage(), object);
        }
        return "";
    }

    public static String toJson(Object o) {
        return toJson(null, o);
    }

    public static String toJson(ObjectMapper objectMapper, Object object) {
        try {
            if (object == null) {
                return "";
            }
            if (object instanceof String) {
                return (String) object;
            }
            if (objectMapper == null) {
                objectMapper = mapper;
            }
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error("toJson error:{}, value:{}", e.getMessage(), object);
        }

        return "";
    }

    public static Map<String, Object> toMap(String json) {
        return parse(json, Map.class);
    }

    public static List<Map<String, String>> toList(String json) {
        return parse(json, List.class);
    }

    public static List<String> toArray(String json) {
        return toList(json, String.class);
    }

    /**
     * 解析字符串数组
     *
     * @param json [{ ... }, { ... }]
     */
    public static <T> List<T> toList(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            log.error("json parse error {}", e.getMessage() , e);
        }
        return Collections.emptyList();
    }

    /**
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> toMap(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructParametricType(Map.class, String.class, clazz));
        } catch (Exception e) {
            log.error("json parse error {}", e.getMessage() , e);
        }
        return Collections.emptyNavigableMap();
    }

    /**
     * 字符串转对象z
     */
    public static <T> T parse(String json, Class<T> clazz) {
        try {
            if (StringUtil.isNotBlank(json)) {
                return mapper.readValue(json, clazz);
            }
        } catch (Exception e) {
            log.error("json parse error {}", e.getMessage() , e);
        }

        return null;
    }

    /**
     * 解析 带泛型的对象
     * 调用方法  parseByTypeReference(json, new TypeReference<ResultBean<T>>(){})
     */
    public static <T> T parseByTypeReference(String json, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(json, typeReference);
        } catch (IOException e) {
            log.error("json parse error {}, json:{}", e.getMessage(), json);
        }
        return null;
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class TestBean {
    private String name;
    private LocalDateTime date = LocalDateTime.now();
    private LocalDate date2 = LocalDate.now();
    private LocalTime date3 = LocalTime.now();
}
