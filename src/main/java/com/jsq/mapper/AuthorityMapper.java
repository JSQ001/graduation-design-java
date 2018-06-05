package com.jsq.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jsq.entity.Authority;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * Created by jsq on 2018/4/27.
 */
@Component
@Mapper
public interface AuthorityMapper extends BaseMapper<Authority>{

}
