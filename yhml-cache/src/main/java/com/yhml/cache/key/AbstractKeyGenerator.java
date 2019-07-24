package com.yhml.cache.key;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.StringUtils;

import com.yhml.core.util.StringUtil;

/**
 * @author: Jfeng
 * @date: 2018/7/26
 */
public abstract class CacheKeyGenerator {

    private static final ParameterNameDiscoverer NAME_DISCOVERER = new DefaultParameterNameDiscoverer();
    private static final ExpressionParser PARSER = new SpelExpressionParser();

    public String getKey(String prefix, String[] keys, Method method, Object[] args) {
        StringBuilder sb = new StringBuilder(prefix);

        //  参数全不为空返回true
        if (StringUtil.isNoneBlank(keys)) {
            sb.append(this.getSpelDefinitionKey(keys, method, args));

        }

        return sb.toString();
    }

    protected String getSpelDefinitionKey(String[] keys, Method method, Object[] args) {
        EvaluationContext context = new MethodBasedEvaluationContext((Object)null, method, args, NAME_DISCOVERER);
        List<String> definitionKeyList = new ArrayList(keys.length);

        for(int index = 0; index < keys.length; ++index) {
            String definitionKey = keys[index];

            if (definitionKey != null && !definitionKey.isEmpty()) {
                String key = PARSER.parseExpression(definitionKey).getValue(context).toString();
                definitionKeyList.add(key);
            }
        }

        return StringUtils.collectionToDelimitedString(definitionKeyList, ".", "", "");
    }
}

