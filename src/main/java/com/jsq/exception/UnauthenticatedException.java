package com.jsq.exception;

/**
 * Created by jsq on 2018/1/10.
 */
public class UnauthenticatedException extends RuntimeException{
    UnauthenticatedException(){
        super("user.not.login");
    }
}
