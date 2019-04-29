package com.oszhugc.lightsecurity.exception;

/**
 * @author oszhugc
 * @Date 2019\4\29 0029 21:10
 **/
public class LightSecurityException extends  RuntimeException {

    public LightSecurityException(Throwable cause){
        super(cause);
    }

    public LightSecurityException(String msg){
        super(msg);
    }

    public LightSecurityException(String msg,Throwable cause){
        super(msg,cause);
    }
}
