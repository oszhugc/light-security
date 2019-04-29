package com.oszhugc.lightsecurity.jwt;

import com.oszhugc.lightsecurity.constants.ConstantsSecurity;
import com.oszhugc.lightsecurity.exception.LightSecurityException;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author oszhugc
 * @Date 2019\4\29 0029 21:26
 **/
@Slf4j
@AllArgsConstructor
public class UserOperator {

    private static final String LIGHT_SECURITY_REQ_ATTR_USER = "light-security-user";
    private static final int SEVEN = 7;

    private final JwtOperator jwtOperator;

    public User getUser(){
        try {
            HttpServletRequest request = getRequest();
            String token  = getTokenFormRequest(request);
            Boolean isValid = jwtOperator.validateToken(token);
            if (!isValid){
                return null;
            }

            Object userInReq = request.getAttribute(LIGHT_SECURITY_REQ_ATTR_USER);
            if (userInReq != null ){
                return (User)userInReq;
            }
            User user = getUserFromToken(token);
            request.setAttribute(LIGHT_SECURITY_REQ_ATTR_USER,user);
            return user;
        }catch (Exception e ){
            log.info("exception happens : ", e);
            throw new LightSecurityException(e);
        }
    }

    private User getUserFromToken(String token) {
        //从token中获取user
        Claims claims = jwtOperator.getClaimsFormToken(token);

        Object roles = claims.get(JwtOperator.ROLES);
        Object userId = claims.get(JwtOperator.USER_ID);
        Object username = claims.get(JwtOperator.USERNAME);

        return User.builder()
                .id((Integer) userId)
                .username((String) username)
                .roles((List<String>) roles)
                .build();

    }

    private String getTokenFormRequest(HttpServletRequest request) {
        String header = request.getHeader(ConstantsSecurity.AUTHORIZATION_HEADER);
        if (StringUtils.isEmpty(header)){
            throw new LightSecurityException("there is no header named Authorization");
        }

        if (!header.startsWith(ConstantsSecurity.BEARER)){
            throw new LightSecurityException("the token must start with Bearer ");
        }
        if (header.length() <= SEVEN){
            throw new LightSecurityException("the length of token must greater than sever");
        }

        return header.substring(SEVEN);

    }

    private static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null){
            throw new LightSecurityException("requestAttributes is null");
        }
        return ((ServletRequestAttributes)requestAttributes).getRequest();


    }


}
