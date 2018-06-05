package com.jsq.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jsq.entity.Department;
import com.jsq.entity.Major;
import com.jsq.entity.User;
import com.jsq.mapper.DepartmentMapper;
import com.jsq.mapper.MajorMapper;
import com.jsq.util.PageUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/api/major")
public class MajorController {
    private final MajorMapper majorMapper;
    private final DepartmentMapper departmentMapper;

    @GetMapping("/search")
    public ResponseEntity<List<Major>> getMajors (@RequestParam(required = false) Long deptId,
                                                   @RequestParam(required = false) String majorName,
                                                  Pageable pageable)throws URISyntaxException{
        Page page = PageUtil.getPage(pageable);
        List<Major> list = majorMapper.selectPage(page,new EntityWrapper<Major>()
                .eq(deptId!=null,"deptId",deptId)
                .like(majorName!=null,"major_name",majorName)
                .orderBy("major_name"))
                .stream().map(e->{
                    e.setDeptName(departmentMapper.selectOne(Department.builder().id(e.getDeptId()).enabled(true).build()).getDeptName());
                    return e;
                }).collect(Collectors.toList());
        HttpHeaders httpHeaders = PageUtil.generateHttpHeaders(page, "/api/major/search");
        return new ResponseEntity<List<Major>>(list, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<Major> major(@Valid @RequestBody Major major){
        majorMapper.insert(major);
        return  new ResponseEntity<Major>(major, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteById (@PathVariable Long id){
        majorMapper.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
