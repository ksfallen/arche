package com.yhml.core.servlet;// package com.yhml.common.servlet;
//
// import java.io.IOException;
// import java.io.PrintWriter;
//
// import javax.servlet.ServletOutputStream;
// import javax.servlet.http.HttpServletResponse;
// import javax.servlet.http.HttpServletResponseWrapper;
//
// /**
// * 包装类 可以获取Response内容
// * 使用方法: 在Filter使用
// * SimpleResponseWrapper responseWrapper = new SimpleResponseWrapper((HttpServletResponse) response);
// * chain.doFilter(request, responseWrapper);
// * SimpleWriter writer = responseWrapper.getSimpleWriter();
// *
// * 获取 response 中的内容
// * String content = writer.getContent();
// *
// * ...业务处理
// *
// * 在将结果写回 writer
// * response.getWriter().write(content);
// *
// * @author: Jianfeng.Hu
// * @date: 2017/10/3
// */
// public class SimpleResponseWrapper extends HttpServletResponseWrapper {
// private SimpleServletOutputStream outputStream;
// private SimpleWriter writer;
//
//
// /**
// * Constructs a response adaptor wrapping the given response.
// *
// * @param response
// * @throws IllegalArgumentException if the response is null
// */
// public SimpleResponseWrapper(HttpServletResponse response) {
// super(response);
// }
//
// @Override
// public ServletOutputStream getOutputStream() throws IOException {
// if (outputStream == null) {
// outputStream = new SimpleServletOutputStream(super.getOutputStream());
// }
//
// return outputStream;
// }
//
// @Override
// public PrintWriter getWriter() throws IOException {
// SimpleWriter writer = new SimpleWriter(super.getWriter());
// return writer;
// }
//
// public SimpleWriter getSimpleWriter() {
// return writer;
// }
//
// public SimpleServletOutputStream getSimpleServletOutputStream() {
// return this.outputStream;
// }
//
// public String getContent() {
// return writer != null ? writer.getContent() : "";
// }
//
// }
