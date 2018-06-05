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
import lombok.experimental.Accessors;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data
@Builder
@TableName("tb_class")
public class Class implements Comparable,Serializable {

    @TableId
    @JsonSerialize(
            using = ToStringSerializer.class   //用字符串类型序列化id
    )
    private Long id;

    @TableField(
            value = "className",
            strategy = FieldStrategy.NOT_NULL)
    private String className;//班级名称

    @TableField(value = "classCount")
    private Long classCount;//班级人数

    @TableField(
            value = "grade",
            strategy = FieldStrategy.NOT_NULL)
    private String grade;

    @TableField(exist = false)
    private Long deptId;

    @TableField(exist = false)
    private Long majorId;

    @TableField(exist = false)
    private Long courseId;

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
