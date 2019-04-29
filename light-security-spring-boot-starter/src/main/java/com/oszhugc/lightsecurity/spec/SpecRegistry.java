package com.oszhugc.lightsecurity.spec;

import lombok.Data;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * @author oszhugc
 * @Date 2019\4\29 0029 21:00
 **/
@Data
public class SpecRegistry {

    private List<Spec> specList = new ArrayList<>();

    public SpecRegistry add(HttpMethod httpMethod,String path,String expression){
        specList.add(new Spec(httpMethod,path,expression));
        return  this;
    }
}
