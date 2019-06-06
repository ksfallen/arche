package com.yhml.core.base.bean;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 结果封装类
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO<T> {
    private Integer pageNo;
    private Integer pageSize;
    private Integer totalCount;
    private  List<T> list;

}

