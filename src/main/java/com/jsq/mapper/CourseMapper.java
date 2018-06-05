package com.jsq.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jsq.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface CourseMapper extends BaseMapper<Course> {
}
