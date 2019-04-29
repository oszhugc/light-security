package com.oszhugc.lightsecurity.autoconfigure.lightsecurity;

import com.oszhugc.lightsecurity.el.PreAuthorizeExpressionRoot;
import com.oszhugc.lightsecurity.interceptor.AuthInterceptor;
import com.oszhugc.lightsecurity.spec.Spec;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author oszhugc
 * @Date 2019\4\29 0029 22:51
 **/
@AllArgsConstructor
@Data
@Configuration
@Import(LightSecurityConfiguration.class)
public class LightSecurityAutoConfiguration implements WebMvcConfigurer {

    private List<Spec> specList;
    private PreAuthorizeExpressionRoot preAuthorizeExpressionRoot;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(
                new AuthInterceptor(specList,preAuthorizeExpressionRoot)
        );
    }
}
