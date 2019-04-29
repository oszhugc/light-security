package com.oszhugc.lightsecurity.util;

import com.oszhugc.lightsecurity.enums.HttpMethod;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author oszhugc
 * @Date 2019\4\29 0029 21:44
 **/
@Slf4j
@UtilityClass
public class RestfulMatchUtil {
    
    private static final String MATCH_ALL = "/**";
    private static AntPathMatcher MATCHER = new AntPathMatcher();
    
    
    public static boolean match(HttpServletRequest request, HttpMethod httpMethod, String pattern){
        boolean methodMaters = matchMethod(request,httpMethod);
        boolean pathMatches = matchPath(request,pattern);

        log.info("match begins. {},{},httpMethod = {}, patter = {}, methodMatch = {}, pathMatches = {}",
                request.getMethod(),getRequestPath(request),
                httpMethod,pattern,methodMaters,pathMatches
        );

        return methodMaters && pathMatches;
    }

    /**
     * 判断路径是否匹配
     *
     * @param request
     * @param pattern
     * @return
     */
    private static boolean matchPath(HttpServletRequest request, String pattern) {
        String url = getRequestPath(request);
        log.debug("path match begins. {},{},pattern = {}",request.getMethod(),url,pattern);
        //如果pattern == /** 则直接认为匹配
        if (pattern.equals(MATCH_ALL)){
            return true;
        }
        return MATCHER.match(pattern,url);

    }

    /**
     * 判断方法是否匹配
     * 
     * @param request
     * @param httpMethod
     * @return
     */
    private static boolean matchMethod(HttpServletRequest request, HttpMethod httpMethod) {
        log.debug("method match begins. {} {}, httpMethod = {}",
                request.getMethod(),getRequestPath(request),httpMethod);
        if (httpMethod == HttpMethod.ANY){
            return true;
        }
        
        return httpMethod != null && StringUtils.hasText(request.getMethod())
                && httpMethod == valueOf(request.getMethod());
    }

    /**
     * 字符串转httpmethod枚举
     *
     * @param method
     * @return
     */
    private static HttpMethod valueOf(String method) {
        try {
            return HttpMethod.valueOf(method);
        }catch (IllegalArgumentException e){
            return null;
        }
    }

    /**
     * 获取请求的路径
     *
     * @param request
     * @return
     */
    private static String getRequestPath(HttpServletRequest request) {
        String url = request.getServletPath();
        String pathInfo = request.getPathInfo();
        if (pathInfo != null){
            url = StringUtils.hasLength(url) ? url + pathInfo : pathInfo;
        }
        return url;
    }
}
