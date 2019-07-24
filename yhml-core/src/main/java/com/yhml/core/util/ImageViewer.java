// package com.yhml.core.util;
//
// import java.awt.*;
// import java.awt.event.WindowAdapter;
// import java.awt.event.WindowEvent;
//
// /**
//  * 查看图形的窗口
//  * 用于显示图形的窗口
//  */
// public class ImageViewer extends Frame {
//     private Image image;
//     /**
//      * 显示一个图像
//      * @param viewMe
//      */
//     public ImageViewer(Image viewMe) {
//         image = viewMe;
//         MediaTracker mediaTracker = new MediaTracker(this);
//         mediaTracker.addImage(image, 0);
//         try {
//             mediaTracker.waitForID(0);
//         } catch (InterruptedException ie) {
//             ie.printStackTrace();
//             System.exit(1);
//         }
//         addWindowListener(new WindowAdapter() {
//             public void windowClosing(WindowEvent e) {
//                 System.exit(0);
//             }
//         });
//         //窗口适应图像大小
//         setSize(image.getWidth(null), image.getHeight(null));
//         //窗口标题
//         setTitle("Viewing Image from Clipboard");
//         setVisible(true);
//     }
//     public void paint(Graphics graphics) {
//         graphics.drawImage(image, 0, 0, null);
//     }
//
// }
