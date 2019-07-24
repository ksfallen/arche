package com.yhml.core.util;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: Jfeng
 * @date: 2019-06-25
 */
@Slf4j
public class CommandUtil {
    public static void main(String[] args) throws URISyntaxException {
        String url = "https://www.baidu.com";
        CommandUtil.browse(new URI(url));
    }

    /**
     * 打开浏览器
     *
     * @param uri uri地址
     * @return 是否成功
     */
    public static boolean browse(URI uri) {
        if (openSystemSpecific(uri.toString())) {
            return true;
        }

        return browseDESKTOP(uri);
    }

    /**
     * 打开文件
     *
     * @param file 文件
     * @return 是否成功
     */
    public static boolean open(File file) {
        if (openSystemSpecific(file.getPath())) {
            return true;
        }
        return openDESKTOP(file);
    }

    /**
     * 编辑指令
     *
     * @param file 文件
     * @return 是否成功
     */
    public static boolean edit(File file) {
        // you can try something like
        // runCommand("gimp", "%s", file.getPath())
        // based on user preferences.
        if (openSystemSpecific(file.getPath())) {
            return true;
        }
        return editDESKTOP(file);
    }

    /**
     * 打开系统特殊的指令
     *
     * @param what 指令
     * @return 是否成功
     */
    private static boolean openSystemSpecific(String what) {
        EnumOS os = getOs();
        if (os.isLinux()) {
            if (runCommand("kde-open", "%s", what))
                return true;
            if (runCommand("gnome-open", "%s", what))
                return true;
            if (runCommand("xdg-open", "%s", what))
                return true;
        }
        if (os.isMac()) {
            if (runCommand("open", "%s", what))
                return true;
        }
        if (os.isWindows()) {
            return runCommand("explorer", "%s", what);
        }
        return false;
    }

    /**
     * @param uri URI
     * @return boolean
     */
    private static boolean browseDESKTOP(URI uri) {
        log.info("Trying to use Desktop.getDesktop().browse() with " + uri.toString());
        try {
            if (!Desktop.isDesktopSupported()) {
                log.error("Platform is not supported.");
                return false;
            }
            if (!Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                log.error("BROWSE is not supported.");
                return false;
            }
            Desktop.getDesktop().browse(uri);
            return true;
        } catch (Throwable t) {
            log.error("Error using desktop browse.", t);
            return false;
        }
    }

    /**
     * @param file File
     * @return boolean
     */
    private static boolean openDESKTOP(File file) {
        log.info("Trying to use Desktop.getDesktop().open() with " + file.toString());
        try {
            if (!Desktop.isDesktopSupported()) {
                log.error("Platform is not supported.");
                return false;
            }
            if (!Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                log.error("OPEN is not supported.");
                return false;
            }
            Desktop.getDesktop().open(file);
            return true;
        } catch (Throwable t) {
            log.error("Error using desktop open.", t);
            return false;
        }
    }

    /**
     * 调用jdk工具类
     *
     * @param file File
     * @return boolean
     */
    private static boolean editDESKTOP(File file) {
        log.info("Trying to use Desktop.getDesktop().edit() with " + file);
        try {
            if (!Desktop.isDesktopSupported()) {
                log.error("Platform is not supported.");
                return false;
            }
            if (!Desktop.getDesktop().isSupported(Desktop.Action.EDIT)) {
                log.error("EDIT is not supported.");
                return false;
            }
            Desktop.getDesktop().edit(file);
            return true;
        } catch (Throwable t) {
            log.error("Error using desktop edit.", t);
            return false;
        }
    }

    /**
     * 运行命令
     *
     * @param command 指令
     * @param args    参数
     * @param file    文件
     * @return 是否
     */
    private static boolean runCommand(String command, String args, String file) {
        log.info("Trying to exec:\n cmd = {}\n args = {}\n {} = ", command, args, file);
        String[] parts = prepareCommand(command, args, file);
        try {
            Process p = Runtime.getRuntime().exec(parts);
            if (p == null)
                return false;
            try {
                int retval = p.exitValue();
                if (retval == 0) {
                    log.error("Process ended immediately.");
                    return false;
                }
                log.error("Process crashed.");
                return false;
            } catch (IllegalThreadStateException itse) {
                log.error("Process is running.");
                return true;
            }
        } catch (IOException e) {
            log.error("Error running command.", e);
            return false;
        }
    }

    /**
     * 准备命令
     *
     * @param command 指令
     * @param args    参数
     * @param file    文件
     * @return string array
     */
    private static String[] prepareCommand(String command, String args, String file) {
        List<String> parts = new ArrayList<>();
        parts.add(command);
        if (args != null) {
            for (String s : args.split(" ")) {
                s = String.format(s, file); // put in the filename thing
                parts.add(s.trim());
            }
        }
        return parts.toArray(new String[0]);
    }

    public enum EnumOS {
        linux,
        macos,
        solaris,
        unknown,
        windows;

        /**
         * @return 是否linux
         */
        public boolean isLinux() {
            return this == linux || this == solaris;
        }

        /**
         * @return 是否mac
         */
        public boolean isMac() {
            return this == macos;
        }

        /**
         * @return 是否windows
         */
        public boolean isWindows() {
            return this == windows;
        }
    }

    /**
     * @return EnumOS
     */
    public static EnumOS getOs() {
        String s = System.getProperty("os.name").toLowerCase();
        if (s.contains("win")) {
            return EnumOS.windows;
        }
        if (s.contains("mac")) {
            return EnumOS.macos;
        }
        if (s.contains("solaris")) {
            return EnumOS.solaris;
        }
        if (s.contains("sunos")) {
            return EnumOS.solaris;
        }
        if (s.contains("linux")) {
            return EnumOS.linux;
        }
        if (s.contains("unix")) {
            return EnumOS.linux;
        }
        return EnumOS.unknown;
    }
}
