package com.oszhugc.lightsecurity.interceptor;

import com.oszhugc.lightsecurity.el.PreAuthorizeExpressionRoot;
import com.oszhugc.lightsecurity.exception.LightSecurityException;
import com.oszhugc.lightsecurity.spec.Spec;
import com.oszhugc.lightsecurity.util.RestfulMatchUtil;
import com.oszhugc.lightsecurity.util.SpringElCheckUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author oszhugc
 * @Date 2019\4\29 0029 22:17
 **/
@RequiredArgsConstructor
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private final List<Spec> specList;
    private final PreAuthorizeExpressionRoot preAuthorizeExpressionRoot;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //当前请求的路径和定义的规则能够匹配
        Boolean checkResult = specList.stream()
                .filter(spec -> RestfulMatchUtil.match(request, spec.getHttpMethod(), spec.getPath()))
                .findFirst()
                .map(spec -> {
                    String expression = spec.getExpression();
                    return SpringElCheckUtil.check(
                            new StandardEvaluationContext(preAuthorizeExpressionRoot),
                            expression
                    );
                })
                .orElse(true);

        if (!checkResult){
            throw new LightSecurityException("no permission");
        }
        return true;
    }
}
