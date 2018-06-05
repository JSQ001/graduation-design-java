package com.jsq.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jsq.entity.Class;
import com.jsq.entity.Department;
import com.jsq.service.ClassService;
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

@Controller
@AllArgsConstructor
@RequestMapping("/api/class")
public class ClassController {
    private final ClassService classService;

    //新增班级
    @PostMapping("/insert")
    public ResponseEntity<Class> insertClass(@Valid @RequestBody Class c){
        return  new ResponseEntity<Class>(classService.addClass(c), HttpStatus.OK);
    }

    //删除班级
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Class> deleteClass(@PathVariable Long id){
        classService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    //修改班级
    @PutMapping("/update")
    public ResponseEntity<Class> updateClass(@Valid @RequestBody Class c){
        classService.updateById(c);
        return ResponseEntity.ok().build();
    }

    //查询班级
    @GetMapping("/search")
    public ResponseEntity<List<Class>> queryClass(@RequestParam(required = false) String className,
                                                  @RequestParam(required = false) Long deptId,
                                                  Pageable pageable) throws URISyntaxException {
        Page page = PageUtil.getPage(pageable);
        List<Class> list = (classService.selectByOptions(deptId,className,page));
        HttpHeaders httpHeaders = PageUtil.generateHttpHeaders(page, "/api/class/search");

        return new ResponseEntity<List<Class>>(list, httpHeaders, HttpStatus.OK);
    }

    //查询班级（不分页）
    public ResponseEntity<List<Class>> queryAllClass(@RequestParam(required = false) Long deptId){
        return new ResponseEntity<List<Class>>(classService.selectAll(deptId),HttpStatus.OK);
    }
}
