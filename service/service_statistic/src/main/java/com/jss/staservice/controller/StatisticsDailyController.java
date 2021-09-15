package com.jss.staservice.controller;


import com.jss.commonutils.R;
import com.jss.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author liu
 * @since 2021-09-11
 */
@RestController
@CrossOrigin
@RequestMapping("/staservice/sta")
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService service;
    //统计一天的浏览人数
    @PostMapping("registerCount/{day}")
    public R registerCount(@PathVariable String day){
        service.registerCount(day);
        return R.ok();
    }

    //图标显示，返回两部分，日期json数组，数量json
    @GetMapping("show/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,@PathVariable String begin,@PathVariable String end){
        Map<String,Object> map = service.getShowData(type,begin,end);
        return R.ok().data(map);
    }
}

