package com.yhml.core.config.jackson;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import cn.hutool.core.date.DatePattern;

/**
 * @author: Jfeng
 * @date: 2019-07-02
 */
public class JacksonModule extends SimpleModule {
    private static final long serialVersionUID = 5645630813802212480L;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN);

    public JacksonModule() {
        super(PackageVersion.VERSION);

        this.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
        this.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));

        this.addSerializer(LocalDate.class, new LocalDateSerializer(formatter));
        this.addDeserializer(LocalDate.class, new LocalDateDeserializer(formatter));

        this.addSerializer(LocalTime.class, new LocalTimeSerializer(formatter));
        this.addDeserializer(LocalTime.class, new LocalTimeDeserializer(formatter));
    }
}
