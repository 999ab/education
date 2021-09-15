package com.jss.eduservice.controller;


import com.jss.commonutils.R;
import com.jss.eduservice.client.VodClient;
import com.jss.eduservice.entity.EduChapter;
import com.jss.eduservice.entity.EduVideo;
import com.jss.eduservice.service.EduVideoService;
import com.jss.servicebase.exceptionhandler.CollegeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author liu
 * @since 2021-08-18
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;
    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }
    //删除小节，删除对应的阿里云视频
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
        //根据小节id获取视频id
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)){
            //根据视频id，远程删除阿里云视频
            R result = vodClient.removeAlyiVideo(videoSourceId);
            if(result.getCode() == 20001){
                throw new CollegeException(20001,"删除失败，熔断器");
            }
        }
        videoService.removeById(id);
        return R.ok();
    }
    //修改小节
    @PostMapping("updateVideo")
    public R updateChapter(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return R.ok();
    }
    //根据id查询小节
    @GetMapping("getVideoById/{videoId}")
    public R getVideoById(@PathVariable String videoId){
        EduVideo video = videoService.getById(videoId);
        return R.ok().data("video",video);
    }
}

