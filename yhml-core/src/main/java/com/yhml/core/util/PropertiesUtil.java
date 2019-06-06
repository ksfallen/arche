package com.yhml.core.util;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.builder.fluent.Parameters;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;


/**
 * http://commons.apache.org/proper/commons-configuration/userguide/user_guide.html
 */
@Slf4j
public class PropertiesUtil {

    private static List<String> defaultFiles = Lists.newArrayList("config/config.properties", "config.properties");

    private static CompositeConfiguration config = new CompositeConfiguration();

    private static Parameters params = new Parameters();

    /**
     * 配置文件指定值
     */
    private static List<String> fileList;

    static {
        loadProp(defaultFiles);
        loadProp(fileList);
        params.properties().setEncoding(StandardCharsets.UTF_8.name());
    }

    public static void main(String[] args) {
        System.out.println(getString("app.test"));
        System.out.println(getList("app.arr"));
        addProperty("app.arr2", "123");
        System.out.println(getList("app.arr2"));
    }

    /**
     * 自定义配置文件
     *
     * @param fileName
     */
    public static void loadProp(String fileName) {

        // InputStreamReader reader = getStream(fileName);
        // if (reader == null) {
        //     return;
        // }

        try {
            Configurations configs = new Configurations(params);
            FileBasedConfigurationBuilder<PropertiesConfiguration> builder = configs.propertiesBuilder(fileName);
            builder.setAutoSave(true);

            if (Objects.nonNull(builder.getFileHandler().getURL())) {
                config.addConfiguration(builder.getConfiguration());
                log.info("=========== 配置加载成功: {}", fileName);
            }


            // FileBasedConfigurationBuilder<FileBasedConfiguration> source = new FileBasedConfigurationBuilder<FileBasedConfiguration>
            //         (PropertiesConfiguration.class).configure(params.properties().setEncoding("UTF-8").setFileName(fileName));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private static void loadProp(List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        for (String filename : list) {
            loadProp(filename);
        }
    }

    public static void loadXML(String fileName) {
        try {
            Configurations configs = new Configurations(params);
            FileBasedConfigurationBuilder<XMLConfiguration> builder = configs.xmlBuilder(fileName);
            config.addConfiguration(builder.getConfiguration());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    // private static InputStreamReader getStream(String filename) {
    //     String path = Objects.requireNonNull(filename);
    //
    //     if (path.startsWith("classpath:")) {
    //         path = path.substring("classpath:".length());
    //     }
    //
    //     if (path.startsWith("/")) {
    //         path = path.substring(1);
    //     }
    //
    //     InputStreamReader reader = null;
    //     try {
    //         reader = new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(path), Charsets.UTF_8);
    //     } catch (Exception e) {
    //         log.error("配置文件没有找到:{}", filename);
    //     }
    //
    //     return reader;
    // }

    public static String get(String key) {
        return getCofig().getString(key, "");
    }

    public static String getString(String key) {
        return getCofig().getString(key, "");
    }

    public static String getString(String key, String defaultValue) {
        return getCofig().getString(key, defaultValue);
    }

    public static List<String> getList(String key) {
        return Arrays.asList(getCofig().getStringArray(key));
    }

    public static Integer getInt(String key, Integer defaultValue) {
        return getCofig().getInt(key, defaultValue);
    }

    public static void addProperty(String key, String value) {
        getCofig().addProperty(key, value);
    }

    public static CompositeConfiguration getCofig() {
        return config;
    }

    public static void setFileList(List<String> fileList) {
        PropertiesUtil.fileList = fileList;
    }
}
