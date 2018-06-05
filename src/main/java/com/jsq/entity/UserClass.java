package com.jsq.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;
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
@TableName("tb_user_class")
public class UserClass {

    @TableId
    private Long id;
    @NotNull
    @TableField(
            value = "userId",
            strategy = FieldStrategy.NOT_NULL)
    private Long userId;

    @TableField(
            value = "classId",
            strategy = FieldStrategy.NOT_NULL)
    private Long classId;
}
