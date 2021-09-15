package com.jss.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jss.commonutils.R;
import com.jss.eduservice.entity.EduTeacher;
import com.jss.eduservice.entity.vo.TeacherQuery;
import com.jss.eduservice.service.EduTeacherService;
import com.jss.servicebase.exceptionhandler.CollegeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author liu
 * @since 2021-08-03
 */
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;
    //查找所有
    @GetMapping("findAll")
    public R findAll(){
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items",list);
    }

    //根据id删除
    @DeleteMapping("{id}")
    public R deleteTeacher(@PathVariable(value = "id") String id){
        boolean b = eduTeacherService.removeById(id);
        if(b){
            return R.ok();
        }else{
            return R.error();
        }
    }

    //分页查询
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit){
        Page<EduTeacher> page = new Page<>(current,limit);
        IPage<EduTeacher> iPage = eduTeacherService.page(page, null);
        long total = page.getTotal();//总计录数
        List<EduTeacher> records = page.getRecords();//数据list集合
        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);
        return R.ok().data(map);
    }

    //多条件分页查询
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if(!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            queryWrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            queryWrapper.gt("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            queryWrapper.gt("gmt_modified",end);
        }
        queryWrapper.orderByDesc("gmt_create");
        eduTeacherService.page(pageTeacher,queryWrapper);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);
        return R.ok().data(map);
    }

    //添加导师
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if(save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //根据导师id进行查询
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        EduTeacher byId = eduTeacherService.getById(id);
        return R.ok().data("teacher",byId);
    }

    //导师修改
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean b = eduTeacherService.updateById(eduTeacher);
        if(b){
            return R.ok();
        }else {
            return R.error();
        }
    }
}

