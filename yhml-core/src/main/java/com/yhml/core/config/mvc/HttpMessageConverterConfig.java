package com.yhml.core.config.mvc;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.common.collect.Lists;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HttpMessageConverterConfig {

    public HttpMessageConverter stringHttpMessageConverter() {
        List<MediaType> list = Lists.newArrayList(MediaType.TEXT_PLAIN, MediaType.ALL);
        StringHttpMessageConverter converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converter.setSupportedMediaTypes(list);
        return converter;
    }
    //
    // public static FormHttpMessageConverter formHttpMessageConverter() {
    //     List<MediaType> list = Lists.newArrayList(MediaType.APPLICATION_FORM_URLENCODED, MediaType.MULTIPART_FORM_DATA);
    //     FormHttpMessageConverter converter = new AllEncompassingFormHttpMessageConverter();
    //     // converter.setCharset(StandardCharsets.UTF_8);
    //     // converter.setSupportedMediaTypes(list);
    //     return converter;
    // }

    /**
     * 使用 FastJson 代替 Jackson
     * 1、先定义一个convert转换消息的对象
     * 2、添加fastjson的配置信息，比如是否要格式化返回的json数据；
     * 3、在convert中添加配置信息
     * 4、将convert添加到converters
     */
    // @Bean
    public HttpMessageConverter fastJsonConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();

        //附加：处理中文乱码
        List<MediaType> fastMedisTypes = new ArrayList<>();
        fastMedisTypes.add(MediaType.APPLICATION_JSON_UTF8);
        // fastMedisTypes.add(MediaType.TEXT_PLAIN);
        converter.setSupportedMediaTypes(fastMedisTypes);
        converter.setFastJsonConfig(fastJsonConfig());
        return converter;
    }

    /**
     * fastjson 配置信息
     * 修改全局日输出期格式 默认 "yyyy-MM-dd HH:mm:ss"
     * @return
     */
    private static FastJsonConfig fastJsonConfig() {
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.getSerializeConfig().put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
        fastJsonConfig.setSerializerFeatures(getFeatures());
        return fastJsonConfig;
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
}
