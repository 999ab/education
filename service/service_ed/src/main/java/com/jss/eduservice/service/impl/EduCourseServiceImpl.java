package com.jss.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jss.eduservice.entity.EduCourse;
import com.jss.eduservice.entity.EduCourseDescription;
import com.jss.eduservice.entity.EduTeacher;
import com.jss.eduservice.entity.EduVideo;
import com.jss.eduservice.entity.vo.CourseInfoVo;
import com.jss.eduservice.entity.vo.CoursePublishVo;
import com.jss.eduservice.entity.vo.CourseWebVo;
import com.jss.eduservice.entity.vo.frontVo.CourseFrontVo;
import com.jss.eduservice.mapper.EduCourseMapper;
import com.jss.eduservice.service.EduChapterService;
import com.jss.eduservice.service.EduCourseDescriptionService;
import com.jss.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jss.eduservice.service.EduVideoService;
import com.jss.servicebase.exceptionhandler.CollegeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Wrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author liu
 * @since 2021-08-18
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private EduChapterService ChapterService;
    @Override
    public String  saveCourseInfo(CourseInfoVo courseInfoVo) {
        //向课程表里面添加课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = this.baseMapper.insert(eduCourse);
        if(insert == 0){
            throw new CollegeException(20001,"添加失败");
        }
        String cid = eduCourse.getId();
        //向课程简介添加课程简介
        EduCourseDescription description = new EduCourseDescription();
        description.setDescription(courseInfoVo.getDescription());
        description.setId(cid);
        courseDescriptionService.save(description);
        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        baseMapper.updateById(eduCourse);
        //修改描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(description);
    }

    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        CoursePublishVo publishCourseInfo = this.baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    @Override
    public void removeCourse(String courseId) {
        //根据课程id删除小节
        eduVideoService.removeVideoByCourseId(courseId);
        //根据课程id删除章节
        ChapterService.removeChapterById(courseId);
        //根据课程id删除描述
        courseDescriptionService.removeById(courseId);
        //根据课程id删除课程本身
        int result = this.baseMapper.deleteById(courseId);
//        System.out.println(result);
        if(result == 0){
            throw new CollegeException(20001,"删除失败");
        }
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){ //一级分类
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())){ //二级分类
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){ //关注度排序
            wrapper.orderByDesc("buy_count");
        }
        if(!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())){ //最新的时间
            wrapper.orderByDesc("gmt_create");
        }
        if(!StringUtils.isEmpty(courseFrontVo.getPriceSort())){ //价格
            wrapper.orderByDesc("price");
        }


        baseMapper.selectPage(pageCourse, wrapper);
        long current = pageCourse.getCurrent();
        List<EduCourse> records = pageCourse.getRecords();
        long size = pageCourse.getSize();
        long pages = pageCourse.getPages();
        long total = pageCourse.getTotal();
        boolean hasNext = pageCourse.hasNext();
        boolean hasPrevious = pageCourse.hasPrevious();

        Map<String,Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
