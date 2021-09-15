package com.jss.eduservice.service;

import com.jss.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jss.eduservice.entity.chapter.ChapterVo;
import com.jss.eduservice.entity.vo.CourseInfoVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author liu
 * @since 2021-08-18
 */
public interface EduChapterService extends IService<EduChapter> {
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterById(String courseId);
}
