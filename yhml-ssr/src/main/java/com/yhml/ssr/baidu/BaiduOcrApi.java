package com.yhml.ssr.baidu;

import com.alibaba.fastjson.JSON;
import com.baidu.aip.client.BaseClient;
import com.baidu.aip.ocr.AipOcr;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * https://cloud.baidu.com/doc/OCR/index.html
 */
public class BaiduOcrApi {
    protected static final Logger LOGGER = Logger.getLogger(BaseClient.class);

    //设置APPID/AK/SK
    public static final String APP_ID = "16549952";
    public static final String API_KEY = "dqRENUYM3uwfWU9b0YMjXUVX";
    public static final String SECRET_KEY = "c7oiSKxpdu0oTmIITRg1a9QGwaLHTu5o";

    // 初始化一个AipOcr
    public static AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

    static {

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        // LOGGER.setLevel(Level.ERROR);
    }


    public static String parse(String path, HashMap<String, String> options) {
        if (options == null) {
            options = new HashMap<>();
        }
        // 传入可选参数调用接口
        // HashMap<String, String> options = new HashMap<>();
        options.put("language_type", "CHN_ENG");
        // options.put("detect_direction", "false");
        // options.put("detect_language", "true");
        options.put("probability", "false");

        // 调用接口
        // JSONObject res = client.basicGeneral(path, options);
        JSONObject res = client.basicAccurateGeneral(path, options);
        try {
            String ret = res.toString(2);
            System.out.println(ret);
            return ret;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static BaiduOcrResult parse(String path) {
        String text = parse(path, null);
        return JSON.parseObject(text, BaiduOcrResult.class);
    }

}
