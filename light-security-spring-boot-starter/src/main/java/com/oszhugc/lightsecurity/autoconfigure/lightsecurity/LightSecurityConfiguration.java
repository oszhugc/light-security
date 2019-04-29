package com.oszhugc.lightsecurity.autoconfigure.lightsecurity;

import com.oszhugc.lightsecurity.annotation.support.PreAuthorizeAspect;
import com.oszhugc.lightsecurity.el.PreAuthorizeExpressionRoot;
import com.oszhugc.lightsecurity.jwt.JwtOperator;
import com.oszhugc.lightsecurity.jwt.UserOperator;
import com.oszhugc.lightsecurity.spec.Spec;
import com.oszhugc.lightsecurity.spec.SpecRegistry;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author oszhugc
 * @Date 2019\4\29 0029 22:37
 **/
@Configuration
@EnableConfigurationProperties(LightSecurityProperties.class)
@AutoConfigureBefore(LightSecurityAutoConfiguration.class)
public class LightSecurityConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public JwtOperator jwtOperator(LightSecurityProperties lightSecurityProperties){
        return new JwtOperator(lightSecurityProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public UserOperator userOperator(JwtOperator jwtOperator){
        return new UserOperator(jwtOperator);
    }

    @Bean
    @ConditionalOnMissingBean
    public PreAuthorizeExpressionRoot preAuthorizeExpressionRoot(UserOperator userOperator){
        return new PreAuthorizeExpressionRoot(userOperator);
    }


    @Bean
    @ConditionalOnMissingBean
    public PreAuthorizeAspect preAuthorizeAspect(PreAuthorizeExpressionRoot preAuthorizeExpressionRoot){
        return new PreAuthorizeAspect(preAuthorizeExpressionRoot);
    }

    @Bean
    @ConditionalOnBean(SpecRegistry.class)
    public List<Spec> specListFromSpecRegistry(SpecRegistry specRegistry) {
        List<Spec> specList = specRegistry.getSpecList();
        if (CollectionUtils.isEmpty(specList)) {
            throw new IllegalArgumentException("specList cannot be empty.");
        }
        return specList;
    }

    @Bean
    @ConditionalOnMissingBean(SpecRegistry.class)
    public List<Spec> specListFromProperties(LightSecurityProperties lightSecurityProperties){
        return lightSecurityProperties.getSpecList();
    }



}
