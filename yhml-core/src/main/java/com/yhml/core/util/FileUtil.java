package com.yhml.core.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import lombok.extern.slf4j.Slf4j;

/**
 * User: Jfeng Date: 2017/3/17
 */
@Slf4j
public class FileUtil {

    private static String line_separator = System.getProperty("line.line_separator");
    private static String file_separator = File.separator;


    /**
     * 文件
     *
     * @param fileName
     * @return
     */
    public static boolean createFile(String fileName) {
        boolean ret = false;
        try {
            File file = new File(fileName);

            if (file.exists()) {
                file.delete();
            }

            ret = file.createNewFile();

        } catch (IOException e) {
            log.error("文件 {} 创建失败", fileName, e);
        }

        return false;
    }

    /**
     * 创建目录
     *
     * @param dest
     * @return
     */
    public static File createDir(String dest) {
        File dir = new File(dest);

        if (dir.exists()) {
            return dir;
        }

        if (!dest.endsWith(file_separator)) {// 结尾是否以"/"结束
            dest = dest + file_separator;
        }

        // 创建目标目录
        if (dir.mkdirs()) {
            log.info("目录创建成功: ", dest);
        } else {
            log.info("目录创建失败: ", dest);
        }

        return dir;
    }


    public static String read(String filePath) throws Exception {
        String line = null;
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            line = br.readLine();
            while (line != null) {
                // 修改某些行的内容
                // if (line.startsWith("bind")) {
                // sb.append(line).append(" start with bind");
                // } else {
                // sb.append(line);
                // }

                sb.append(line);
                sb.append(line_separator);
            }
        }

        return sb.toString();
    }

    /**
     * 将内容回写到文件中
     *
     * @param filePath
     * @param content
     */
    public static void write(String filePath, String content) {
        BufferedWriter bw = null;

        try {
            // 根据文件路径创建缓冲输出流
            bw = new BufferedWriter(new FileWriter(filePath));
            // 将内容写入文件中
            bw.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    bw = null;
                }
            }
        }
    }

    public static void fileAppender(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line = null;

        // 一行一行的读
        StringBuilder sb = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append("\r\n");
        }

        reader.close();

        // 写回去
        RandomAccessFile mm = new RandomAccessFile(fileName, "rw");
        mm.writeBytes(sb.toString());
        mm.close();
    }

    public static Document parseXML(String filePath) throws FileNotFoundException, DocumentException {
        SAXReader reader = new SAXReader();
        reader.setEncoding(StandardCharsets.UTF_8.name());
        File file = EnvironmentUtils.getFileAbsolutePath(filePath);
        return reader.read(file);
    }

    /**
     * 查询 file 目录下 filename 的文件
     *
     * @param file
     * @param filename
     * @param list 返回 files
     */
    public static void findFile(File file, String filename, List<File> list) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File subFile : files) {
                findFile(subFile, filename, list);
            }
        } else {
            if (file.getAbsolutePath().contains(filename)) {
                list.add(file);
            }
        }
    }


    // public static List<Map<String, String>> readXml(String filePath) {
    //     List<Map<String, String>> list = new ArrayList<>();
    //
    //     try {
    //         Document doc = parseXML(filePath);
    //
    //         for (Element ele : doc.getRootElement().elements()) {
    //             Map<String, String> resultMap = new HashMap<>();
    //             list.add(resultMap);
    //             ele.attributes().forEach(attribute -> {
    //                 resultMap.put(attribute.getName(), attribute.getValue());
    //             });
    //         }
    //     } catch (FileNotFoundException | DocumentException e) {
    //         log.error("解析xml文件发生错误", e);
    //     }
    //
    //     return list;
    // }

}
