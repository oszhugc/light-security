package com.oszhugc.lightsecurity.jwt;

import com.oszhugc.lightsecurity.autoconfigure.lightsecurity.LightSecurityProperties;
import com.oszhugc.lightsecurity.exception.LightSecurityException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;

/**
 * @author oszhugc
 * @Date 2019\4\29 0029 21:13
 **/
@Slf4j
@RequiredArgsConstructor
public class JwtOperator {

    public static final String USER_ID = "id";
    public static final String USERNAME = "username";
    public static final String ROLES = "roles";
    private final LightSecurityProperties lightSecurityProperties;


    public Claims getClaimsFormToken(String token){
        try {
            return Jwts.parser()
                    .setSigningKey(this.lightSecurityProperties.getJwt().getSecret().getBytes())
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            log.error("token解析错误", e);
            throw new LightSecurityException("Token invalided",e);
        }
    }


    public Date getExpirationDateFromToken(String token){
        return getClaimsFormToken(token).getExpiration();
    }


    private Boolean isTokenExpired(String token){
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationTime(){
        return new Date(System.currentTimeMillis() + lightSecurityProperties.getJwt().getExpireationInSecond() * 1000);
    }

    public String generateToken(User user){
        HashMap<String, Object> claims = new HashMap<>(3);
        claims.put(USER_ID,user.getId());
        claims.put(USERNAME,user.getUsername());
        claims.put(ROLES,user.getRoles());
        Date createTime = new Date();
        Date expirationTime = this.getExpirationTime();

        byte[] keyBytes = this.lightSecurityProperties.getJwt().getSecret().getBytes();
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(createTime)
                .setExpiration(expirationTime)
                .signWith(key)
                .compact();
    }


    public Boolean validateToken(String token){
        return !isTokenExpired(token);
    }

}
