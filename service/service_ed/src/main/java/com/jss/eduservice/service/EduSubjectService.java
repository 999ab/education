package com.jss.eduservice.service;

import com.jss.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jss.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author liu
 * @since 2021-08-17
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file,EduSubjectService eduSubjectService);

    List<OneSubject> getAllOneTwoSubject();
}
