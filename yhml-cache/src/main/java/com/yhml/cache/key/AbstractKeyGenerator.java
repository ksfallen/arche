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

import com.yhml.core.util.StringUtil;

/**
 * @author: Jfeng
 * @date: 2018/7/26
 */
public abstract class AbstractKeyGenerator implements IKeyGenerator {

    private static final ParameterNameDiscoverer NAME_DISCOVERER = new DefaultParameterNameDiscoverer();
    private static final ExpressionParser PARSER = new SpelExpressionParser();


    protected String getKey(Method method, Object[] args, String prefix, String[] keys, String delimiter) {
        StringBuilder sb = new StringBuilder(prefix);

        if (keys != null && keys.length > 0) {
            sb.append(this.getSpelDefinitionKey(method, args, keys, delimiter));
        }

        return sb.toString();
    }

    protected String getSpelDefinitionKey(Method method, Object[] args, String[] keys, String delimiter) {
        EvaluationContext context = new MethodBasedEvaluationContext((Object) null, method, args, NAME_DISCOVERER);
        List<String> keyList = new ArrayList(keys.length);

        // Arrays.stream(keys).filter(key -> StringUtil.isNotBlank(key)).forEach(definitionKey -> {
        //     String temp = PARSER.parseExpression(definitionKey).getValue(context).toString();
        //     keyList.add(temp);
        // });

        for (String definitionKey : keys) {

            if (StringUtil.isNotBlank(definitionKey)) {
                String temp = PARSER.parseExpression(definitionKey).getValue(context).toString();
                keyList.add(temp);
            }
        }

        return StringUtil.join(delimiter, keyList);
    }
}

