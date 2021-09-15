package com.jss.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jss.eduservice.entity.EduSubject;
import com.jss.eduservice.entity.excel.SubjectData;
import com.jss.eduservice.service.EduSubjectService;
import com.jss.servicebase.exceptionhandler.CollegeException;



public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    public EduSubjectService subjectService;

    public SubjectExcelListener(){}

    public SubjectExcelListener(EduSubjectService subjectService){
        this.subjectService = subjectService;
    }
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData == null){
            throw new CollegeException(20001,"文件数据为空");
        }
        //判断一级分类

        EduSubject subject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if(subject == null){
            subject = new EduSubject();
            subject.setParentId("0");
            subject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(subject);
        }
        //判断二级分类
        String pid = subject.getId();
        EduSubject eduSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
        if(eduSubject == null){
            eduSubject = new EduSubject();
            eduSubject.setParentId(pid);
            eduSubject.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(eduSubject);
        }
    }


    //判断一级分类不能重复添加
    private EduSubject existOneSubject(EduSubjectService subjectService,String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",0);
        EduSubject one = subjectService.getOne(wrapper);
        return one;
    }
    //判断二级分类不能重复添加
    private EduSubject existTwoSubject(EduSubjectService subjectService,String name,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject two = subjectService.getOne(wrapper);
        return two;
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
