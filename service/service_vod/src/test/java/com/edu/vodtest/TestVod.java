package com.edu.vodtest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

public class TestVod {
    public static void main(String[] args) throws  Exception{
    //上传视频的方法
//        String accessKeyId ="LTAI5tHQcHzEAKqiZ9cQQHET";
//        String accessKeySecret = "YhAarcyxVxCNM0Mjr0gUx2UjBaMuv9";
//        String title = "001";//在阿里云中显示的文件名字
//        String fileName = "D:/mvp4/001.mp4";//本地文件的路径和名称
//        UploadVideoRequest request = new UploadVideoRequest(accessKeyId,accessKeySecret,title,fileName);
//        request.setPartSize(2 * 1024*1024L);
//        request.setTaskNum(1);
//
//        UploadVideoImpl upload = new UploadVideoImpl();
//        UploadVideoResponse response = upload.uploadVideo(request);
//        System.out.println("Request="+ response.getRequestId() + "\n");
//        if(response.isSuccess()){
//            System.out.println("VideoId="+ response.getVideoId() + "\n");
//        }else {
//            System.out.println("VideoId=" + response.getVideoId()+"\n");
//            System.out.println("ErrorCode=" + response.getCode() + "\n");
//            System.out.println("ErrorMessage=" + response.getMessage() + "\n");
//        }
        getPlayAuth();
    }

    //获取视频凭证，来解码加密视频
    public static void getPlayAuth() throws  Exception{
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tHQcHzEAKqiZ9cQQHET","YhAarcyxVxCNM0Mjr0gUx2UjBaMuv9");
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        request.setVideoId("5e9db5e313ab4045a85a96cf850d9d7c");
        response = client.getAcsResponse(request);
        System.out.println("playauth:"+response.getPlayAuth());
    }

    //根据视频id获取视频播放地址
    public static void getPlayUrl() throws Exception{
        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tHQcHzEAKqiZ9cQQHET","YhAarcyxVxCNM0Mjr0gUx2UjBaMuv9");
        //创建获取视频地址reques和response
        GetPlayInfoRequest request =new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        //向request对象里面设置视频id
        request.setVideoId("0c27c249c0804b9c9d3abd92a7c8bd01");
        //调用初始化对象中的方法，传递request，获取数据
        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }


}
