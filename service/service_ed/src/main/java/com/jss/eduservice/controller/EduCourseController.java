package com.jss.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jss.commonutils.R;
import com.jss.eduservice.entity.EduCourse;
import com.jss.eduservice.entity.EduTeacher;
import com.jss.eduservice.entity.vo.CourseInfoVo;
import com.jss.eduservice.entity.vo.CoursePublishVo;
import com.jss.eduservice.entity.vo.CourseQuery;
import com.jss.eduservice.entity.vo.TeacherQuery;
import com.jss.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author liu
 * @since 2021-08-18
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;

    //课程列表查询
    @GetMapping
    public R getCourseList(){
        List<EduCourse> list = courseService.list(null);
        return R.ok().data("list",list);
    }

    //多条件分页查询
    @PostMapping("pageCourseCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) CourseQuery CourseQuery){
        Page<EduCourse> pageTeacher = new Page<>(current,limit);
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        String title = CourseQuery.getTitle();
        String  status = CourseQuery.getStatus();

        if(!StringUtils.isEmpty(title)){
            queryWrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(status)){
            queryWrapper.eq("status",status);
        }
        queryWrapper.orderByDesc("gmt_create");
        courseService.page(pageTeacher,queryWrapper);
        long total = pageTeacher.getTotal();
        List<EduCourse> records = pageTeacher.getRecords();
        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);
        return R.ok().data(map);
    }

    //返回添加后的id值，为了后面的大纲而用
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
       String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }
    //根据courseid查询课程的基本信息
    @GetMapping("getCouserInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    //修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程信息确定
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }

    //课程最终发布
    //修改课程最终状态
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return R.ok();
    }

    //删除课程
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        courseService.removeCourse(courseId);
        return R.ok();
    }
}

