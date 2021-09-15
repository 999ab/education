package com.jss.eduorder.controller;


import com.jss.commonutils.R;
import com.jss.eduorder.service.TPayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author liu
 * @since 2021-09-09
 */
@RestController
@RequestMapping("/eduorder/paylog")
@CrossOrigin
public class TPayLogController {
    @Autowired
    private TPayLogService payLogService;

    //创建二维码
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        Map map = payLogService.createNative(orderNo);
        return R.ok().data(map);
    }

    //查询订单支付状态
    @GetMapping("queryPayStatus/{orderNo}")
    public  R queryPayStatus(@PathVariable String orderNo){
        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        if(map == null){
            return R.error().message("支付错误");
        }
        if(map.get("trade_state").equals("SUCCESS")){
            payLogService.updateOrderStatus(map);
            return R.ok();
        }
        return R.ok().message("支付中");
    }
}

