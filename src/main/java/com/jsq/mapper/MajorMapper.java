package com.jsq.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jsq.entity.Major;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface MajorMapper extends BaseMapper<Major> {
}
