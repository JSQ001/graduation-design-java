package com.jsq.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Set;

/**
 * Created by jsq on 2018/1/9.
 * 用户类
 */

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("tb_user")
public class User implements Comparable,Serializable{
    private static final long serialVersionUID = 8646266877283900291L;

    @TableId
    @JsonSerialize(
            using = ToStringSerializer.class   //用字符串类型序列化id
    )
    private Long id;

    @NotNull    //controller接收参数时校验，和@Valid联合使用
    @TableField(
            value = "user_number",
            strategy = FieldStrategy.NOT_NULL)  //入库时校验
    private String userNumber;//学号/工号

    @TableField(
            value = "password",
            strategy = FieldStrategy.NOT_NULL)
    private String password;  //密码

    @NotNull
    @TableField(
            value = "nickname",
            strategy = FieldStrategy.NOT_NULL)
    private String nickName;  //姓名

    @TableField(
            value = "avatar",
            strategy = FieldStrategy.NOT_NULL)
    private String avatar;        // 头像

    @NotNull
    @TableField(
            value = "gender",
            strategy = FieldStrategy.NOT_NULL)
    private String gender;  //性别

    @TableField(value = "phone")
    private String phone;  //电话

    @TableField(exist = false)
    private Set<Long> role;

    @TableField(exist = false)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long deptId;

    @TableField(exist = false)
    private String deptName; //系部名称

    @TableField(exist = false)
    private Long classId;

    @TableField(exist = false)
    private String className;

    @TableField(exist = false)
    private Long majorId;

    @TableField(exist = false)
    private String majorName;


    @TableField(
            value = "is_locked",
            strategy = FieldStrategy.NOT_NULL
    )
    private boolean isLocked;

    @TableField(
            value = "is_enabled",
            strategy = FieldStrategy.NOT_NULL
    )
    private  boolean enabled;

    private ZonedDateTime createdDate;
    private ZonedDateTime lastUpdatedDate;
    private Long lastUpdatedBy;

    @Override
    public int compareTo(Object o) {
        if(o instanceof User){
            return this.userNumber.compareTo(((User) o).userNumber);
        }
        return 0;
    }
}
