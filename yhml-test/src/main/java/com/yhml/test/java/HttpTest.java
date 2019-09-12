// package com.yhml.test.java;
//
// import com.yhml.core.concurrent.TaskUtil;
// import com.yhml.core.util.JsonUtil;
//
// import cn.hutool.core.util.RandomUtil;
// import cn.hutool.http.HttpUtil;
// import lombok.Getter;
// import lombok.Setter;
//
// /**
//  * @author: Jfeng
//  * @date: 2019-08-15
//  */
// public class HttpTest {
//     private static final String url = "https://abc-account.allcitygo.com/account/accConsume";
//
//     public static void main(String[] args) {
//
//         TaskUtil.timeTask(100, 10, () -> {
//             long time = System.currentTimeMillis();
//             long card = RandomUtil.randomLong(18080002126L, 18080009999L);
//
//             RequestBody body = new RequestBody();
//             body.setBizNo(time + "");
//             body.setCardNo(String.valueOf(card));
//             body.setTransTime("20190809105851");
//             body.setTransId("20190809105851");
//
//             // String body1 = JsonUtil.toJsonString(body);
//             // System.out.println(body);
//             String resp = HttpUtil.post(url, body.toString());
//             // System.out.println(resp);
//         });
//
//     }
// }
//
// @Getter
// @Setter
// class RequestBody {
//
//     /**
//      * amt : 1
//      * bizNo : 2019081412002126
//      * cardNo : 18080002126
//      * channelInfo : tsmpay
//      * creditAble : 1
//      * info : info
//      * merchantNo : 10000186
//      * openAccountId : 3202001000002582
//      * openCardMerchantNo : 10000186
//      * orderAmt : 1
//      * source : app
//      * terminalNo : test
//      * transTime : 20190809105851
//      * cityCode : 331000
//      * transId : 20190808164103
//      */
//
//     private String amt = "1";
//     private String bizNo;
//     private String cardNo;
//     private String channelInfo = "tsmpay";
//     private String creditAble = "1";
//     private String info = "info";
//     private String merchantNo = "10000186";
//     private String openAccountId = "3202001000002582";
//     private String openCardMerchantNo = "10000186";
//     private String orderAmt = "1";
//     private String source = "app";
//     private String terminalNo = "test";
//     private String transTime;
//     private String cityCode = "331000";
//     private String transId;
//
//     @Override
//     public String toString() {
//         return JsonUtil.toJsonString(this);
//     }
// }
