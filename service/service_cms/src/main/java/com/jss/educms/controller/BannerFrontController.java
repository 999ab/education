package com.jss.educms.controller;

import com.jss.commonutils.R;
import com.jss.educms.entity.CrmBanner;
import com.jss.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
        * <p>
 * 首页banner表 前端控制
         * </p>
        *
        * @author liu
        * @since 2021-08-31
        */
@RestController
@CrossOrigin
@RequestMapping("/educms/bannerfront")
public class BannerFrontController {
    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("getAllBanner")
    public R getAllBanner(){
       List<CrmBanner> list = bannerService.selectAllBanner();
       return R.ok().data("list",list);
    }
}
