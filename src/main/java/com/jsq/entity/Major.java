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
import java.time.ZonedDateTime;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("tb_major")
public class Major {

    @TableId
    @JsonSerialize(
            using = ToStringSerializer.class
    )
    private Long id;

    @NotNull
    @TableField(
            value = "major_name",
            strategy = FieldStrategy.NOT_NULL)
    private String majorName;

    @NotNull
    @TableField(
            value = "deptId",
            strategy = FieldStrategy.NOT_NULL)
    private Long deptId;

    @TableField(exist = false)
    private String deptName;


    @NotNull
    @TableField(
            value = "created_date",
            strategy = FieldStrategy.NOT_NULL)
    private ZonedDateTime createdDate;


}
