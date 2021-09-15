package com.jss.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jss.commonutils.R;
import com.jss.eduservice.entity.EduCourse;
import com.jss.eduservice.entity.EduTeacher;
import com.jss.eduservice.service.EduCourseService;
import com.jss.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {
    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;
    //查询8条热门课程，4条讲师
    @GetMapping("index")
    public R index(){
        QueryWrapper<EduCourse>  wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> list = courseService.list(wrapper);


        QueryWrapper<EduTeacher>  teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("id");
        teacherQueryWrapper.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(teacherQueryWrapper);
        return R.ok().data("eduList",list).data("teacherList",teacherList);
    }
}
