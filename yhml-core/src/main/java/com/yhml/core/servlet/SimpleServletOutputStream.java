package com.yhml.core.servlet;// package com.yhml.common.servlet;
//
// import java.io.IOException;
//
// import javax.servlet.ServletOutputStream;
// import javax.servlet.WriteListener;
//
// /**
// * @author: Jianfeng.Hu
// * @date: 2017/10/3
// */
// public class SimpleServletOutputStream extends ServletOutputStream {
// // private final DataOutputStream output;
// private ServletOutputStream output;
//
//
// public SimpleServletOutputStream(ServletOutputStream output) {
// // this.output = new DataOutputStream(output);
// this.output = output;
// }
//
//
// @Override
// public void write(int b) throws IOException {
// output.write(b);
// }
//
// @Override
// public void write(byte[] arg0, int arg1, int arg2) throws IOException {
// output.write(arg0, arg1, arg2);
// }
//
// @Override
// public void write(byte[] arg0) throws IOException {
// output.write(arg0);
// }
//
// @Override
// public boolean isReady() {
// return false;
// }
//
// @Override
// public void setWriteListener(WriteListener listener) {
//
// }
// }
