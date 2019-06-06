package com.yhml.core.base.bean;

import java.util.Date;

import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Range;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseParamBean extends BaseBean {

    // private static List<String> excludeFields = Lists.newArrayList();
    //
    // static {
    //     excludeFields.addAll(BaseBean.excludeFields);
    //     excludeFields.add("startDate");
    //     excludeFields.add("endDate");
    //     // excludeFields.add("pageSize");
    //     // excludeFields.add("pageNo");
    //     // excludeFields.add("orderBy");
    //     // excludeFields.add("orderBy");
    // }

    @Transient
    @JSONField(serialize = false)
    private Date startDate;

    @Transient
    @JSONField(serialize = false)
    private Date endDate;

    /** 分页大小 */
    @Transient
    @JSONField(serialize = false)
    @Max(100)
    @Range(min = 1, max = 100)
    private Integer pageSize = 10;

    /** 页码 */
    @Transient
    @JSONField(serialize = false)
    @Min(1)
    private Integer pageNum;

    /** 排序 */
    @Transient
    @JSONField(serialize = false)
    private String orderBy;

}
