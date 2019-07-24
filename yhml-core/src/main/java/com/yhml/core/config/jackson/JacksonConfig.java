package com.yhml.core.config.jackson;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.yhml.core.util.DateUtils;

import cn.hutool.core.date.DatePattern;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: Jfeng
 * @date: 2019-06-13
 */
@Slf4j
@JsonComponent
public class JacksonConfig {

    @org.springframework.beans.factory.annotation.Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String pattern;

    private static final String[] patterns = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"};

    /**
     * 日期格式化
     */
    public static class DateJsonSerializer extends JsonSerializer<Date> {
        @Override
        public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(DateUtils.format(date, DatePattern.NORM_DATETIME_PATTERN));
        }
    }

    /**
     * 解析日期字符串
     */
    public static class DateJsonDeserializer extends JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
            try {
                return org.apache.commons.lang3.time.DateUtils.parseDate(parser.getText(), patterns);
            } catch (ParseException e) {
                log.error("", e);
            }

            return null;
        }

        @Override
        public Class<?> handledType() {
            return Date.class;
        }
    }

    // public static class LocalDateTimeJsonDeserializer extends JsonDeserializer<LocalDateTime> {
    //     @Override
    //     public LocalDateTime deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
    //         try {
    //             return DateUtils.parseDate(parser.getText(), patterns);
    //         } catch (ParseException e) {
    //             log.error("", e);
    //         }
    //
    //         return null;
    //     }
    //
    //     @Override
    //     public Class<?> handledType() {
    //         return LocalDateTime.class;
    //     }
    // }

}
