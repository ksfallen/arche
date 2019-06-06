package com.yhml.bd.bd.es.model;

import java.util.Objects;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

/**
 * @author: Jfeng
 * @date: 2018/7/6
 */
@Data
public class SearchQuery {
    private SearchCondition condition;
    private SortOrder sortOrder = SortOrder.ASC;
    private FieldSort fieldSort;

    @Min(value = 1)
    private Integer pageNo = 1;

    @Range(min = 1, max = 20)
    private Integer pageSize = 10;

    public boolean isAsc() {
        return Objects.equals(org.elasticsearch.search.sort.SortOrder.ASC.toString(), sortOrder.toString());
    }

    public org.elasticsearch.search.sort.SortOrder getSortOrder() {
        return isAsc()? org.elasticsearch.search.sort.SortOrder.ASC : org.elasticsearch.search.sort.SortOrder.DESC;
    }
}
