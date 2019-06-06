package com.yhml.bd.bd.es.goods;

import com.simple.common.base.bean.BaseBean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeleteModel extends BaseBean {
    private String platformId;
    private String spuId;
    private String shopId;
}
