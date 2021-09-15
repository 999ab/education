package com.jss.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jss.commonutils.JwtUtils;
import com.jss.commonutils.R;
import com.jss.commonutils.ordervo.CourseWebOrder;
import com.jss.eduservice.client.OrdersClient;
import com.jss.eduservice.entity.EduCourse;
import com.jss.eduservice.entity.chapter.ChapterVo;
import com.jss.eduservice.entity.vo.CourseWebVo;
import com.jss.eduservice.entity.vo.frontVo.CourseFrontVo;
import com.jss.eduservice.service.EduChapterService;
import com.jss.eduservice.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {
    @Autowired
    private EduCourseService service;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private OrdersClient ordersClient;
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> pageCourse = new Page<>(page,limit);
        Map<String,Object> map = service.getCourseFrontList(pageCourse,courseFrontVo);
        return R.ok().data(map);
    }
    //课程详情的方法
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){
        //根据课程id，编写sql语句查询课程信息
        CourseWebVo courseWebVo = service.getBaseCourseInfo(courseId);
        //根据课程id查询章节和小节
        List<ChapterVo> chapterVideoByCourseId = chapterService.getChapterVideoByCourseId(courseId);
        boolean isBuy = ordersClient.isBuyCourse("courseId", JwtUtils.getMemberIdByJwtToken(request));
        return  R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoByCourseId).data("isBuy",isBuy);
    }
    //根据课程id查询课程信息
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebOrder getCourseInfoOrder(@PathVariable String id){
        CourseWebVo info = service.getBaseCourseInfo(id);
        CourseWebOrder courseWebOrder = new CourseWebOrder();
        BeanUtils.copyProperties(info,courseWebOrder);
        return courseWebOrder;
    }
}
