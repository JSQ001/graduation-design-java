package com.jsq.web;


import com.jsq.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jsq on 2018/1/8.
 */

@RestController
@RequestMapping("/test")
public class UserInfoController {
   // private UserInfoService userInfoService;

  //  public UserInfoController(UserInfoService userInfoService){this.userInfoService = userInfoService;}

    @GetMapping
    ResponseEntity<User> getInfo(){
    //    System.out.println(userInfoService.selectOne(new EntityWrapper<User>().eq("name","jsq")));
        return ResponseEntity.ok(null);

    }
}
