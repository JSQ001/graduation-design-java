package com.jsq.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jsq.entity.Department;
import com.jsq.entity.Project;
import com.jsq.entity.User;
import com.jsq.entity.enumeration.StudentImportCode;
import com.jsq.service.DepartmentService;
import com.jsq.service.ProjectService;
import com.jsq.service.StudentImportService;
import com.jsq.service.UserService;
import com.jsq.util.PageUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    private final StudentImportService studentImportService;


    /**
     * @apiDefine UserNotFoundError
     *
     * @apiError UserNotFound The id of the User was not found.
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 404 Not Found
     *     {
     *       "error": "UserNotFound"
     *     }
     */

    @PostMapping("/insert")
    public ResponseEntity<User> user(@Valid @RequestBody User user){
        return  new ResponseEntity<User>(userService.insertUser(user), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> selectDept(@RequestParam (required = false) Long deptId,
                                                 @RequestParam (required = false) Long classId,
                                                  @RequestParam (required = false) String nickName,
                                                  @RequestParam (required = true)  String type,
                                                  @RequestParam (required = false) String userNumber,
                                                  @RequestParam (required = false,defaultValue = "true") boolean enabled,
                                                   Pageable pageable) throws URISyntaxException {
        Page page = PageUtil.getPage(pageable);
        List<User> list = userService.getUserByOptions(deptId,classId,nickName,userNumber,enabled,page, type);
        HttpHeaders httpHeaders = PageUtil.generateHttpHeaders(page, "/api/user/search");
        return new ResponseEntity<List<User>>(list, httpHeaders, HttpStatus.OK);
    }

    //获取所以教师
    @GetMapping("teacher/all")
    public ResponseEntity<List<User>> getAllTeacher(){
        return new ResponseEntity<List<User>>(userService.getAllTeacher(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteById (@PathVariable Long id){
         userService.deleteById(id);
        return ResponseEntity.ok().build();
    }


    //修改密码
    @PutMapping("/update")
    public ResponseEntity<User> updatePassword (@RequestBody User user){
        userService.updateById(user);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 导入
     * */

    //下载导入模板
    @GetMapping("/export/template")
    public ResponseEntity<byte[]> exportTemplate(){
        byte[] result = studentImportService.exportTemplate();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/import")
    public ResponseEntity importItem(@RequestParam MultipartFile file) throws IOException {
        InputStream in = file.getInputStream();
        UUID transactionLogUUID = studentImportService.importStudent(in);
        in.close();
        HashMap<String,UUID> result = new HashMap<>();
        result.put("transactionID",transactionLogUUID);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/export/failed/data/{transactionID}")
    public ResponseEntity<byte[]> exportFailedData(@PathVariable UUID transactionID){
        return ResponseEntity.ok(studentImportService.exportFailedData(transactionID));
    }

}
