package com.jss.oss.service.Impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.jss.oss.service.OssService;
import com.jss.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        String endpoint = ConstantPropertiesUtils.END_POIND;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try {
            OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
            //获取上传文件的输入流
            InputStream inputStream = file.getInputStream();
            //获取文件名称
            String filename = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            //得到不一样的filename的名字
            filename = uuid+filename;
            //把文件按照日期进行分类
            String datePath = new DateTime().toString("yyyy/MM/dd");//获取日期
            filename = datePath+"/"+filename;

            ossClient.putObject(bucketName,filename,inputStream);
            ossClient.shutdown();
            //将上传后文件路径返回 https://edu-10232.oss-cn-beijing.aliyuncs.com/01.jpg
            String url ="https://"+bucketName+"."+endpoint+"/"+filename;
            return url;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }
}
