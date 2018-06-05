package com.jsq.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jsq.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * Created by jsq on 2018/1/9.
 */
@Mapper
@Component
public interface UserInfoMapper extends BaseMapper<User>{
    /**
     * 根据用户名查询信息
     */
    //public User getBeanInfoByName(@Param("ew") Wrapper<User> wrapper);
    @Select("select * from tb_user where user_number = #{username}")
    User selectByUsername(@Param("username") String username);

    User selectUserByUserNum(String userNumber);
}
