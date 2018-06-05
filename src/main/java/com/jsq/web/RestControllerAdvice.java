package com.jsq.web;

import com.jsq.exception.ExceptionErrorCode;
import com.jsq.exception.UnauthenticatedException;
import com.jsq.util.ExceptionDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by jsq on 2018/1/10.
 */
@ControllerAdvice    //统一异常处理注解
public class RestControllerAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestControllerAdvice.class);
    RestControllerAdvice (){}
    @ExceptionHandler({UnauthenticatedException.class})
    public ResponseEntity<ExceptionDetail> handUnauthenticated(UnauthenticatedException e) {
        LOGGER.error(ExceptionErrorCode.UNAUTHENTICATED.toString(), e);
        ExceptionDetail detail = new ExceptionDetail();
        detail.setErrorCode(ExceptionErrorCode.UNAUTHENTICATED);
        detail.setMessage(e.getMessage());
        return new ResponseEntity(detail, ExceptionErrorCode.UNAUTHENTICATED.getHttpStatus());
    }
}
