package com.jsq.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data
@TableName("tb_dept_class")
public class ClassDept implements Comparable,Serializable {

    @TableId
    @JsonSerialize(
            using = ToStringSerializer.class   //用字符串类型序列化id
    )
    private Long id;

    @TableField(
            value = "classId",
            strategy = FieldStrategy.NOT_NULL)
    private Long classId;//班级id

    @TableField(value = "deptId")
    private Long deptId;//系部id



    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
