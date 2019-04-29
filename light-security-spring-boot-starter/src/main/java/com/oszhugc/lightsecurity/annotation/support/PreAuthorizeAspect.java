package com.oszhugc.lightsecurity.annotation.support;

import com.oszhugc.lightsecurity.annotation.PreAuthorize;
import com.oszhugc.lightsecurity.el.PreAuthorizeExpressionRoot;
import com.oszhugc.lightsecurity.exception.LightSecurityException;
import com.oszhugc.lightsecurity.util.SpringElCheckUtil;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * @author oszhugc
 * @Date 2019\4\29 0029 22:30
 **/
@Aspect
@AllArgsConstructor
public class PreAuthorizeAspect {

    private final PreAuthorizeExpressionRoot preAuthorizeExpressionRoot;

    @Around("@annotation(com.oszhugc.lightsecurity.annotation.PreAuthorize)")
    public Object preAuth(ProceedingJoinPoint point)throws Throwable{
       MethodSignature signature =  (MethodSignature)point.getSignature();
       Method method = signature.getMethod();

       if (method.isAnnotationPresent(PreAuthorize.class)){
           PreAuthorize preAuthorize = method.getAnnotation(PreAuthorize.class);

           String expression = preAuthorize.value();

           boolean check = SpringElCheckUtil.check(
                   new StandardEvaluationContext(preAuthorizeExpressionRoot),
                   expression
           );
           if (!check){
               throw new LightSecurityException("no access");
           }
       }
       return point.proceed();
    }

}
