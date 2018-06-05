package com.jsq.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jsq.entity.Class;
import com.jsq.entity.Course;
import com.jsq.entity.CourseClass;
import com.jsq.entity.User;
import com.jsq.mapper.ClassMapper;
import com.jsq.mapper.CourseClassMapper;
import com.jsq.mapper.CourseMapper;
import com.jsq.mapper.UserInfoMapper;
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
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/api/course")
public class CourseController {

    private final CourseMapper courseMapper;
    private final UserInfoMapper userInfoMapper;
    private final CourseClassMapper courseClassMapper;
    private final ClassMapper classMapper;

    @PostMapping("/insert")
    public ResponseEntity<Course> addCourse(@Valid @RequestBody Course course){
        courseMapper.insert(course);
        return  new ResponseEntity<Course>(course, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Course>> getCourse(@RequestParam(required = false) String courseNumber,
                                                  @RequestParam (required = false) String  courseName,
                                                  @RequestParam (required = false) Long teacherId,
                                                  @RequestParam (required = false,defaultValue = "true") boolean enabled,
                                                  Pageable pageable) throws URISyntaxException {
        Page page = PageUtil.getPage(pageable);
        List<Course> list = courseMapper.selectPage(page,new EntityWrapper<Course>()
                .eq(courseNumber!=null,"courseNumber",courseName)
                .eq(courseName!=null, "courseName", courseName)
                .eq(teacherId!=null,"teacherId",teacherId))
                .stream().map(e->
                    e.setTeacherName(userInfoMapper.selectById(e.getTeacherId()).getNickName())
                ).collect(Collectors.toList());
        HttpHeaders httpHeaders = PageUtil.generateHttpHeaders(page, "/api/course/search");
        return new ResponseEntity<List<Course>>(list, httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Course> deleteById (@PathVariable Long id){
        courseMapper.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/class/{id}")
    public ResponseEntity<Course> delClass (@PathVariable Long id){
        courseClassMapper.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Course>> getAllCourse(){
        return new ResponseEntity<List<Course>>(courseMapper.selectList(new EntityWrapper<Course>()
                        .eq("enabled",true)),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getById(@PathVariable Long id){
        return new ResponseEntity<Course>(courseMapper.selectById(id),HttpStatus.OK);
    }

    @PostMapping("/class/insert")
    public ResponseEntity<CourseClass> insertA (@RequestBody CourseClass c){
        courseClassMapper.insert(c);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/class/search")
    public ResponseEntity<List<Class>> getCourseClass(@RequestParam Long courseId,
                                                  Pageable pageable) throws URISyntaxException {
        Page page = PageUtil.getPage(pageable);

        List<Long> classList = courseClassMapper.selectList(new EntityWrapper<CourseClass>()
                .eq("courseId",courseId)).stream().map(CourseClass::getClassId)
                .collect(Collectors.toList());

        List<Class> list = classMapper.selectPage(page,new EntityWrapper<Class>().in("id",classList));
        HttpHeaders httpHeaders = PageUtil.generateHttpHeaders(page, "/api/course/class/search");
        return new ResponseEntity<List<Class>>(list, httpHeaders, HttpStatus.OK);
    }
}
