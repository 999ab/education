package com.jss.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jss.commonutils.R;
import com.jss.staservice.client.UcenterClient;
import com.jss.staservice.entity.StatisticsDaily;
import com.jss.staservice.mapper.StatisticsDailyMapper;
import com.jss.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author liu
 * @since 2021-09-11
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Autowired
    public UcenterClient client;
    @Override
    public void registerCount(String day) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);

        R register = client.countRegister(day);
        Integer count = (Integer) register.getData().get("countRegister");
        Integer loginNum = RandomUtils.nextInt(100, 200);
        Integer videoViewNum = RandomUtils.nextInt(100, 200);
        Integer courseNum = RandomUtils.nextInt(100, 200);
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setRegisterNum(count);
        statisticsDaily.setDateCalculated(day);

        statisticsDaily.setVideoViewNum(videoViewNum);
        statisticsDaily.setCourseNum(courseNum);
        statisticsDaily.setLoginNum(loginNum);
        baseMapper.insert(statisticsDaily);
    }

    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type);
        List<StatisticsDaily> staList = baseMapper.selectList(wrapper);
        List<String> date = new ArrayList<>();
        List<Integer> num = new ArrayList<>();
        for (int i = 0; i < staList.size(); i++) {
            StatisticsDaily daily = staList.get(i);
            date.add(daily.getDateCalculated());
            switch (type){
                case "login_num":
                    num.add(daily.getLoginNum());
                    break;
                case "register_num":
                    num.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    num.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    num.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("date_calculatedList",date);
        map.put("numDataList",num);
        return map;
    }
}
