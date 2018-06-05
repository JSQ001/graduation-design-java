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

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data
@TableName("tb_user_project")
public class UserProject implements Comparable,Serializable {

    @TableId
    @JsonSerialize(
            using = ToStringSerializer.class   //用字符串类型序列化id
    )
    private Long id;

    @TableField(
            value = "userId",
            strategy = FieldStrategy.NOT_NULL)
    private Long userId;

    @TableField(
            value = "projectId",
            strategy = FieldStrategy.NOT_NULL)
    private Long projectId;

    @TableField(value = "project_score")
    private String projectScore;

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
