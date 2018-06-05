package com.jsq.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data
@Builder
@TableName("tb_course_class")
public class CourseClass {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @NotNull
    @TableField(value = "classId", strategy = FieldStrategy.NOT_NULL)
    private Long classId;

    @NotNull
    @TableField(value = "courseId", strategy = FieldStrategy.NOT_NULL)
    private Long courseId;
}
