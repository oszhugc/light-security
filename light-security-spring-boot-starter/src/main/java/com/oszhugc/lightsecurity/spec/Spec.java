package com.oszhugc.lightsecurity.spec;

import com.oszhugc.lightsecurity.enums.HttpMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author oszhugc
 * @Date 2019\4\29 0029 20:54
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
 public class Spec {

    /**
     * 请求方法
     */
    private HttpMethod httpMethod;

    /**
     * 路径
     */
    private String path;

    /**
     * 表达式
     * - hasLogin() 判断是否登录
     * - permitAll() 直接允许访问
     * - hasRole('角色名') 判断是否具有指定角色
     * - hasAnyRole('角色1','角色2') 是否具备角色1/2中的任意一个
     */
    private String expression;
}
