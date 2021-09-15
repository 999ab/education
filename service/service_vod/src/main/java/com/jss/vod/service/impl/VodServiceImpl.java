package com.jss.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.jss.commonutils.R;
import com.jss.servicebase.exceptionhandler.CollegeException;
import com.jss.vod.service.VodService;
import com.jss.vod.utils.ConstantVodUtils;
import com.jss.vod.utils.InitVodClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {

    @Override
    public void removeAllAliyunVideo(List videoList) {
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.END_POIND,ConstantVodUtils.ACCESS_KEY_ID);
            DeleteVideoRequest request = new DeleteVideoRequest();
            String videoIds = StringUtils.join(videoList, ",");
            request.setVideoIds(videoIds);
            client.getAcsResponse(request);
        }catch (Exception e){
            e.printStackTrace();
            throw new CollegeException(20001,"删除阿里云视屏失败");
        }
    }

    @Override
    public String uploadVideoAly(MultipartFile file) {
        try{
            String fileName = file.getOriginalFilename();//上传的原始名
            String title = fileName.substring(0,fileName.lastIndexOf("."));//上传后的名称
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.END_POIND, ConstantVodUtils.ACCESS_KEY_ID,title,fileName,inputStream );
            System.out.println(ConstantVodUtils.END_POIND);
            System.out.println(ConstantVodUtils.ACCESS_KEY_ID);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
            // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else {
                videoId = response.getVideoId();
                System.out.println("11111");
            }
            return videoId;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("====上传视屏出现异常===");
            return null;
        }
    }
}