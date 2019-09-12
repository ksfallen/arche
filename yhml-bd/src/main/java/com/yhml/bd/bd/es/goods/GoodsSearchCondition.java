package com.yhml.bd.bd.es.goods;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yhml.bd.bd.es.annotation.RangeCompare;
import com.yhml.bd.bd.es.annotation.SearchInfo;
import com.yhml.bd.bd.es.enums.CompareOperation;
import com.yhml.bd.bd.es.enums.QueryMethod;
import com.yhml.bd.bd.es.model.SearchCondition;

import lombok.Getter;
import lombok.Setter;


/**
 * 搜索条件
 */
@Getter
@Setter
public class GoodsSearchCondition extends SearchCondition {

    @SearchInfo(method = QueryMethod.TERM)
    private String platformId;      //平台id

    @SearchInfo(method = QueryMethod.TERM)
    private String spuId;           //商品id


    @SearchInfo(isFilter = false, method = QueryMethod.MATCH)
    private String name;            //商品名称


    @SearchInfo(method = QueryMethod.RANGE, rangeInfo = {@RangeCompare(queryName = "saleDateBegin", operation = CompareOperation.LESS,
            include = true), @RangeCompare(queryName = "saleDateEnd", operation = CompareOperation.GREATER, include = true)})
    private Date saleDate;          //可售时间

    @SearchInfo(method = QueryMethod.TERM)
    private String saleStatus;      //默认为上架状态

    @SearchInfo(method = QueryMethod.TERM)
    private String shopId;          //店铺id

    @SearchInfo(isFilter = false, method = QueryMethod.MATCH)
    private String shopName;        //店铺名称

    @SearchInfo(method = QueryMethod.TERM)
    private String shopType;        //店铺类型

    @SearchInfo(method = QueryMethod.TERM)
    private String categoryId;      //商品分类id

    @SearchInfo(method = QueryMethod.MATCH)
    private String categoryName;    //商品分类名

    @SearchInfo(isFilter = false, method = QueryMethod.TERM)
    private String keyWord;         //关键词

    @SearchInfo(isFilter = false, method = QueryMethod.MATCH)
    private String fullText;        //全文字段,不对外,内部处理使用(商品分类名/店铺名/商品编号/商品名称/关键字)


    public GoodsSearchCondition setPlatformId(String platformId) {
        this.platformId=platformId;
        return this;
    }

    public GoodsSearchCondition setSpuId(String spuId) {
        this.spuId=spuId;
        return this;
    }


    public GoodsSearchCondition setName(String name) {
        this.name = name;
        return this;
    }

    public GoodsSearchCondition setFullText(String fullText) {
        this.fullText = fullText;
        return this;
    }


    private static List<String> areaCodePath(String areaCode) {
        if (areaCode != null && areaCode.length() > 0) {
            if (!areaCode.matches("\\d{6}")) {
                throw new RuntimeException("行政区划编码" + (areaCode == null ? "null" : areaCode) + "格式不正确，应为6位数字。");
            }
            List<String> list = new ArrayList<>();
            list.add(areaCode);

            if (!areaCode.equals("100000")) {
                if (!areaCode.endsWith("0000")) {
                    if (!areaCode.endsWith("00")) {
                        list.add(areaCode.substring(0, 4) + "00");
                    }
                    list.add(areaCode.substring(0, 2) + "0000");
                }
                list.add("100000");
            }
            return list;
        }
        return null;
    }


    //    public static void main(String[] args) {
    //        System.out.println(areaCodePath("100000"));
    //    }

    //    private static void testAreaCodePath(String areaCode) {
    //        System.out.println("**********" + areaCode + "************");
    //        for (String code : areaCodePath(areaCode)) {
    //            System.out.print("->"+code);
    //        }
    //        System.out.println("\n**********END************");
    //
    //    }
    //    public static void main(String[] args) {
    //        testAreaCodePath("123456");
    //        testAreaCodePath("123450");
    //        testAreaCodePath("123400");
    //        testAreaCodePath("123000");
    //        testAreaCodePath("120000");
    //        testAreaCodePath("100000");
    //    }


}
