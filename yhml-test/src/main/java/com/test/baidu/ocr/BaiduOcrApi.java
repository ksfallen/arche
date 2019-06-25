package com.test.baidu.ocr;

import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;

import lombok.SneakyThrows;

/**
 * @author: Jfeng
 * @date: 2019-06-18
 */
public class BaiduOrcApi {
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

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        // client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        // client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
    }



    @SneakyThrows
    public static void main(String[] args) {

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<>();
        options.put("language_type", "CHN_ENG");
        // options.put("detect_direction", "false");
        options.put("detect_language", "true");
        options.put("probability", "true");



        // 调用接口
        String path = "src/main/resources/res/ssr4102.PNG";
        JSONObject res = client.basicGeneral(path, options);
        System.out.println(res.toString(2));

    }
}
