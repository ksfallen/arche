package com.test;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author: Jfeng
 * @date: 2018/11/21
 */
public class GPhotoDownload {

    private static final String IMGURL_REG = "<img.*src=(.*?)[^>]*?>";
    private static final String IMGSRC_REG = "[a-zA-z]+://[^\\s]*";


    public static void main(String[] args) throws Exception {
        //获得html文本内容
        // String  url = "http://g.co/fb/yypx2";
        String  url = "https://plus.google.com/photos/113684854375405108383/albums/6087831903821258689/6087831904726072418";

        Document doc = Jsoup.connect(url).get();

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
    private static String getHtml(String url)throws Exception{
        URL url1=new URL(url);//使用java.net.URL
        URLConnection connection=url1.openConnection();//打开链接
        InputStream in=connection.getInputStream();//获取输入流
        InputStreamReader isr=new InputStreamReader(in);//流的包装
        BufferedReader br=new BufferedReader(isr);

        String line;
        StringBuilder sb=new StringBuilder();
        while((line=br.readLine())!=null){//整行读取
            sb.append(line,0,line.length());//添加到StringBuffer中
            sb.append('\n');//添加换行符
        }
        //关闭各种流，先声明的后关闭
        br.close();
        isr.close();
        in.close();
        return sb.toString();
    }

    private static List<String> getImageUrl(String html){
        Matcher matcher= Pattern.compile(IMGURL_REG).matcher(html);
        List<String>listimgurl= new ArrayList<>();
        while (matcher.find()){
            listimgurl.add(matcher.group());
        }
        return listimgurl;
    }


    private static List<String> getImageSrc(List<String> listimageurl){
        List<String> listImageSrc= new ArrayList<>();
        for (String image:listimageurl){
            Matcher matcher=Pattern.compile(IMGSRC_REG).matcher(image);
            while (matcher.find()){
                listImageSrc.add(matcher.group().substring(0, matcher.group().length()-1));
            }
        }
        return listImageSrc;
    }


    private static void download(List<String> listImgSrc) {
        try {
            //开始时间
            Date begindate = new Date();
            for (String url : listImgSrc) {
                //开始时间
                Date begindate2 = new Date();
                String imageName = url.substring(url.lastIndexOf("/") + 1);
                URL uri = new URL(url);
                InputStream in = uri.openStream();
                FileOutputStream fo = new FileOutputStream(new File("src/res/"+imageName));//文件输出流
                byte[] buf = new byte[1024];
                int length = 0;
                System.out.println("开始下载:" + url);
                while ((length = in.read(buf, 0, buf.length)) != -1) {
                    fo.write(buf, 0, length);
                }
                //关闭流
                in.close();
                fo.close();
                System.out.println(imageName + "下载完成");
                //结束时间
                Date overdate2 = new Date();
                double time = overdate2.getTime() - begindate2.getTime();
                System.out.println("耗时：" + time / 1000 + "s");
            }
            Date overdate = new Date();
            double time = overdate.getTime() - begindate.getTime();
            System.out.println("总耗时：" + time / 1000 + "s");
        } catch (Exception e) {
            System.out.println("下载失败");
        }
    }
}
