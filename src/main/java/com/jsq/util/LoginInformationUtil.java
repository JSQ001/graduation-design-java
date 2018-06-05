package com.jsq.util;

import com.jsq.entity.User;
import com.jsq.exception.ValidationError;
import com.jsq.exception.ValidationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

/**
 * @description: 获取登陆信息工具类
 * @version: 1.0
 * @author: wenzhou.tang@hand-china.com
 * @date: 2017/9/15 10:48
 */
public class LoginInformationUtil {

    //获取当前用户id
    public static Long getCurrentUserID(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Long userID = null;
        try {
            User user = null;
            Object o = securityContext.getAuthentication().getPrincipal();
            if(o instanceof User){
                user = (User) o;
            }

            userID = user.getId();

        } catch (Exception e) {
            throw new ValidationException(new ValidationError("operation.error", "not found current userID"));
        }
        if (userID == null) {
            throw new ValidationException(new ValidationError("operation.error", "not found current userID"));
        }
        return userID;
    }

    //获取当前用户id
    public static User getCurrentUser(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        User user = null;
        try {
            Object o = securityContext.getAuthentication().getPrincipal();
            if(o instanceof User){
                user = (User) o;
            }
        } catch (Exception e) {
            throw new ValidationException(new ValidationError("operation.error", "not found current userID"));
        }
        return user;
    }

}
 