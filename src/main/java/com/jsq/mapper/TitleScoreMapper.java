package com.jsq.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jsq.entity.TitleScore;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface TitleScoreMapper extends BaseMapper<TitleScore> {

}
