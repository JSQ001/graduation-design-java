package com.jsq.util.typeHandler;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import com.jsq.util.LoginInformationUtil;
import org.apache.ibatis.reflection.MetaObject;

import java.time.ZonedDateTime;

public class DomainObjectMetaObjectHandler extends MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Long currentUserID = LoginInformationUtil.getCurrentUserID();
        setValue("isEnabled",Boolean.TRUE,metaObject);
        setValue("isDeleted",Boolean.FALSE,metaObject);
        setValue("createdBy",currentUserID,metaObject);
        setValue("createdDate", ZonedDateTime.now(),metaObject);
        setValue("lastUpdatedBy",currentUserID,metaObject);
        setValue("lastUpdatedDate",ZonedDateTime.now(),metaObject);
        setValue("versionNumber",1,metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long currentUserID = LoginInformationUtil.getCurrentUserID();
        if(currentUserID!=null){
            setFieldValByName("lastUpdatedBy",currentUserID,metaObject);
        }
        setFieldValByName("lastUpdatedDate",ZonedDateTime.now(),metaObject);
    }

    private void setValue(String fieldName,Object value,MetaObject metaObject){
        Object fieldValue = getFieldValByName(fieldName, metaObject);
        if(fieldValue==null&&value!=null){
            setFieldValByName(fieldName,value,metaObject);
        }

    }

}
