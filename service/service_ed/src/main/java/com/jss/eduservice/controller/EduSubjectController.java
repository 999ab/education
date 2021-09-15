package com.jss.eduservice.controller;


import com.jss.commonutils.R;
import com.jss.eduservice.entity.subject.OneSubject;
import com.jss.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;


import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author liu
 * @since 2021-08-17
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubject;

    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        eduSubject.saveSubject(file,eduSubject);
        return R.ok();
    }
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list = eduSubject.getAllOneTwoSubject();

        return R.ok().data("list",list);
    }
}

