package com.oszhugc.lightsecurity.autoconfigure.lightsecurity;

import com.oszhugc.lightsecurity.spec.Spec;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置属性
 * @author oszhugc
 * @Date 2019\4\29 0029 21:04
 **/
@ConfigurationProperties(prefix = "light-security")
@Setter
@Getter
public class LightSecurityProperties {

    private Jwt jwt = new Jwt();

    private List<Spec> specList = new ArrayList<>();


    @Getter
    @Setter
    public static class Jwt{

        /**
         * secret
         */
        private String secret = "light-security";

        /**
         * token的有效时间(秒),默认两周
         */
        private Long expireationInSecond = 1209600L;

        /**
         * 加签的算法,默认sha512
         */
        private SignatureAlgorithm algorithm = SignatureAlgorithm.HS512;
    }

}
