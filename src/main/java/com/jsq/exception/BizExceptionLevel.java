package com.jsq.exception;

/**
 * Created by kai.zhang on 2017-11-13.
 */
public interface BizExceptionLevel {
    String ERROR = "ERROR";     //错误
    String ALLOWED = "ALLOWED";         //警告
    String BLOCK = "BLOCK";          //禁止
    String NO_MESSAGE = "NO_MESSAGE";          //通过
    String COMPEL = "COMPEL";        //强制通过(警告后继续提交)
}
