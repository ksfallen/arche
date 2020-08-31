// package com.yhml.tools.money.handler;
//
// import com.yhml.tools.constants.ToolConstants;
// import org.jsoup.Jsoup;
// import org.jsoup.nodes.Element;
// import org.openqa.selenium.By;
// import org.openqa.selenium.Cookie;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.WebElement;
// import org.openqa.selenium.chrome.ChromeDriver;
// import org.openqa.selenium.chrome.ChromeOptions;
// import org.openqa.selenium.support.ui.ExpectedConditions;
// import org.openqa.selenium.support.ui.WebDriverWait;
// import cn.hutool.core.collection.CollectionUtil;
// import cn.hutool.core.io.file.FileReader;
// import cn.hutool.core.io.file.FileWriter;
// import cn.hutool.core.io.resource.ResourceUtil;
// import cn.hutool.core.lang.Assert;
// import cn.hutool.core.util.StrUtil;
// import cn.hutool.http.HttpRequest;
// import cn.hutool.http.HttpResponse;
//
// import java.net.HttpCookie;
// import java.net.URL;
// import java.util.*;
// import java.util.concurrent.TimeUnit;
// import java.util.stream.Collectors;
//
// import lombok.extern.slf4j.Slf4j;
//
// /**
//  * @author Jfeng
//  * @date 2020/8/25
//  */
// @Slf4j
// public class AlipayLogin {
//     private static WebDriver driver;
//     public static Map<String, String> headers = new HashMap<>();
//     private static Set<HttpCookie> httpCookies = new HashSet<>();
//
//     private static String BILL_URL = "https://consumeprod.alipay.com/record/advanced.htm";
//     // private static String LOGIN_URL = "https://auth.alipay.com/login/index.htm?goto=https%3A%2F%2Fwww.alipay.com%2F";
//     private static String LOGIN_URL = "https://auth.alipay.com/login/index.htm" + "?goto=" + BILL_URL;
//     private static URL resource = ResourceUtil.getResource("cookie.txt");
//
//     static {
//         headers.put("Host", "consumeprod.alipay.com");
//         headers.put("referer", "https://consumeprod.alipay.com/record/advanced.htm");
//         headers.put("user-agent", ToolConstants.USER_AGENT);
//         headers.put("upgrade-insecure-requests", "1");
//         headers.put("cookie", "");
//     }
//
//
//     public static void main(String[] args) {
//         // login();
//         // readCookie();
//         // System.out.println(getBillDetail("2020083122001482551418408134"));
//         // downloadBill();
//         // System.out.println(URLUtil.encode(Bill_Url));
//         // elements.click();
//         // WebElement username = driver.findElement(By.id("J-input-user"));
//         // WebElement passwd = driver.findElement(By.id("password_rsainput"));
//         // inputSlow(username, userName);
//         // inputSlow(passwd, password);
//     }
//
//     public static void downloadBill() {
//         try {
//             // WebElement element1 = driver.findElement(By.xpath("//a[contains(text(),'Excel格式')]"));
//             WebElement element = driver.findElement(By.cssSelector("span.ft-gray+a"));
//             element.click();
//             // TimeUnit.SECONDS.sleep(5);
//             // driver.get(BILL_URL);
//             // element = driver.findElement(By.cssSelector("span.ft-gray+a"));
//             // element.click();
//
//             // WebElement search = driver.findElement(By.cssSelector("#J-set-query-form"));
//             // element = driver.findElement(By.cssSelector("span.ft-gray+a"));
//             // search.click();
//         } catch (Exception e) {
//             log.error("账单下载失败", e);
//         } finally {
//             close();
//         }
//     }
//
//     public static void close() {
//         driver.quit();
//     }
//
//     private static void inputSlow(WebElement element, String text) {
//         Random random = new Random();
//         String[] ss = text.split("");
//         for (String s : ss) {
//             element.sendKeys(s);
//             try {
//                 int mills = random.nextInt(500) % (500 - 200 + 1) + 200;
//                 TimeUnit.MILLISECONDS.sleep(mills);
//             } catch (InterruptedException ignored) {
//             }
//         }
//     }
//
//     private static void sleepSec(long timeout) {
//         try {
//             TimeUnit.SECONDS.sleep(timeout);
//         } catch (InterruptedException e) {
//             e.printStackTrace();
//         }
//     }
//
//     private static boolean login() {
//         // String cookie = readCookie();
//         // getCookie();
//         readCookie();
//         HttpResponse response = getBillResponse();
//         boolean suc = response.getStatus() == 200;
//         Assert.isTrue(suc, "支付宝登录过期");
//         return suc;
//     }
//
//     private static HttpResponse getBillResponse() {
//         return HttpRequest.get(BILL_URL).addHeaders(headers).execute();
//     }
//
//     public static String getBillDetail(String tradeId) {
//         if (headers.get("cookie").equals("")) {
//             readCookie();
//         }
//         // String tradeTime = DateUtil.parseDateTime(model.getTradeTime()).toString(DatePattern.PURE_DATETIME_PATTERN);
//         String url = "https://consumeprod.alipay.com/record/detail/simpleDetail.htm?bizType=TRADE&bizInNo={}"; //&gmtBizCreate={}";
//         url = StrUtil.format(url, tradeId);
//         String html = HttpRequest.get(url).addHeaders(headers).execute().body();
//         if (StrUtil.isBlank(html)) {
//             // log.info("支付宝Cookie过期");
//             return "";
//         }
//         Element element = Jsoup.parse(html).select("div.detail-list").first();
//         if (element != null) {
//             element = element.selectFirst("span");
//         }
//         if (element == null) {
//             log.info("获取账户失败:{}", tradeId);
//         } else {
//             return element.text();
//         }
//         return "";
//     }
//
//     private static void getCookie() {
//         ChromeOptions options = new ChromeOptions();
//         // 后台运行浏览器
//         // options.addArguments("--headless");
//         // options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
//         driver = new ChromeDriver(options);
//         driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//         driver.get(LOGIN_URL);
//         WebDriverWait wait = new WebDriverWait(driver, 10);
//         wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".J-download-tip")));
//         // wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".personal-login")));
//         // WebElement element = driver.findElement(By.cssSelector(".personal-login"));
//         // element.click();
//         // saveCookies();
//         Set<Cookie> cookies = driver.manage().getCookies();
//         List<String> collect = cookies.stream().map(cookie -> cookie.getName() + "=" + cookie.getValue()).collect(Collectors.toList());
//
//         FileWriter fileWriter = new FileWriter(resource.getPath());
//         fileWriter.write(CollectionUtil.join(collect, ";"));
//     }
//
//     public static String readCookie() {
//         FileReader fileReader = new FileReader(resource.getPath());
//         String str = fileReader.readString();
//         if (str != null) {
//             str = str.replace("cookie: ", "").trim();
//             headers.put("cookie", str);
//         }
//         return str;
//     }
// }
