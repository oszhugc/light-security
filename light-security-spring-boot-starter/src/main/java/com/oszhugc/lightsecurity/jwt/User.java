package com.oszhugc.lightsecurity.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author oszhugc
 * @Date 2019\4\29 0029 21:12
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    private Integer id;
    private String username;
    private List<String> roles;
}
