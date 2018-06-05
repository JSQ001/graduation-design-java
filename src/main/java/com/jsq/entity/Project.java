package com.jsq.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data
@TableName("tb_project")
public class Project implements Comparable,Serializable {

    @TableId
    @JsonSerialize(
            using = ToStringSerializer.class   //用字符串类型序列化id
    )
    private Long id;

    @NotNull    //controller接收参数时校验，和@Valid联合使用
    @TableField(
            value = "projectName",
            strategy = FieldStrategy.NOT_NULL)  //入库时校验
    private String projectName;

    @TableField(
            value = "projectType",
            strategy = FieldStrategy.NOT_NULL)
    private String projectType;

    @TableField(
            value = "release_time",
            strategy = FieldStrategy.NOT_NULL)
    private ZonedDateTime releaseTime;

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
