package com.jsq.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jsq.entity.Department;
import com.jsq.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DepartmentService extends BaseService<DepartmentMapper,Department> {
    @Autowired
    private  DepartmentMapper departmentMapper;

    //新增系部
    public Department insertDept(Department department){
        Integer insert = departmentMapper.insert(department);
        return department;
    }

    //删除系部
    public Integer deleteDept(Long id){
        return departmentMapper.deleteById(id);
    }

    //修改系部
    public Department updateDept(Department department){
        if(department.getDescription().equals(""))
            department.setDescription(null);
        departmentMapper.updateById(department);
        return department;
    }

    //条件查询系部
    public List<Department> selectDeptByOptions(String deptCode,String deptName,Page<Department> page){
        return  departmentMapper.selectPage(page,new EntityWrapper<Department>()
                .where("is_enabled = true")
                .eq(deptCode!=null,"deptCode",deptCode)
                .eq(deptName!=null,"deptName",deptName));
    }
}
