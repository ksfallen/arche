package com.yhml.ssr;

import com.yhml.ssr.baidu.BaiduOcrApi;
import com.yhml.ssr.baidu.BaiduOcrResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import lombok.ToString;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ToString
public class SSRRun {
    private static final String ssr = "ssr";
    private static final String v2ary = "v2ary";

    private String output = ".";
    private String type = ssr;
    private boolean openLink = false;

    public static void main(String[] args) throws Exception {
        SSRRun run = new SSRRun();

        // @formatter:off
        for (int index = 0; index < args.length; index++) {
            String value = args[index];
            switch (index) {
                case 0: run.type = value; break;
                case 1: run.openLink =  Boolean.parseBoolean(value); break;
                case 2: run.output = value; break;
                default:
            }
        }
        // @formatter:on
        run.run();
    }

    private void run() throws Exception {
        URL resource = SSRRun.class.getResource("");

        if (resource != null) {
            String path = resource.getPath().replace("file:", "");
            path = path.substring(0, path.lastIndexOf("yhml-ssr"));

            if (!resource.getPath().contains(".jar!")) {
                path += "yhml-ssr";
            }

            this.output = path.endsWith("/") ? path.substring(0, path.length() - 1) : path;
        }

        System.out.println("[#] output: " + this.output);
        System.out.println("[#] openLink: " + this.openLink);

        if (ssr.contains(this.type)) {
            ssr();
        } else if (v2ary.contains(this.type)) {
            v2ary();
        } else {
            System.out.println("类型参数错误");
        }
    }

    private static String getWord(String value) {
        String[] split = value.replace("：", ":").split(":");
        return split.length == 1 ? split[0].toLowerCase().trim() : split[1].toLowerCase().trim();
    }

    private static String getServer(String value) {
        Pattern pattern = Pattern.compile("\\d+.");
        Matcher matcher = pattern.matcher(value);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            sb.append(matcher.group());
        }
        return sb.toString();
    }

    private static String getPort(String value) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(value);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            sb.append(matcher.group());
        }
        return sb.toString();
    }


    public void ssr() throws Exception {
        String url = "https://github.com/Alvin9999/new-pac/wiki/ss免费账号";
        Document doc = Jsoup.connect(url).get();
        boolean isStop = false;

        // 解析文本
        Elements elements = doc.select("p");
        List<SSRBean> list = parseText(elements);

        // 解析图片
        // Elements elements = doc.select("img[src]");
        // List<SSRBean> list = parseImage(elements);

        System.out.println("\n============");
        for (SSRBean ssr : list) {
            System.out.println(ssr.toSSRLink());
            if (openLink) {
                try {
                    Desktop.getDesktop().browse(new URI(ssr.toSSRLink()));
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception ignored) {
                }
            }
        }
    }

    public void v2ary() throws Exception {
        String url = "https://github.com/Alvin9999/new-pac/wiki/v2ray免费账号";
        Document doc = Jsoup.connect(url).get();
        boolean isStop = false;

        // 解析文本
        Elements elements = doc.select("p");
        List<V2rayBean> list = parseTextTov2ary(elements);

        System.out.println("\n============");
        // for (V2rayBean ssr : list) {
        //     System.out.println(ssr.toSSRLink());
        //     if (openLink) {
        //         try {
        //             Desktop.getDesktop().browse(new URI(ssr.toSSRLink()));
        //             TimeUnit.SECONDS.sleep(1);
        //         } catch (Exception ignored) {
        //         }
        //     }
        // }
    }

    private List<SSRBean> parseText(Elements elements) {
        List<SSRBean> list = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d{2,3}.\\d{2,3}.\\d{2,3}.\\d{2,3}");
        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            String text = element.text();
            Matcher matcher = pattern.matcher(text);
            if (!matcher.find()) {
                continue;
            }

            String server = matcher.group();
            String port = elements.get(++i).text();
            String passwd = elements.get(++i).text();
            String method = elements.get(++i).text();
            String protocol = elements.get(++i).text();
            String obfs = elements.get(++i).text();
            SSRBean bean = new SSRBean();
            bean.setServer(server).setPort(getWord(port)).setPassword(getWord(passwd));
            bean.setMethod(getWord(method)).setProtocol(getWord(protocol)).setObfs(getWord(obfs));

            System.out.println("\n" + bean);
            list.add(bean);
        }
        return list;
    }

    private List<V2rayBean> parseTextTov2ary(Elements elements) {
        List<V2rayBean> list = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            String text = element.text();
            if (!text.contains("Address")) {
                continue;
            }

            String address = text;
            String port = elements.get(++i).text();
            String uid = elements.get(++i).text();
            String alterId = elements.get(++i).text();
            String security = elements.get(++i).text();
            String network = elements.get(++i).text();
            String headerType = elements.get(++i).text();
            String path = elements.get(++i).text();
            String tls = elements.get(++i).text();
            V2rayBean bean = new V2rayBean();
            bean.setAddress(getWord(address)).setPort(getWord(port)).setUuid(getWord(uid));
            bean.setAlterId(getWord(alterId)).setSecurity(getWord(security)).setNetwork(getWord(network));
            bean.setHeaderType(getWord(headerType)).setPath(getWord(path)).setTls(getWord(tls));
            System.out.println("\n" + bean);
            list.add(bean);
        }
        return list;
    }

    private List<SSRBean> parseImage(Elements images) {
        String url = "";
        for (Element image : images) {
            url = image.attr("src");
            if (url == null || url.length() == 0) {
                continue;
            }
            System.out.println("[#] img url: " + url);
            if (image.attr("data-canonical-url").contains("loli")) {
                url = image.attr("data-canonical-url");
                break;
            }
        }
        if (url == null || url.length() == 0) {
            return new ArrayList<>();
        }

        String imageName = "ssr.png";
        // String file = download(output, url);
        if (!url.startsWith("https://")) {
            url = "https://t1.free-air.org" + url;
        }

        File imageFile = download(new File(output, imageName), url);

        if (!imageFile.exists()) {
            System.out.println("[#] image file 不存在: " + imageFile.getAbsolutePath());
            return new ArrayList<>();
        }

        BaiduOcrResult words = BaiduOcrApi.parse(imageFile.getAbsolutePath());
        return SSRUtil.parse2(words);
    }

    public static File download(File newFile, String urlStr) {
        System.out.println("[#] 开始下载:" + urlStr);
        HttpURLConnection httpUrlConnection = null;
        InputStream fis = null;
        FileOutputStream fos = null;
        try {
            httpUrlConnection = getHttpInputStream(urlStr);
            fis = httpUrlConnection.getInputStream();
            fos = new FileOutputStream(newFile);
            byte[] temp = new byte[1024];
            int b;
            while ((b = fis.read(temp)) != -1) {
                fos.write(temp, 0, b);
                fos.flush();
            }
        } catch (Exception e) {
            System.out.println("[#] image file 下载失败: " + urlStr);
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (fis != null) {
                    fis.close();
                }
                if (httpUrlConnection != null) {
                    httpUrlConnection.disconnect();
                }
            } catch (IOException ignored) {
            }
        }
        System.out.println("[#] image file: " + newFile.getAbsolutePath());
        return newFile;
    }

    private static HttpURLConnection getHttpInputStream(String urlStr) throws IOException {
        HttpURLConnection httpUrlConnection = null;
        URL url = new URL(urlStr);
        httpUrlConnection = (HttpURLConnection) url.openConnection();
        httpUrlConnection.setConnectTimeout(10 * 1000);
        httpUrlConnection.setDoInput(true);
        httpUrlConnection.setDoOutput(true);
        httpUrlConnection.setUseCaches(false);
        httpUrlConnection.setRequestMethod("GET");
        httpUrlConnection.setRequestProperty("CHARSET", "UTF-8");
        httpUrlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)"); // 服务器端禁止抓取
        httpUrlConnection.connect();
        return httpUrlConnection;
    }

}
