package com.jss.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.jss.commonutils.R;
import com.jss.servicebase.exceptionhandler.CollegeException;
import com.jss.vod.service.VodService;
import com.jss.vod.utils.ConstantVodUtils;
import com.jss.vod.utils.InitVodClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {
    @Autowired
    private VodService service;
    //上传视频到阿里云的方法
    @PostMapping("uploadAlyiVideo")
    public R uploadAlyiVideo(MultipartFile file){
        String videoId = service.uploadVideoAly(file);
        return R.ok().data("video",videoId);
    }

    //根据视屏id删除阿里云视屏
    @DeleteMapping("removeAlyiVideo/{id}")
    public R removeAlyiVideo(@PathVariable String id){
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.END_POIND,ConstantVodUtils.ACCESS_KEY_ID);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            client.getAcsResponse(request);
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            throw new CollegeException(20001,"删除阿里云视屏失败");
        }
    }
    //删除多个视频
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoList") List videoList){
        service.removeAllAliyunVideo(videoList);
        return R.ok();

    }
    //根据视频id获取视频凭证
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id){
        try {
            DefaultAcsClient client =
                    InitVodClient.initVodClient(ConstantVodUtils.END_POIND,ConstantVodUtils.ACCESS_KEY_ID);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        }catch (Exception e){
            throw new CollegeException(20001,"视频播放失败");
        }
    }
}
