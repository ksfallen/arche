package com.yhml.cache.lock;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.StringUtils;

import com.yhml.cache.annotaton.LockAction;


/**
 * @author: Jfeng
 * @date: 2019-07-15
 */
public class LockKeyGenerator {
    private static final ParameterNameDiscoverer NAME_DISCOVERER = new DefaultParameterNameDiscoverer();
    private static final ExpressionParser PARSER = new SpelExpressionParser();


    public String getKeyName(MethodInvocation invocation, LockAction lock) {
        StringBuilder sb = new StringBuilder();
        sb.append(lock.prefix());

        if (lock.keys().length > 1 || !"".equals(lock.keys()[0])) {
            sb.append(this.getSpelDefinitionKey(lock.keys(), invocation.getMethod(), invocation.getArguments()));
        }

        return sb.toString();
    }

    private String getSpelDefinitionKey(String[] definitionKeys, Method method, Object[] parameterValues) {
        EvaluationContext context = new MethodBasedEvaluationContext((Object)null, method, parameterValues, NAME_DISCOVERER);
        List<String> definitionKeyList = new ArrayList(definitionKeys.length);

        for(int index = 0; index < definitionKeys.length; ++index) {
            String definitionKey = definitionKeys[index];

            if (definitionKey != null && !definitionKey.isEmpty()) {
                String key = PARSER.parseExpression(definitionKey).getValue(context).toString();
                definitionKeyList.add(key);
            }
        }

        return StringUtils.collectionToDelimitedString(definitionKeyList, ".", "", "");
    }
}
