package com.yhml.core.servlet;// package com.yhml.common.servlet;
//
// import java.io.PrintWriter;
// import java.io.Writer;
//
// /**
// * @author: Jianfeng.Hu
// * @date: 2017/10/3
// */
// public class SimpleWriter extends PrintWriter{
//
// private StringBuilder buffer;
//
//
// public SimpleWriter(Writer out) {
// super(out);
// buffer = new StringBuilder();
// }
//
// @Override
// public void write(char[] buf, int off, int len) {
// // super.write(buf, off, len);
// char[] dest = new char[len];
// System.arraycopy(buf, off, dest, 0, len);
// buffer.append(dest);
// }
//
// /**
// * writer 内容
// * @return
// */
// public String getContent(){
// return buffer.toString();
// }
//
// }
//
