package com.jsq.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jsq.entity.Project;
import com.jsq.entity.UserProject;
import com.jsq.mapper.ProjectMapper;
import com.jsq.mapper.UserProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectService extends BaseService<ProjectMapper,Project> {
    @Autowired
    private  ProjectMapper projectMapper;
    @Autowired
    private UserProjectMapper userProjectMapper;

    //获取在线科目数据
    public List<Project> getWork(long userId, String type){
        List<Long> list = userProjectMapper.selectList(new EntityWrapper<UserProject>().eq("userId", userId))
                .stream().map(UserProject::getProjectId).collect(Collectors.toList());
        List<Project> projectList = projectMapper.selectList(new EntityWrapper<Project>().
                eq("projectType",type).in("id",list));

        return projectList;
    }

}
