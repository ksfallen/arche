package com.simple.gateway.filter;

import com.google.common.base.Charsets;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.yhml.core.util.RequestUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import lombok.extern.slf4j.Slf4j;

/*
 *
 * @author Jfeng
 * @date 2018/1/24
 */
@Slf4j
// @Component
public class AccessLogFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info("==> method:{} URL:{} params:{} IP:{} ",
                request.getMethod(),
                request.getRequestURL(),
                RequestUtil.getParams(request),
                RequestUtil.getIpAddress(request));

        // 处理响应
        if (ctx.getResponseBody() != null) {
            log.info(ctx.getResponseBody());
            return null;
        }

        InputStream is;

        try {
            is = ctx.getResponseDataStream();
            InputStream inputStream = is;

            if (is != null) {
                // 返回类型是否GZIP
                if (ctx.getResponseGZipped()) {

                    final Long len = ctx.getOriginContentLength();
                    if (len == null || len > 0) {
                        try {
                            inputStream = new GZIPInputStream(is);
                        } catch (IOException ex) {
                            log.error("gzip expected but not, unencoded response " + ctx.getRequest().getRequestURL().toString());
                            inputStream = is;
                        }
                    }
                }

                String body = IOUtils.toString(inputStream, Charsets.UTF_8);
                // InputStream 不能重复使用
                ctx.setResponseBody(body);
                log.info("<== resp:{}", body);
            }
        } catch (Exception e) {
            log.error("LogFilter error", e);
        }

        return null;
    }
}
