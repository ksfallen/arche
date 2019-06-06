// package com.yhml.core.util;
//
// import java.io.File;
// import java.io.FileInputStream;
// import java.io.IOException;
// import java.io.InputStream;
// import java.nio.charset.StandardCharsets;
// import java.security.KeyStore;
// import java.security.KeyStoreException;
// import java.security.NoSuchAlgorithmException;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Map;
// import java.util.Map.Entry;
// import java.util.concurrent.ConcurrentHashMap;
//
// import javax.net.ssl.SSLContext;
// import javax.servlet.http.HttpServletResponse;
//
// import org.apache.commons.collections.MapUtils;
// import org.apache.commons.lang3.StringUtils;
// import org.apache.http.Header;
// import org.apache.http.HttpEntity;
// import org.apache.http.HttpHost;
// import org.apache.http.client.entity.EntityBuilder;
// import org.apache.http.client.methods.CloseableHttpResponse;
// import org.apache.http.client.methods.HttpGet;
// import org.apache.http.client.methods.HttpPost;
// import org.apache.http.client.methods.HttpUriRequest;
// import org.apache.http.config.Registry;
// import org.apache.http.config.RegistryBuilder;
// import org.apache.http.conn.socket.ConnectionSocketFactory;
// import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
// import org.apache.http.conn.socket.PlainConnectionSocketFactory;
// import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
// import org.apache.http.conn.ssl.SSLContextBuilder;
// import org.apache.http.conn.ssl.SSLContexts;
// import org.apache.http.entity.ContentType;
// import org.apache.http.entity.mime.MultipartEntityBuilder;
// import org.apache.http.impl.client.CloseableHttpClient;
// import org.apache.http.impl.client.HttpClientBuilder;
// import org.apache.http.impl.client.HttpClients;
// import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
// import org.apache.http.util.EntityUtils;
//
// import lombok.Getter;
// import lombok.Setter;
// import lombok.extern.slf4j.Slf4j;
//
// /**
//  * 功能说明: HttpClient工具类；支持http/https、单向/双向认证；默认执行后自动关闭
//  */
// @Slf4j
// @Getter
// @Setter
// public class HttpClientUtil3 {
//
//     private static final ContentType DEFAULT_TEXT_UTF8 = ContentType.DEFAULT_TEXT.withCharset(StandardCharsets.UTF_8);
//     private static Map<String, Registry<ConnectionSocketFactory>> registryMap = new ConcurrentHashMap<>();
//     private static String[] protocols = {"TLSv1", "TLSv1.1", "TLSv1.2", "SSLv2Hello", "SSLv3"};
//
//     private HttpClientBuilder httpBuilder;
//     // private BasicCookieStore cookieStore;
//     private CloseableHttpClient httpclient;
//     /**
//      * 设置连接自动关闭，默认为true；<b>
//      * 若设置为false，则连续调用可保持会话，使用后显示调用close方法； 保持会话时传入header不会生效
//      */
//     private boolean automaticClose = true;
//
//     private HttpClientUtil3() {
//     }
//
//     /**
//      * 获取默认私钥实例，用于http请求、https单向认证请求、<br>
//      * 或默认证书的https双向认证请求 默认私钥未配置时，<br>
//      * 返回实例仅适用于http请求、https单向认证请求
//      *
//      * @return
//      */
//     public static HttpClientUtil3 getInstance() {
//         String keyStore = System.getProperty("ssl.keyStore");
//         String storePass = System.getProperty("ssl.storePass");
//         String storeType = System.getProperty("ssl.storeType");
//         return getInstance(keyStore, storePass, storeType);
//     }
//
//     /**
//      * 获取指定私钥实例，用于指定证书的https双向认证请求
//      *
//      * @param keyStore
//      * @param keyPass
//      * @param storeType JKS--默认,JCEKS, PKCS12 and PKCS11
//      *
//      * @return
//      */
//     public static HttpClientUtil3 getInstance(String keyStore, String keyPass, String storeType) {
//         HttpClientUtil3 util = new HttpClientUtil3();
//         Registry<ConnectionSocketFactory> reg = util.registry(keyStore, keyPass, storeType);
//         // 设置连接管理器
//         PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(reg);
//         connManager.setMaxTotal(200);
//         HttpClientBuilder httpBuilder = HttpClients.custom();
//         httpBuilder.setConnectionManager(connManager);
//
//         // util.cookieStore = new BasicCookieStore();
//         // httpBuilder.setDefaultCookieStore(util.cookieStore);
//         util.httpBuilder = httpBuilder;
//         util.httpclient = httpBuilder.build();
//
//         return util;
//     }
//
//     /**
//      * 静态同步管理，避免重复加载证书
//      *
//      * @param keyStore
//      * @param keyPass
//      * @param storeType
//      *
//      * @return
//      */
//     private Registry<ConnectionSocketFactory> registry(String keyStore, String keyPass, String storeType) {
//         Registry<ConnectionSocketFactory> registry = null;
//         if (registryMap.containsKey(keyStore)) {
//             registry = registryMap.getString(keyStore);
//         } else {
//             RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>builder();
//             ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();
//             registryBuilder.register("http", plainSF);
//             // 指定信任密钥存储对象和连接套接字工厂
//             try {
//                 SSLContextBuilder sslBuilder = SSLContexts.custom().useTLS();
//                 this.trustAllCertificate(sslBuilder);
//                 if (!StringUtils.isEmpty(keyStore) && !StringUtils.isEmpty(keyPass)) {
//                     loadClientCertificate(sslBuilder, keyStore, keyPass, storeType);
//                 }
//                 SSLContext sslContext = sslBuilder.build();
//
//                 LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext, protocols, null,
//                         SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//                 registryBuilder.register("https", sslSF);
//             } catch (Exception e) {
//                 log.error("信任服务器证书发生错误", e);
//             }
//             registry = registryBuilder.build();
//             registryMap.put(keyStore, registry);
//         }
//         registryMap.notify();
//
//
//         return registry;
//     }
//
//     /**
//      * 加载客户端证书
//      *
//      * @param sslBuilder
//      * @param keyStore
//      * @param keyPass
//      * @param storeType
//      */
//     private void loadClientCertificate(SSLContextBuilder sslBuilder, String keyStore, String keyPass, String storeType) {
//         storeType = StringUtils.defaultIfBlank(storeType, KeyStore.getDefaultType());
//         try (FileInputStream instream = new FileInputStream(new File(storeType))) {
//             KeyStore keystore = KeyStore.getInstance(storeType);
//             keystore.load(instream, keyPass.toCharArray());
//             sslBuilder.loadKeyMaterial(keystore, keyPass.toCharArray()).build();
//         } catch (Exception e) {
//             log.error("加载客户端证书发生错误", e);
//         }
//     }
//
//     /**
//      * 信任所有服务器证书
//      *
//      * @param sslBuilder
//      *
//      * @throws KeyStoreException
//      * @throws NoSuchAlgorithmException
//      */
//     private void trustAllCertificate(SSLContextBuilder sslBuilder) throws KeyStoreException, NoSuchAlgorithmException {
//         KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
//         // 信任所有
//         sslBuilder.loadTrustMaterial(trustStore, (chain, authType) -> true);
//     }
//
//     /**
//      * 构建http请求实体，支持字符串及文件
//      *
//      * @param params
//      *
//      * @return
//      */
//     private HttpEntity buildHttpEntry(Map<String, Object> params) {
//         MultipartEntityBuilder source = MultipartEntityBuilder.builder();
//         source.setCharset(StandardCharsets.UTF_8);
//
//         if (MapUtils.isEmpty(params)) {
//             return source.build();
//         }
//         for (Entry<String, Object> entry : params.entrySet()) {
//             String pname = entry.getKey();
//             Object pvalue = entry.getValue();
//
//             // 如果pvalue为null，就可以不放到http请求的参数中
//             if (pvalue == null) {
//                 continue;
//             }
//
//             if (pvalue instanceof String) {
//                 source.addTextBody(pname, String.valueOf(pvalue), DEFAULT_TEXT_UTF8);
//             } else if (pvalue instanceof File) {
//                 source.addBinaryBody(pname, (File) pvalue);
//             } else if (pvalue instanceof InputStream) {
//                 source.addBinaryBody(pname, (InputStream) pvalue);
//             } else if (pvalue instanceof List) {
//                 for (Object obj : (List) pvalue) {
//                     source.addTextBody(pname, String.valueOf(obj), DEFAULT_TEXT_UTF8);
//                 }
//             } else if (pvalue.getClass().isArray()) {
//                 Object[] array = (Object[]) pvalue;
//                 for (int i = 0; i < array.length; i++) {
//                     source.addTextBody(pname, String.valueOf(array[i]), DEFAULT_TEXT_UTF8);
//                 }
//             } else {// 兼容处理，long、int等基本类型
//                 source.addTextBody(pname, String.valueOf(pvalue), DEFAULT_TEXT_UTF8);
//             }
//         }
//
//         return source.build();
//     }
//
//     /**
//      * POST方式请求，支持http/https，支持提交字符串/文件
//      *
//      * @param url
//      * @param params
//      * @param headers
//      *
//      * @return
//      *
//      * @throws IOException
//      */
//     public String httpPost(String url, Map<String, Object> params, List<Header> headers) throws IOException {
//         HttpPost httppost = new HttpPost(url);
//         HttpEntity reqEntity = buildHttpEntry(params);
//         httppost.setEntity(reqEntity);
//         return execute(httppost, headers);
//     }
//
//     /**
//      * POST方式请求，支持http/https，支持提交xml
//      *
//      * @param url
//      * @param xml
//      * @param headers
//      *
//      * @return
//      *
//      * @throws IOException
//      */
//     public String httpPost(String url, String xml, List<Header> headers) throws IOException {
//         HttpPost httppost = new HttpPost(url);
//         HttpEntity reqEntity = buildXmlHttpEntry(xml);
//         httppost.setEntity(reqEntity);
//         return execute(httppost, headers);
//     }
//
//     /**
//      * 构建xml-http请求实体
//      *
//      * @param params
//      *
//      * @return
//      */
//     private HttpEntity buildXmlHttpEntry(String xml) {
//         EntityBuilder source = EntityBuilder.builder();
//         source.setContentEncoding(StandardCharsets.UTF_8.name());
//         source.setContentType(ContentType.APPLICATION_XML);
//         source.setText(xml);
//         return source.build();
//     }
//
//     /**
//      * POST方式请求，支持http/https，支持提交bytes类型
//      *
//      * @param url
//      * @param binary
//      * @param headers
//      *
//      * @return
//      *
//      * @throws IOException
//      */
//     public String httpPost(String url, byte[] binary, List<Header> headers) throws IOException {
//         HttpPost httppost = new HttpPost(url);
//         HttpEntity reqEntity = buildBinaryHttpEntry(binary);
//         httppost.setEntity(reqEntity);
//         return execute(httppost, headers);
//     }
//
//     /**
//      * 构建bytes-http请求实体
//      *
//      * @param params
//      *
//      * @return
//      */
//     private HttpEntity buildBinaryHttpEntry(byte[] binary) {
//         EntityBuilder source = EntityBuilder.builder();
//         source.setContentEncoding(StandardCharsets.UTF_8.name());
//         source.setBinary(binary);
//         return source.build();
//     }
//
//     /**
//      * GET方式请求，支持http/https
//      *
//      * @param url
//      *
//      * @return
//      *
//      * @throws IOException
//      */
//     public String httpGet(String url) throws IOException {
//         HttpGet httpget = new HttpGet(url);
//         return execute(httpget, null);
//     }
//
//     /**
//      * GET方式请求，支持http/https
//      *
//      * @param url
//      * @param headers
//      *
//      * @return
//      *
//      * @throws IOException
//      */
//     public String httpGet(String url, List<Header> headers) throws IOException {
//         HttpGet httpget = new HttpGet(url);
//         return execute(httpget, headers);
//     }
//
//     /**
//      * 执行http请求，解析输出流为字节
//      *
//      * @param request
//      * @param headers 不能为null，否则不能把执行post请求得到的header放进去
//      *
//      * @return
//      *
//      * @throws IOException
//      */
//     private String execute(HttpUriRequest request, List<Header> headers) throws IOException {
//         if (headers != null && headers.size() > 0) {
//             // 添加请求头
//             for (Header header : headers) {
//                 request.addHeader(header);
//             }
//         } else if (headers == null) {
//             // 如果入参的headers为null,后面headers.add(header)是返回不出去的。这一句只是为了避免空指针异常
//             headers = new ArrayList<>();
//         }
//
//
//         try (CloseableHttpResponse response = httpclient.execute(request)) {
//
//             for (Header header : response.getAllHeaders()) {
//                 if ("Set-Cookie".equalsIgnoreCase(header.getName())) {
//                     // 回写返回的cookie
//                     headers.add(header);
//                 }
//             }
//             if (response.getStatusLine().getStatusCode() == HttpServletResponse.SC_OK) {
//                 HttpEntity entity = response.getEntity();
//                 String data = EntityUtils.toString(entity, StandardCharsets.UTF_8);
//                 return data;
//             } else {
//                 throw new RuntimeException("网络请求发生异常:" + response.getStatusLine());
//             }
//         } finally {
//             if (automaticClose) {
//                 close();
//             }
//         }
//     }
//
//     /**
//      * 设置连接自动关闭，默认为true； 若设置为false，则连续调用可保持会话，使用后显示调用close方法； 保持会话时传入header不会生效
//      *
//      * @param autoClose
//      */
//     public void setAutomaticClose(boolean autoClose) {
//         this.automaticClose = autoClose;
//     }
//
//     /**
//      * 获取cookie存储
//      *
//      */
//     // public BasicCookieStore getCookieStore() {
//     // return cookieStore;
//     // }
//
//     /**
//      * 代理地址
//      *
//      * @param hostname
//      * @param port
//      */
//     public void setProxyHost(String hostname, int port) {
//         HttpHost host = new HttpHost(hostname, port);
//         httpclient = httpBuilder.setProxy(host).build();
//     }
//
//     public void close() {
//         try {
//             if (httpclient != null)
//                 httpclient.close();
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
//
//     /**
//      * 垃圾回收时关闭
//      */
//     @Override
//     public void finalize() {
//         close();
//     }
// }
