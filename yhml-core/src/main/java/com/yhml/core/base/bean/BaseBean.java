package com.yhml.core.base.bean;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.yhml.core.util.JsonUtil;

public class BaseBean {

    // protected static List<String> excludeFields = Lists.newArrayList("passWord", "password", "mobile");

    @Override
    public String toString() {
        // ReflectionToStringBuilder.setDefaultStyle(ToStringStyle.JSON_STYLE);
        // return ReflectionToStringBuilder.toStringExclude(this, getExcludeFields());
        return JsonUtil.toJsonString(this);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    // protected List<String> getExcludeFields() {
    //     return excludeFields;
    // }
}
