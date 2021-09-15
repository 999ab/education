package com.jss.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jss.eduservice.client.VodClient;
import com.jss.eduservice.entity.EduVideo;
import com.jss.eduservice.mapper.EduVideoMapper;
import com.jss.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author liu
 * @since 2021-08-18
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Autowired
    private VodClient vodClient;
    @Override
    public void removeVideoByCourseId(String courseId) {
        //根据课程id查询，查询所有视频的id
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        wrapperVideo.select("video_source_id");
        List<EduVideo> videosList = this.baseMapper.selectList(wrapperVideo);
        List<String> videos = new ArrayList<>();
        for (int i = 0; i < videosList.size(); i++) {
            EduVideo eduVideo = videosList.get(i);
            String videoSourceId = eduVideo.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)) {
                videos.add(videoSourceId);
            }
        }
        if(videos.size()>0){
            vodClient.deleteBatch(videos);
        }
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        this.baseMapper.delete(wrapper);
    }
}
