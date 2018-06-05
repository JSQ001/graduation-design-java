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
@TableName("tb_department_user")
public class UserDept {
    @TableId
    @JsonSerialize(
            using = ToStringSerializer.class   //用字符串类型序列化id
    )
    private Long id;

    @TableField(
            value = "departmentId",
            strategy = FieldStrategy.NOT_NULL)  //入库时校验
    @JsonSerialize(using = ToStringSerializer.class)
    private Long deptId;


    @TableField(
            value = "userId",
            strategy = FieldStrategy.NOT_NULL)  //入库时校验
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
}
