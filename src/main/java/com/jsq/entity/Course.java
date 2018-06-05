package com.jsq.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("tb_course")
public class Course {
    @TableId
    @JsonSerialize(
            using = ToStringSerializer.class   //用字符串类型序列化id
    )
    private Long id;

    @NotNull    //controller接收参数时校验，和@Valid联合使用
    @TableField(
            value = "teacherId",
            strategy = FieldStrategy.NOT_NULL)
    private Long teacherId;//教师id
    @TableField(exist = false)
    private String teacherName;//教师id

    @TableField(
            value = "courseNumber",
            strategy = FieldStrategy.NOT_NULL)
    private String courseNumber;  //课程号

    @TableField(
            value = "courseType",
            strategy = FieldStrategy.NOT_NULL)
    private String courseType;

    @TableField(
            value = "courseName",
            strategy = FieldStrategy.NOT_NULL)
    private String courseName;  //课程名称

    private boolean enabled;

}
