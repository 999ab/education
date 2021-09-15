package com.jss.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jss.commonutils.R;
import com.jss.eduservice.entity.EduCourse;
import com.jss.eduservice.entity.EduTeacher;
import com.jss.eduservice.service.EduCourseService;
import com.jss.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {
    @Autowired
    private EduTeacherService service;
    @Autowired
    private EduCourseService courseService;

    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page, @PathVariable long limit){
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);
        Map<String,Object> map = service.getTeacherFrontList(pageTeacher);
        return R.ok().data(map);
    }

    //根据讲师id查询
    @GetMapping("getTeacherFrontInto/{teacherId}")
    public R getTeacherFrontInto(@PathVariable String teacherId){
        //根据id查询讲师
        EduTeacher teacher = service.getById(teacherId);
        //根据id查询所有课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> list = courseService.list(wrapper);
        return R.ok().data("teacher",teacher).data("courseList",list);
    }

}
