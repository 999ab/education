package com.jss.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jss.commonutils.JwtUtils;
import com.jss.commonutils.R;
import com.jss.eduorder.entity.TOrder;
import com.jss.eduorder.service.TOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author liu
 * @since 2021-09-09
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class TOrderController {
    @Autowired
    private TOrderService orderService;
    @PostMapping("createOrder/{courseId}")
    public R saveOrder(@PathVariable String  courseId, HttpServletRequest request){
        //创建订单，返回订单号
        String ordderNo = orderService.createOrders(courseId,JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId",ordderNo);
    }

    //根据订单id查询订单信息
    @PostMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId){
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        TOrder tOrder = orderService.getOne(wrapper);
        return R.ok().data("item",tOrder);
    }

    //根据课程id和用户id查询订单表中的订单状态
    @GetMapping("isBuyCourse/{courseId}/{id}")
    public Boolean isBuyCourse(@PathVariable  String courseId,@PathVariable String id){
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",id);
        wrapper.eq("status",1);
        int count = orderService.count(wrapper);
        if(count > 0){
            return true;
        }else {
            return false;
        }
    }
}

