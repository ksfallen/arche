package com.yhml.core.util;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class HolidayUtil {

    private List<Holiday> holidayList = new ArrayList<>();
    private static HolidayUtil instance = null;
    private static final String file = "holiday.xml";

    private HolidayUtil() {
        initHolidayCache();
    }

    public static HolidayUtil getInstance() {
        if (instance == null)
            instance = new HolidayUtil();
        return instance;
    }

    private void initHolidayCache() {
        SAXReader reader = new SAXReader();
        try {
            Document doc = FileUtil.parseXML(file);

            List<Element> list = doc.getRootElement().elements();

            list.forEach(e -> {
                String name = e.attributeValue("name");
                String startTime = e.attributeValue("startTime");
                String endTime = e.attributeValue("endTime");

                if (StringUtil.isNoneBlank(name, startTime, endTime)) {
                    this.holidayList.add(new Holiday(name, startTime, endTime));
                }
            });

            log.info("节假日数据初始化成功...");
        } catch (FileNotFoundException | DocumentException e) {
            log.error("节假日数据初始化失败...", e);
        }
    }

    @Data
    @AllArgsConstructor
    public static class Holiday {
        private String name;
        private String startTime;
        private String endTime;
    }

}
