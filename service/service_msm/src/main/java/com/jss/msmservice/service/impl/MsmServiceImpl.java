package com.jss.msmservice.service.impl;

import com.jss.msmservice.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(Map<String, Object> param, String phone) {
        if(StringUtils.isEmpty(phone)){
            return false;
        }
        System.out.println("=================");
        System.out.println(param.get("code"));
        return true;
    }
}
