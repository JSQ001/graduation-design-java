package com.jsq.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Created by jsq on 2018/4/21.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_department")
public class Department implements Comparable,Serializable {
    @TableId
    @JsonSerialize(
            using = ToStringSerializer.class   //用字符串类型序列化id
    )
    private Long id;

    @TableField(
            value = "deptCode",
            strategy = FieldStrategy.NOT_NULL)
    private String deptCode;//系部代码

    @TableField(
            value = "deptName",
            strategy = FieldStrategy.NOT_NULL)
    private String deptName;//系部名称

    @TableField(
            value = "is_enabled",
            strategy = FieldStrategy.NOT_NULL
    )
    private  boolean enabled;

    private ZonedDateTime createdDate;
    @TableField(
            value = "description",
            strategy = FieldStrategy.NOT_NULL)
    private String description;//描述

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
