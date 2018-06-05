package com.jsq.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.jsq.entity.Department;
import com.jsq.entity.Project;
import com.jsq.entity.User;
import com.jsq.service.DepartmentService;
import com.jsq.service.ProjectService;
import com.jsq.service.UserService;
import com.jsq.util.LoginInformationUtil;
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

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/api")
public class ApiController {

    private final UserService userService;

    private final ProjectService projectService;

    private final DepartmentService departmentService;

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal Principal principal, Model model){
        model.addAttribute("username", principal.getName());
        return "user/user";
    }


    /**
     * @api {get} /user/:id Request User information
     * @apiName GetUser
     * @apiGroup User
     *
     * @apiParam {Number} id Users unique ID.
     *
     * @apiSuccess {String} firstname Firstname of the User.
     * @apiSuccess {String} lastname  Lastname of the User.
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "firstname": "John",
     *       "lastname": "Doe"
     *     }
     *
     * @apiUse UserNotFoundError
     */

    /**
     * @api {put} /user/ Modify User information
     * @apiName PutUser
     * @apiGroup User
     *
     * @apiParam {Number} id          Users unique ID.
     * @apiParam {String} [firstname] Firstname of the User.
     * @apiParam {String} [lastname]  Lastname of the User.
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *
     * @apiUse UserNotFoundError
     */
    @GetMapping("/account")
    public ResponseEntity <Object> user(){
        Object object = SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        System.out.print(object);

        return  new ResponseEntity<Object>(object, HttpStatus.OK);
    }

    @PostMapping("/account/update")
    public ResponseEntity <Object> change(String newPassword){
        User user = LoginInformationUtil.getCurrentUser();
        user.setPassword(newPassword);
        userService.updateById(user);

        return  new ResponseEntity<Object>(user, HttpStatus.OK);
    }


    @GetMapping("/work/{id}")
    public ResponseEntity <List<Project>> getWork(@PathVariable Long id,@RequestParam(required = true) String type){

        List<Project> list = projectService.getWork(id,type);

        return  new ResponseEntity<List<Project>>(list, HttpStatus.OK);
    }


    /**
     *
     * 系部接口
     *
    */

    //新增系部
    @PostMapping("/department/insert")
    public ResponseEntity <Department> insertDept(@Valid @RequestBody Department department){
        return  new ResponseEntity<Department>(departmentService.insertDept(department), HttpStatus.OK);
    }

    //修改系部
    @PutMapping("/department/update")
    public ResponseEntity<Department> updateDept(@Valid @RequestBody Department department){
        return  new ResponseEntity<Department>(departmentService.updateDept(department), HttpStatus.OK);

    }

    @DeleteMapping("/department/{id}")
    public ResponseEntity deleteBudgetControlRule(@PathVariable Long id){
        departmentService.deleteDept(id);
        return ResponseEntity.ok().build();
    }

    //查询系部
    @GetMapping("/department/search")
    public ResponseEntity<List<Department>> selectDept(@RequestParam(required = false) String deptCode,
                                                       @RequestParam (required = false) String deptName,
                                                        Pageable pageable) throws URISyntaxException {
        Page page = PageUtil.getPage(pageable);

        List<Department> list = departmentService.selectDeptByOptions(deptCode,deptName,page);
        HttpHeaders httpHeaders = PageUtil.generateHttpHeaders(page, "/api/department/search");

        return new ResponseEntity<List<Department>>(list, httpHeaders, HttpStatus.OK);
    }

}
