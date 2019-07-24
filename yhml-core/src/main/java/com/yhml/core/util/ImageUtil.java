package com.yhml.core.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.springframework.util.FileSystemUtils;

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


    /**
     * 删除图片
     *
     * @param path
     */
    public static void remove(String path) {
        FileSystemUtils.deleteRecursively(new File(path));
    }

    /**
     * 图片转换为二进制
     *
     * @param fileName
     * @return
     */
    @SneakyThrows
    public static String getImageBase64(String fileName) {
        byte[] bytes = getImageBinary(fileName);
        return Base64.encodeBase64String(bytes);
        //return encoder.encodeBuffer(bytes).trim();
    }

    @SneakyThrows(value = IOException.class)
    public static byte[] getImageBinary(String fileName) {
        File file = new File(fileName);

        if (!file.exists() || file.isDirectory()) {
            return new byte[0];
        }

        // 扩展名
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);

        BufferedImage bi = ImageIO.read(file);
        @Cleanup ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, ext, baos);

        byte[] bytes = baos.toByteArray();
        return bytes;
        //return encoder.encodeBuffer(bytes).trim();
    }

    /**
     * 将二进制转换为图片
     *
     * @param base64String 图片二进制流
     * @param fileName     文件名 全路径
     * @param type     图片类型 jpg,png,gif
     */
    @SneakyThrows(value = IOException.class)
    public static void saveImage(String base64String, String fileName, String type) {
        byte[] bytes1 = Base64.decodeBase64(base64String);
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
        BufferedImage image = ImageIO.read(bais);

        File w2 = new File(fileName);

        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, type, w2);

        // RandomAccessFile file = new RandomAccessFile(fileName, "rw");
        // file.write(bytes);
        // file.close();
    }

    public static void saveImage(String base64String, String fileName) {
        saveImage(base64String, fileName, "jpg");
    }

    /**
     * 根据图片地址获得数据的字节流
     *
     * @param imageUrl 网络连接地址
     * @return
     */
    public static byte[] getImageFromNetByUrl(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5 * 1000);

        // 通过输入流获取图片数据
        InputStream inStream = conn.getInputStream();
        return readInputStream(inStream);
    }

    /**
     * 从输入流中获取数据
     *
     * @param inStream
     * @return
     * @throws IOException
     */
    private static byte[] readInputStream(InputStream inStream) throws IOException {
        @Cleanup ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[10240];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        return outStream.toByteArray();
    }


    //获取HTML内容
    public static String getHtml(String url) throws Exception {
        URL url1 = new URL(url);//使用java.net.URL
        URLConnection connection = url1.openConnection();//打开链接
        @Cleanup InputStream in = connection.getInputStream();//获取输入流
        @Cleanup InputStreamReader isr = new InputStreamReader(in);//流的包装
        @Cleanup BufferedReader br = new BufferedReader(isr);

        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {//整行读取
            sb.append(line, 0, line.length());//添加到StringBuffer中
            sb.append('\n');//添加换行符
        }

        //关闭各种流，先声明的后关闭
        return sb.toString();
    }

    public static List<String> getImageUrl(String html) {
        Matcher matcher = Pattern.compile(IMGURL_REG).matcher(html);
        List<String> listimgurl = new ArrayList<>();
        while (matcher.find()) {
            listimgurl.add(matcher.group());
        }
        return listimgurl;
    }


    public static List<String> getImageSrc(List<String> listimageurl) {
        List<String> listImageSrc = new ArrayList<>();
        for (String image : listimageurl) {
            Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(image);
            while (matcher.find()) {
                listImageSrc.add(matcher.group().substring(0, matcher.group().length() - 1));
            }
        }
        return listImageSrc;
    }


    public static String download(String output, String url) {
        String pathname = "";
        try {
            if (StringUtil.isBlank(url)) {
                log.info("url is blank:" + url);
                return pathname;
            }

            //开始时间
            long t = System.currentTimeMillis();

            log.info("开始下载:" + url);

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
            log.info("imageName:{}, time:{}", pathname, System.currentTimeMillis() - t);

        } catch (IOException ex) {
            log.error("下载失败", ex);
        }

        return pathname;
    }

    private static void download(String path, List<String> list) {
        long t1 = System.currentTimeMillis();

        list.forEach(e -> download(path, e));

        log.info("total time:{}", System.currentTimeMillis() - t1);
    }

    /**
     * 用于读取图像文件并生成Image对象
     */
    public static Image getImageFromFile(String fileName) {
       return Toolkit.getDefaultToolkit().getImage(fileName);
    }
}
