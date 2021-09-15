package com.jss.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jss.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jss.eduservice.entity.vo.CourseInfoVo;
import com.jss.eduservice.entity.vo.CoursePublishVo;
import com.jss.eduservice.entity.vo.CourseWebVo;
import com.jss.eduservice.entity.vo.frontVo.CourseFrontVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author liu
 * @since 2021-08-18
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo publishCourseInfo(String id);

    void removeCourse(String courseId);

    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
