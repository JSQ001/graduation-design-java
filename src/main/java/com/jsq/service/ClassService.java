package com.jsq.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jsq.entity.*;
import com.jsq.entity.Class;
import com.jsq.mapper.ClassDeptMapper;
import com.jsq.mapper.ClassMapper;
import com.jsq.mapper.MajorClassMapper;
import com.jsq.mapper.UserClassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClassService extends BaseService<ClassMapper,Class> {

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private ClassDeptMapper classDeptMapper;

    @Autowired
    private UserClassMapper userClassMapper;

    @Autowired
    private MajorClassMapper majorClassMapper;

    //增加班级
    public Class addClass(Class c){
        classMapper.insert(c);
        classDeptMapper.insert(ClassDept.builder().deptId(c.getDeptId()).classId(c.getId()).build());
        majorClassMapper.insert(MajorClass.builder().majorId(c.getMajorId()).classId(c.getId()).build());
        return c;
    }

    //条件查询
    public List<Class> selectByOptions (Long deptId, String className, Page<Class> page){
        List classIds = null;
        if(deptId!=null){
            classIds = classDeptMapper.selectList(new EntityWrapper<ClassDept>().
                    eq("deptId",deptId))
                    .stream()
                    .map(ClassDept::getClassId)
                    .collect(Collectors.toList());
        }
        return classMapper.selectPage(page,new EntityWrapper<Class>().
                in(deptId!=null,"id",classIds)
                .like(className!=null,"className",className)
                .orderBy("grade"))
                .stream().map(e->{
                    e.setClassCount(userClassMapper.selectList(new EntityWrapper<UserClass>().eq("classId",e.getId())).stream().count());
                    return  e;
                }).collect(Collectors.toList());
    }

    //查询所有
    public List<Class> selectAll (Long deptId){
        List<Long> classIds = null;
        if(deptId != null){
            classIds = classDeptMapper.selectList(new EntityWrapper<ClassDept>()
                    .eq("deptId",deptId))
                    .stream().map(ClassDept::getClassId).collect(Collectors.toList());
        }
        return classMapper.selectList(new EntityWrapper<Class>()
                .in(classIds.size()>0,"id",classIds));
    }
}
