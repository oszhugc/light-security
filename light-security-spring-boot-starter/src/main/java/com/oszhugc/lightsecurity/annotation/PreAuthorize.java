package com.oszhugc.lightsecurity.annotation;

import java.lang.annotation.*;

/**
 * @Author oszhugc
 * @Date 2019\4\29 0029 22:24
 * @Version 2.0
 **/
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PreAuthorize {

    /**
     * 待验证的Spring-El表达式
     *
     * @return
     */
    String value();
}
