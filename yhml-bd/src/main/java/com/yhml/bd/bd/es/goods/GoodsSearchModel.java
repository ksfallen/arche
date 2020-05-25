package com.yhml.bd.bd.es.goods;


import com.yhml.bd.bd.es.annotation.ESField;
import com.yhml.core.util.StringUtil;

import java.util.*;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * 商品搜索索引模型
 */
@Getter
@Setter
@Accessors(chain = true)
public class GoodsSearchModel {
    private String platformId;
    private String bizCode;
    private String cityCode;
    private String spuId;
    private String spuNo;

    // @ESField(type = text, analyzer = ik_max_word)
    private String name;

    @ESField
    private List<String> areaCode;

    private String headImage;
    private Date saleDateBegin;
    private Date saleDateEnd;
    private String saleStatus;
    private String shopId;
    private String shopName;
    private String shopType; // 店铺类型
    @ESField
    private List<String> categoryId;
    @ESField
    private List<String> categoryName;
    @ESField
    private List<String> keyWord;
    private Date createDate;//商品的创建时间
    private Long sales;     //商品销量
    private Long price;    //价格 分
    private Long spuType;        //商品类型 1-标品 0-非标品
    private Long spuUnitPrice;    // 单价
    private String spuUnitName;   //商品单位
    private String spuPackingName;//商品包装单位
    private Long spuPackingPrice; //商品包装价格   注:商品为标品时,字段值同price,为真正计算费用价钱,为非标品时价格表示包装价格
    private List<String> labels = new ArrayList<>();     //商品标签
    private String fullText;

    public String getFullText() {
        // 商品分类名/店铺名/商品编号/商品名称/关键字
        Set<String> set = new HashSet<>();
        set.add(getSpuNo());
        set.add(getName());
        set.add(getShopType());
        if (null != getKeyWord()) {
            set.addAll(getKeyWord());
        }
        return StringUtil.join(set);
    }
}
