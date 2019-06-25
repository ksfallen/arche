package com.test;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.assertj.core.util.Lists;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.util.FileSystemUtils;

import com.yhml.core.util.StringUtil;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * doc
 * https://www.yiibai.com/jsoup/jsoup-quick-start.html
 * https://jsoup.org/
 */
@Slf4j
public class ImageUtil {

    private static final String IMGURL_REG = "<img.*src=(.*?)[^>]*?>";
    private static final String IMGURL_REG2 = "img[src~=(?i)\\.(png|jpe?g|gif)]";
    private static final String IMGSRC_REG = "[a-zA-z]+://[^\\s]*";

    private static final String PATHNAME = "src/main/resources/res";

    @Test
    @SneakyThrows
    public void ssr() {
        String url = "https://github.com/Alvin9999/new-pac/wiki/ss%E5%85%8D%E8%B4%B9%E8%B4%A6%E5%8F%B7";

        Document doc = Jsoup.connect(url).get();
        Elements images = doc.select(IMGURL_REG2);

        String src = "";
        for (Element image : images) {
            src = image.attr("src");
            log.info("src:{}", src);
            if (src.contains("master")) {
                break;
            }
        }

        download(src);
    }

    public void removeRes() {
        File file = new File(PATHNAME);
        FileSystemUtils.deleteRecursively(file);
    }

    public static void main(String[] args) throws Exception {
        //获得html文本内容
        // String  url = "http://g.co/fb/yypx2";
        // String  url = "https://plus.google.com/photos/113684854375405108383/albums/6087831903821258689/6087831904726072418";
        String url = "https://github.com/Alvin9999/new-pac/wiki/ss%E5%85%8D%E8%B4%B9%E8%B4%A6%E5%8F%B7";

        Document doc = Jsoup.connect(url).get();

        Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");

        for (Element image : images) {
            System.out.println("src : " + image.attr("src"));
        }


        System.out.println(doc.title());


        // String HTML = getHtml(url);
        // 获取图片标签
        // List<String> imgUrl = getImageUrl(HTML);
        // System.out.println(imgUrl);
        // 获取图片src地址
        // List<String> imgSrc = getImageSrc(imgUrl);
        // System.out.println(imgSrc);
        //下载图片
        // download(imgSrc);

    }

    //获取HTML内容
    private static String getHtml(String url) throws Exception {
        URL url1 = new URL(url);//使用java.net.URL
        URLConnection connection = url1.openConnection();//打开链接
        InputStream in = connection.getInputStream();//获取输入流
        InputStreamReader isr = new InputStreamReader(in);//流的包装
        BufferedReader br = new BufferedReader(isr);

        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {//整行读取
            sb.append(line, 0, line.length());//添加到StringBuffer中
            sb.append('\n');//添加换行符
        }
        //关闭各种流，先声明的后关闭
        br.close();
        isr.close();
        in.close();
        return sb.toString();
    }

    private static List<String> getImageUrl(String html) {
        Matcher matcher = Pattern.compile(IMGURL_REG).matcher(html);
        List<String> listimgurl = new ArrayList<>();
        while (matcher.find()) {
            listimgurl.add(matcher.group());
        }
        return listimgurl;
    }


    private static List<String> getImageSrc(List<String> listimageurl) {
        List<String> listImageSrc = new ArrayList<>();
        for (String image : listimageurl) {
            Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(image);
            while (matcher.find()) {
                listImageSrc.add(matcher.group().substring(0, matcher.group().length() - 1));
            }
        }
        return listImageSrc;
    }


    private static void download(String src) {
        download(Lists.newArrayList(src));
    }

    private static void download(List<String> listImgSrc) {
        try {
            //开始时间
            long t1 = System.currentTimeMillis();

            for (String url : listImgSrc) {
                if (StringUtil.isBlank(url)) {
                    log.info("src is blank:" + url);
                    continue;
                }

                //开始时间
                String imageName = url.substring(url.lastIndexOf("/") + 1);
                URL uri = new URL(url);

                @Cleanup InputStream in = uri.openStream();

                //文件输出流
                @Cleanup FileOutputStream fo = new FileOutputStream(new File(PATHNAME + File.separator + imageName));

                byte[] buf = new byte[1024];
                int length = 0;

                long t2 = System.currentTimeMillis();
                log.info("开始下载:" + url);

                while ((length = in.read(buf, 0, buf.length)) != -1) {
                    fo.write(buf, 0, length);
                }

                //结束时间
                log.info("imageName:{}, time:{}", imageName, System.currentTimeMillis() - t2);
            }
            log.info("total time:{}", System.currentTimeMillis() - t1);
        } catch (Exception e) {
            log.error("下载失败", e);
        }
    }
}
