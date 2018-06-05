package com.jsq.exception;

import java.util.Arrays;

/**
 * Created by kai.zhang on 2017-09-18.
 */
public class BizDifferentLevelException extends BizException {
    protected String errorLevel;

    public BizDifferentLevelException(String errorLevel,String code) {
        this(errorLevel,code, null, null, null);
    }

    public BizDifferentLevelException(String errorLevel,String code, Object[] args) {
        this(errorLevel,code, args, null, null);
    }

    public BizDifferentLevelException(String errorLevel,String code, String message) {
        this(errorLevel,code, null, message, null);
    }

    public BizDifferentLevelException(String errorLevel,String code, Object[] args, String message) {
        this(errorLevel,code, args, message, null);
    }

    public BizDifferentLevelException(String errorLevel,String code, Throwable cause) {
        this(errorLevel,code, null, null, cause);
    }

    public BizDifferentLevelException(String errorLevel,String code, Object[] args, Throwable cause) {
        this(errorLevel,code, args, null, cause);
    }

    public BizDifferentLevelException(String errorLevel,String code, String message, Throwable cause) {
        this(errorLevel,code, null, message, cause);
    }

    public BizDifferentLevelException(String errorLevel,String code, Object[] args, String message, Throwable cause) {
        super(code, args, message, cause);
        this.errorLevel = errorLevel;
    }

    public String getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

    public String getMsg() {
        return msg;
    }

    public String getErrorLevel(){
        return errorLevel;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(getClass().getSimpleName());
        buf.append("[code='" + getCode() + "'");
        buf.append(", args=" + Arrays.toString(args));
        buf.append(", errorLevel= '" + getErrorLevel() + "'");
        buf.append(", msg='" + msg + "']");
        String message = getLocalizedMessage();
        buf.append((message != null) ? ": " + message : "");
        StackTraceElement[] traces = getStackTrace();
        buf.append(traces.length == 0 ? "" : ": at " + traces[0]);
        return buf.toString();
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
