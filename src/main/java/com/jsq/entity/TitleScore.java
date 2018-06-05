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
@TableName("tb_user_title_score")
public class TitleScore {

    @TableId
    @JsonSerialize(
            using = ToStringSerializer.class   //用字符串类型序列化id
    )
    private Long id;

    @NotNull
    @TableField(
            value = "userNumber",
            strategy = FieldStrategy.NOT_NULL)
    private String userNumber;

    @TableField(
            value = "titleHId",
            strategy = FieldStrategy.NOT_NULL)
    private Long titleHId;

    @TableField(exist = false)
    private String titleName;

    @NotNull
    @TableField(
            value = "score",
            strategy = FieldStrategy.NOT_NULL)
    private double score;

}
