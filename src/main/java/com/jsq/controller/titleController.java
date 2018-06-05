package com.jsq.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jsq.entity.*;
import com.jsq.entity.Class;
import com.jsq.mapper.CourseClassMapper;
import com.jsq.mapper.TitleHeadMapper;
import com.jsq.mapper.TitleScoreMapper;
import com.jsq.mapper.UserClassMapper;
import com.jsq.service.TitleService;
import com.jsq.util.LoginInformationUtil;
import com.jsq.util.PageUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.*;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/api/title")
public class titleController {

    private final TitleHeadMapper titleHeadMapper;

    private final TitleService titleService;

    private final TitleScoreMapper titleScoreMapper;

    private final UserClassMapper userClassMapper;

    private CourseClassMapper courseClassMapper;

    @PostMapping("/head/insert")
    public ResponseEntity<TitleHead> addTitleHead (@Valid @RequestBody TitleHead titleHead){

        return new ResponseEntity<TitleHead>(titleService.insertHead(titleHead),HttpStatus.OK);
    }

    @GetMapping("/head/search")
    public ResponseEntity<List<TitleHead>> selectPage (@RequestParam(required = false) String titleNumber,
                                                        @RequestParam(required = false) String titleName,
                                                        @RequestParam(required = false) String titleType,
                                                       Pageable pageable) throws URISyntaxException{

        Page page = PageUtil.getPage(pageable);
        List<TitleHead> list = titleHeadMapper.selectPage(page,new EntityWrapper<TitleHead>()
                .eq(titleNumber!=null,"titleNumber",titleNumber)
                .like(titleName!=null,"titleName",titleName)
                .eq(titleType!=null,"titleType",titleType)
                .orderBy("titleNumber"));

        HttpHeaders httpHeaders = PageUtil.generateHttpHeaders(page, "/api/title/head/search");

        return new ResponseEntity<List<TitleHead>>(list, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/head/get/{id}")
    public ResponseEntity<TitleHead> getTitle(@PathVariable Long id){

        return new ResponseEntity<TitleHead>(titleHeadMapper.selectOne(TitleHead.builder().id(id).enabled(true).build()),HttpStatus.OK);
    }

    @DeleteMapping("/head/delete/{id}")
    public ResponseEntity<TitleHead> deleteClass(@PathVariable Long id){
        try{
            String path = "D:\\jsq\\graduation-design-spring\\src\\main\\resources\\templates\\title";
            String titleName = titleHeadMapper.selectOne(TitleHead.builder().id(id).enabled(true).build()).getTitleName();
            File file = new File(path,titleName+".txt");
            file.deleteOnExit();
        }catch (Exception e){
            System.out.print(e);
        }
        titleHeadMapper.deleteById(id);
        return ResponseEntity.ok().build();
    }

    //添加题目
    @PostMapping("/line/add/{id}")
    public ResponseEntity<TitleLine> addTitleLine (@Valid @RequestBody TitleLine titleLine,@PathVariable Long id){

        return new ResponseEntity<TitleLine>(titleService.addTitleLine(titleLine,id),HttpStatus.OK);
    }

    //删除题目
    @DeleteMapping("/line/delete/{uuid}")
    public ResponseEntity<TitleLine> delTitleLine (@PathVariable UUID uuid,@RequestParam Long id){
        titleService.deleteLine(uuid,id);
        return ResponseEntity.ok().build();
    }

    //查询题目
    @RequestMapping("/line/search")
    public ResponseEntity<List<TitleLine>> searchTitle (@RequestParam Long id,
                                                        @RequestParam (required = false,defaultValue = "false") boolean flag){

        return new ResponseEntity<List<TitleLine>>(titleService.getInfo(flag,id),HttpStatus.OK);
    }

    //评分
    @RequestMapping("/get/score/test")
    public ResponseEntity<TitleLine> getScore (@Valid @RequestBody List<TitleLine> titleScoreList,
                                                @RequestParam Long headId, @RequestParam String userNumber){

        titleService.getScore(titleScoreList,headId,userNumber);
        return ResponseEntity.ok().build();
    }

    //获取考试成绩
    @GetMapping("/test/score")
    public ResponseEntity<List<TitleScore>> titleScore( Pageable pageable) throws URISyntaxException{
        String userNumber = LoginInformationUtil.getCurrentUser().getUserNumber();
        Page page = PageUtil.getPage(pageable);
        List<TitleScore> list =  titleScoreMapper.selectPage(page,new EntityWrapper<TitleScore>()
                .eq("userNumber",userNumber))
                .stream().map(e->{
                    e.setTitleName(titleHeadMapper.selectOne(TitleHead.builder().enabled(true).id(e.getTitleHId()).build()).getTitleName());
                    return e;
                }).collect(Collectors.toList());
        HttpHeaders httpHeaders = PageUtil.generateHttpHeaders(page, "/api/title/test/score");
        return new ResponseEntity<List<TitleScore>>(list,HttpStatus.OK);
    }

    @RequestMapping("upload")
    public String fileUpload( HttpServletRequest request) {
        String filePath = request.getSession().getServletContext().getRealPath("/");
        try {
            InputStream inputStream  = request.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(new File("C:\\Users\\jsq\\Desktop\\a.txt"));
            int c;
            while ((c = inputStream.read())!= -1){
                fileOutputStream.write(c);
            }
        }catch (Exception e){
            System.out.print(e);
        }

      /*  // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                // 文件保存路径
                String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/"
                        + file.getOriginalFilename();
                // 转存文件
                file.transferTo(new File(filePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        // 重定向
        return "redirect:/list.html";
    }


    @GetMapping("/head/{id}/search")
    public ResponseEntity<List<TitleHead>> selectStuPage (@PathVariable Long id, Pageable pageable) throws URISyntaxException{

        Page page = PageUtil.getPage(pageable);

        List<Long> courseIds = courseClassMapper.selectList(new EntityWrapper<CourseClass>()
                .eq("classId",userClassMapper.selectOne(new UserClass().builder().userId(id).build()).getClassId()))
                .stream().map(CourseClass::getCourseId).collect(Collectors.toList());

        List<TitleHead> list = titleHeadMapper.selectPage(page,new EntityWrapper<TitleHead>()
                .in("courseId",courseIds)
                .orderBy("titleNumber"))
                .stream()
                .map(item->{
                    TitleScore titleScore = null;
                    List<TitleScore> listScore = titleScoreMapper.selectList(new EntityWrapper<TitleScore>()
                            .eq("titleHId",item.getId()));
                    if(listScore.size() > 0){
                        item.setStatus("已做");
                    }else {
                        item.setStatus("待做");
                    }
                    return item;
                }).collect(Collectors.toList());

        HttpHeaders httpHeaders = PageUtil.generateHttpHeaders(page, "/api/title/head/search");

        return new ResponseEntity<List<TitleHead>>(list, httpHeaders, HttpStatus.OK);
    }


}
