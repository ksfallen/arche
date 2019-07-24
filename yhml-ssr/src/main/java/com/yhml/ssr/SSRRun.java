package com.yhml.ssr;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.yhml.ssr.baidu.BaiduOcrApi;
import com.yhml.ssr.baidu.BaiduOcrResult;
import com.yhml.ssr.baidu.WordsResult;

import lombok.Cleanup;

public class Main {

    String output = ".";
    boolean openLink = false;

    public static void main(String[] args) throws Exception {
        Main test = new Main();

        for (int index = 0; index < args.length; index++) {
            String value = args[index];
            switch (index) {
                case 1: test.openLink =  Boolean.valueOf(value); break;
                case 2: test.output = value; break;
                default:
            }
        }

        test.ssr();
    }

    public void ssr() throws Exception {
        String url = "https://github.com/Alvin9999/new-pac/wiki/ss%E5%85%8D%E8%B4%B9%E8%B4%A6%E5%8F%B7";
        Document doc = Jsoup.connect(url).get();
        Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g)]");

        String src = "";
        for (Element image : images) {
            src = image.attr("src");
            System.out.println("[#] img src:" + src );
            if (src.contains("master")) {
                break;
            }
        }

        // https://raw.githubusercontent.com/Alvin9999/PAC/master/ss/ssr4104.PNG
        System.out.println("[#] ssr image: " + src);

        if (src == null || src.length() == 0) {
            return;
        }

        String file = download(output, src);
        File imageFile = new File(file);

        if (!imageFile.exists()) {
            System.out.println("[#] image file 不存在:" +imageFile.getAbsolutePath());
            return;
        }

        BaiduOcrResult words = BaiduOcrApi.parse(imageFile.getAbsolutePath());

        int i = 0;

        SSRBean bean = new SSRBean();
        List<SSRBean> list = new ArrayList<>();
        for (WordsResult word :  words.getWordsResult()) {
            String value = word.getWords().trim().replaceAll("[()\\s协议混淆]", "");

            if (i == 0 ) {
                bean = new SSRBean();
                list.add(bean);
            }

            switch (i++) {
                case 0: break;
                case 1: bean.setServer(getWord(value)); break;
                case 2: bean.setPort(getWord(value)); break;
                case 3: bean.setPassword(getWord(value)); break;
                case 4: bean.setMethod(getWord(value)); break;
                case 5: bean.setProtocol(getWord(value)); break;
                case 6:
                    bean.setObfs(getWord(value));
                    i = 0;
                    break;
                default:
            }
        }

        System.out.println("\n============");
        for (SSRBean bean1 : list) {
            String link = bean1.toSSRLink();
            System.out.println(link);

            if (openLink) {
                try {
                    Desktop.getDesktop().browse(new URI(link));
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception ignored) {
                }
            }
        }
    }

    private static String getWord(String value) {
        String[] split = value.split(":");
        return split.length == 1 ? split[0].toLowerCase() : split[1].toLowerCase();
    }

    public static String download(String output, String url) {
        String pathname = "";
        try {
            if (StringUtil.isBlank(url)) {
                System.out.println("url is blank:" + url);
                return pathname;
            }

            //开始时间
            long t = System.currentTimeMillis();

            System.out.println("[#] 开始下载:" + url);

            String imageName = url.substring(url.lastIndexOf("/") + 1);
            URL uri = new URL(url);

            @Cleanup InputStream in = uri.openStream();

            //文件输出流
            pathname = output + File.separator + imageName;
            @Cleanup FileOutputStream fo = new FileOutputStream(new File(pathname));

            byte[] buf = new byte[1024];
            int length = 0;

            while ((length = in.read(buf, 0, buf.length)) != -1) {
                fo.write(buf, 0, length);
            }

            //结束时间
            System.out.println("[#] SSR 图片文件: " + pathname);
            System.out.println("[#] time: " + (System.currentTimeMillis() - t));

        } catch (IOException ex) {
            System.out.println("[x] 下载失败");
            ex.printStackTrace();
        }

        return pathname;
    }

}
