package com.jss.msmservice.controller;

import com.jss.commonutils.R;
import com.jss.msmservice.service.MsmService;
import com.jss.msmservice.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RequestMapping("/edumsm/msm")
@RestController
@CrossOrigin
public class MsmController {
    @Autowired
    private MsmService service;
    @Autowired
    private RedisTemplate<String,String> template;
    @GetMapping("/send/{phone}")
    public R sendMsm(@PathVariable String  phone){
        //先从redis获取，有则返回无则发送
        String code = template.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)){
            return R.ok();
        }
        code = RandomUtils.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        boolean isSend = service.send(param,phone);
        if(isSend){
            template.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return R.ok();
        }else{
            return R.error().message("短信发送失败");
        }
    }
}
