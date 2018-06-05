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

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_major_class")
public class MajorClass {
    @TableId
    @JsonSerialize(
            using = ToStringSerializer.class
    )
    private Long id;

    @NotNull
    @TableField(
            value = "classId",
            strategy = FieldStrategy.NOT_NULL)
    private Long classId;

    @NotNull
    @TableField(
            value = "majorId",
            strategy = FieldStrategy.NOT_NULL)
    private Long majorId;
}
