package com.jss.staservice.schedule;

import com.jss.staservice.service.StatisticsDailyService;
import com.jss.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTask {
    @Autowired
    private StatisticsDailyService service;
    @Scheduled(cron = "0 0 1 * * ?")
    public void task(){

        service.registerCount(DateUtil.formatDate(DateUtil.addDateS(new Date(),-1)));
        System.out.println("&&&&&&&&&&**********");
    }
}
