package com.jss.eduservice.controller;

import com.jss.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin
public class EduLoginController {

    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }

    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","admin").data("name","adminn").data("avatar","https://img0.baidu.com/it/u=163597491,234850794&fm=26&fmt=auto&gp=0.jpg");
    }
}
