package com.jss.oss.controller;

import com.jss.commonutils.R;
import com.jss.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;
    @PostMapping
    public R uploadOssFile(MultipartFile file){
        //上传文件
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }
}
