package com.yhml.core.base;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.yhml.core.util.RequestUtil;

/**
 *
 * @author Jfeng
 * @date 2017/10/11
 */
@CrossOrigin(allowCredentials = "true")
public class BaseController {
    protected Logger log = LoggerFactory.getLogger(getClass());

    // @Autowired
    // protected HttpServletRequest request;

    protected HttpServletRequest getRequest() {
        return RequestUtil.getRequest();
    }

    public String getString(String param, String defaultValue) {
        String value = getRequest().getParameter(param);
        return StringUtils.isBlank(value) ? defaultValue : value.trim();
    }

    public String getString(String param) {
        return getString(param, "");
    }

    public Integer getInt(String param, int defaultValue) {
        return NumberUtils.toInt(getString(param, null), defaultValue);
    }

    protected Long getLong(String param, long defaultValue) {
        return NumberUtils.toLong(getString(param, null), defaultValue);
    }

}