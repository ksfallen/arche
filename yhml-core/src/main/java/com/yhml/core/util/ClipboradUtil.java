package com.yhml.core.util;

import java.awt.*;
import java.awt.datatransfer.*;

/**
 * 系统剪贴板
 */
public class ClipboradUtil {

    public static void main(String[] args) {
        // try {
        //     ImageViewer im = new ImageViewer(getImageFromClipboard());
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
    }

    /**
     * 从指定的剪切板中获取文本内容
     * 本地剪切板使用 Clipborad cp = new Clipboard("clip1"); 来构造
     * 系统剪切板使用  Clipboard sysc = Toolkit.getDefaultToolkit().getSystemClipboard();
     * 剪切板的内容   getContents(null); 返回Transferable
     */
    protected static String getClipboardText() throws Exception {
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();//获取系统剪贴板
        // 获取剪切板中的内容
        Transferable trans = clip.getContents(null);
        if (trans != null) {
            // 检查内容是否是文本类型
            if (trans.isDataFlavorSupported(DataFlavor.stringFlavor))
                return (String) trans.getTransferData(DataFlavor.stringFlavor);
        }
        return StringUtil.EMPTY;
    }

    /**
     * 往剪切板写文本数据
     */
    public static void setClipboardText(String content) {
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable trans = new StringSelection(content);
        clip.setContents(trans, null);
    }

    /**
     * 从剪切板读取图像
     */
    public static Image getImageFromClipboard() throws Exception {
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable trans = clip.getContents(null);
        if (trans != null && trans.isDataFlavorSupported(DataFlavor.imageFlavor)) {
            return (Image) trans.getTransferData(DataFlavor.imageFlavor);
        }
        return null;
    }

    // 写图像到剪切板
    protected static void setClipboardImage2(final Image image) {
        Transferable trans = new Transferable() {

            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[]{DataFlavor.imageFlavor};
            }

            public boolean isDataFlavorSupported(DataFlavor flavor) {
                return DataFlavor.imageFlavor.equals(flavor);
            }

            public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
                if (isDataFlavorSupported(flavor))
                    return image;
                throw new UnsupportedFlavorException(flavor);
            }
        };
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(trans, null);
    }

}

