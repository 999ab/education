package com.jss.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jss.eduservice.entity.EduSubject;
import com.jss.eduservice.entity.excel.SubjectData;
import com.jss.eduservice.entity.subject.OneSubject;
import com.jss.eduservice.entity.subject.TwoSubject;
import com.jss.eduservice.listener.SubjectExcelListener;
import com.jss.eduservice.mapper.EduSubjectMapper;
import com.jss.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author liu
 * @since 2021-08-17
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //查询一级对象
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id", "0");
        List<EduSubject> oneSubjects = this.baseMapper.selectList(oneWrapper);
        //查询二级对象
        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper<>();
        oneWrapper.ne("parent_id", "0");
        List<EduSubject> twoSubjects = this.baseMapper.selectList(twoWrapper);
        //创建目标集合进行封装
        List<OneSubject> subjects = new ArrayList<>();
        //封装一级对
        for (int i = 0; i < oneSubjects.size(); i++) {
            OneSubject oneSubject = new OneSubject();
            EduSubject eduSubject = oneSubjects.get(i);
            oneSubject.setId(eduSubject.getId());
            oneSubject.setTitle(eduSubject.getTitle());// BeanUtils.copyProperties(eduSubject,oneSubject);
            subjects.add(oneSubject);

            //封装二级对象
            List<TwoSubject> finaltSubject = new ArrayList<>();
            for (int m = 0; m < twoSubjects.size(); m++) {
                EduSubject tSubject = twoSubjects.get(m);
                TwoSubject twoSubject = new TwoSubject();
                if(tSubject.getParentId().equals(oneSubject.getId())){
                    BeanUtils.copyProperties(tSubject, twoSubject);
                    finaltSubject.add(twoSubject);
                }
            }

            oneSubject.setChildren(finaltSubject);
        }
        return subjects;
    }
}