package com.yhml.core.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URLClassLoader;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * 环境变量
 */
@Slf4j
public class EnvironmentUtils {

    private static final String CLASSPATH_PREFIX = "classpath:";


    /**
     * 获取 WEB-INF Class 目录地址
     */
    public static String getWebInfClassPath() {
        return Thread.currentThread().getContextClassLoader().getResource("/").getPath();
    }

    /**
     * 获取 classpath 路径
     *
     * @return
     */
    public static String getClassPath() {
        return Thread.currentThread().getContextClassLoader().getResource("").getPath();
    }

    public static String getWebRootPath() {
        HttpServletRequest request = RequestUtil.getRequest();
        if (request != null) {
            return request.getServletContext().getRealPath("/");
        }

        File file = new File(getClassPath());
        return file.getParentFile().getParentFile().getAbsolutePath();
    }

    /**
     * 获取classpath 下的文件
     *
     * @param filename
     * @return
     * @throws FileNotFoundException
     */
    public static File getFileAbsolutePath(String filename) throws FileNotFoundException {
        if (filename.startsWith(CLASSPATH_PREFIX)) {
            filename = filename.substring(CLASSPATH_PREFIX.length());
        }

        if (filename.startsWith("/")) {
            filename = filename.substring(1);
        }

        String fileUrl = getClassPath() + filename;
        File file = new File(fileUrl);

        if (file.exists()) {
            return file;
        } else {
            throw new FileNotFoundException(filename + " Not Found in ClassPath ...");
        }

    }

    /**
     * 获取应用依赖cairh jar版本
     *
     * @return
     */
    // private static Map<String, String> getCairhAppJarVersion() {
    //     Map<String, String> versionMap = new HashMap<>();
    //     URL[] parentUrls = getURLClassLoader().getURLs();
    //     for (URL url : parentUrls) {
    //         if (!"file".equals(url.getProtocol())) {
    //             continue;
    //         }
    //         URI entryURI;
    //         try {
    //             entryURI = url.toURI();
    //         } catch (URISyntaxException e) {
    //             continue;
    //         }
    //         File entry = new File(entryURI);
    //         if (entry.getPath().toLowerCase().indexOf(".jar") <= 0 || !entry.getName().toLowerCase().contains("cairh-")) {
    //             continue;
    //         }
    //         try {
    //             JarFile jf = new JarFile(entry.getPath());
    //             Manifest mf = jf.getManifest();
    //             if (mf == null) {
    //                 continue;
    //             }
    //             Attributes attrs = mf.getMainAttributes();
    //             if (attrs.getValue("Project-Version") != null) {
    //                 log.info(entry.getName() + ":" + mf.getMainAttributes().getValue("Project-Version"));
    //                 versionMap.put(entry.getName(), attrs.getValue("Project-Version") + "." + attrs.getValue("Build-Time"));
    //             }
    //         } catch (IOException e) {
    //             log.error("获取应用JAR MANIFEST.MF异常", e);
    //         }
    //     }
    //     return versionMap;
    // }

    private static URLClassLoader getURLClassLoader() {
        URLClassLoader ucl = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        if (!(loader instanceof URLClassLoader)) {
            loader = EnvironmentUtils.class.getClassLoader();
            if (loader instanceof URLClassLoader) {
                ucl = (URLClassLoader) loader;
            }
        } else {
            ucl = (URLClassLoader) loader;
        }

        return ucl;
    }


    /**
     * 取文件系统根目录全路径
     *
     * @return
     */
    // public static String getFileHomePath() {
    //     String file_sys_home_dir = PropertiesUtil.getString("homedir.uploader.cdn", "/");
    //     if (!file_sys_home_dir.endsWith("/") && !file_sys_home_dir.endsWith("\\")) {
    //         file_sys_home_dir = file_sys_home_dir + "/";
    //     }
    //     return file_sys_home_dir;
    // }

}
