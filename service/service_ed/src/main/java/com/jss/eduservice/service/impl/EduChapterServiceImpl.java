package com.jss.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jss.eduservice.entity.EduChapter;
import com.jss.eduservice.entity.EduVideo;
import com.jss.eduservice.entity.chapter.ChapterVo;
import com.jss.eduservice.entity.chapter.VideoVo;
import com.jss.eduservice.mapper.EduChapterMapper;
import com.jss.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jss.eduservice.service.EduVideoService;
import com.jss.servicebase.exceptionhandler.CollegeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author liu
 * @since 2021-08-18
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService videoService;
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //根据courseId查询出所有的章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> ChaptersList = this.baseMapper.selectList(wrapperChapter);
        //根据courseId查询出所有的小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> videosList = videoService.list(wrapperVideo);
        //遍历查询所有章节list集合进行封装
        List<ChapterVo> chapterVolist = new ArrayList<>();
        for (int i = 0; i < ChaptersList.size(); i++) {
            EduChapter eduChapter = ChaptersList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            chapterVolist.add(chapterVo);
            //遍历查询所有小节进行封装
            List<VideoVo> videoVoList = new ArrayList<>();
            for (int m = 0; m < videosList.size(); m++) {
                EduVideo eduVideo = videosList.get(m);
                VideoVo videoVo = new VideoVo();
                if(eduVideo.getChapterId().equals(eduChapter.getId())){
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoList);
        }

        return chapterVolist;
    }


    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        if(count > 0){//有数据，不能进行删除
            throw new CollegeException(20001,"不能删除");
        }else {//没有数据，可以删除
            int result = this.baseMapper.deleteById(chapterId);
           return result>0;
        }
    }

    @Override
    public void removeChapterById(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        this.baseMapper.delete(wrapper);
     }
}
