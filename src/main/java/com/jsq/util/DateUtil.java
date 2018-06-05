package com.jsq.util;

import com.jsq.exception.BizException;
import org.thymeleaf.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateUtil {


//    String(yyyy-MM-dd)转Date
    public static Date StringToDate(String StringDate){
        if(StringUtils.isEmpty(StringDate)){
            throw new BizException(RespCode.STRING_IS_NULL);
        }
        String rex = "\\d{4}-\\d{2}-\\d{2}";
        if(!StringDate.matches(rex)){
            throw new BizException(RespCode.DATE_FORMAT_NOT_TRUE);
        }
        SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(StringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

//    Date转ZonedDateTime
    public static ZonedDateTime DateToZoneDateTime(Date date){
        return date==null?null:ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }





}
