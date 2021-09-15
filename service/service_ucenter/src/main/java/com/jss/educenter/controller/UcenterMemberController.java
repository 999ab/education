package com.jss.educenter.controller;


import com.jss.commonutils.JwtUtils;
import com.jss.commonutils.R;
import com.jss.commonutils.ordervo.UcenterMemberOreder;
import com.jss.educenter.entity.UcenterMember;
import com.jss.educenter.entity.vo.RegisterVo;
import com.jss.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author liu
 * @since 2021-09-03
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService service;
    //登录
    @PostMapping("login")
    public R login(@RequestBody UcenterMember member){
        String  token = service.login(member);
        return R.ok().data("token",token);
    }
    //注册
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        service.register(registerVo);
        return R.ok();
    }
    //根据token获取用户信息
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        String id = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = service.getById(id);
        return R.ok().data("userInfo",member);
    }

    //根据用户id获取用户信息
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOreder getUserInfoOrder(@PathVariable String id){
        UcenterMember m = service.getById(id);
        //把m对象的值给UcenterMemberOreder对象
        UcenterMemberOreder memberOreder = new UcenterMemberOreder();
        BeanUtils.copyProperties(m,memberOreder);
        return memberOreder;
    }
    //查询某一天的注册人数
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day){
        Integer count = service.countRegisterDay(day);
        return R.ok().data("countRegister",count);
    }
}

