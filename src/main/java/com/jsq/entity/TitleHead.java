package com.jsq.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("tb_title_head")
public class TitleHead {
    @TableId
    @JsonSerialize(
            using = ToStringSerializer.class   //用字符串类型序列化id
    )
    private Long id;

    @TableField(
            value = "titleNumber",
            strategy = FieldStrategy.NOT_NULL)
    private String titleNumber;

    @TableField(
            value = "titleName",
            strategy = FieldStrategy.NOT_NULL)
    private String titleName;

    @TableField(
            value = "titleType",
            strategy = FieldStrategy.NOT_NULL)
    private String titleType;

    @TableField(
            value = "teacherId",
            strategy = FieldStrategy.NOT_NULL)
    private Long teacherId;

    @TableField(
            value = "courseId",
            strategy = FieldStrategy.NOT_NULL)
    private Long courseId;

    private String url;

    private boolean enabled;

    @TableField(exist = false)
    private String status;
}
