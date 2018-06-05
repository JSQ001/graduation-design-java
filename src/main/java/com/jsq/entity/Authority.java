package com.jsq.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * Created by jsq on 2018/1/23.
 */
@Data
@TableName("tb_authority")

public class Authority implements GrantedAuthority {

    @TableId
    @JsonSerialize(
            using = ToStringSerializer.class   //用字符串类型序列化id
    )
    private Long id;

    @TableField(
            value = "authority_name",
            strategy = FieldStrategy.NOT_NULL)  //入库时校验
    private String authorityName;//角色名
    @Override
    public String getAuthority() {
        return authorityName;
    }
}
