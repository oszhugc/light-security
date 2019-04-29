package com.oszhugc.lightsecurity.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import javax.swing.text.html.parser.Parser;

/**
 * @author oszhugc
 * @Date 2019\4\29 0029 22:02
 **/
@UtilityClass
@Slf4j
public class SpringElCheckUtil {

    private static ExpressionParser PARSER = new SpelExpressionParser();


    public static boolean check(EvaluationContext context,String expression){
        Boolean result = PARSER.parseExpression(expression).getValue(context, Boolean.class);
        log.info("expression = {}, eval result = {}",expression, result);
        return result != null ? result : false;
    }
}
