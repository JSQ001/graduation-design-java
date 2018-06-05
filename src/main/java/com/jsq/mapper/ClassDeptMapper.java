package com.jsq.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jsq.entity.ClassDept;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface ClassDeptMapper extends BaseMapper<ClassDept> {
}
