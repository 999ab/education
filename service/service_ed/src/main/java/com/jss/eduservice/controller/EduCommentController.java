package com.jss.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jss.commonutils.R;
import com.jss.eduservice.entity.EduComment;
import com.jss.eduservice.entity.EduTeacher;
import com.jss.eduservice.service.EduCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author liu
 * @since 2021-09-09
 */
@RestController
@RequestMapping("/eduservice/comment")
public class EduCommentController {
    @Autowired
    private EduCommentService service;
    @PostMapping("pagecomment/{page}/{limit}")
    public R pageComment(@PathVariable long page,@PathVariable long limit,String courseId){
        Page<EduComment> commentPage = new Page<>(page,limit);
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        service.page(commentPage, wrapper);
        long current = commentPage.getCurrent();
        List<EduComment> records = commentPage.getRecords();
        long size = commentPage.getSize();
        long pages = commentPage.getPages();
        long total = commentPage.getTotal();
        boolean hasNext = commentPage.hasNext();
        boolean hasPrevious = commentPage.hasPrevious();

        Map<String,Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return R.ok().data(map);
    }

    @PostMapping("save")
    public R save(@RequestBody EduComment comment){
        service.save(comment);
        return R.ok();
    }
}

