package com.jss.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jss.commonutils.R;
import com.jss.educms.entity.CrmBanner;
import com.jss.educms.service.CrmBannerService;
import com.jss.servicebase.exceptionhandler.CollegeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 后端控制
 * </p>
 *
 * @author liu
 * @since 2021-08-31
 */
@RestController
@RequestMapping("/educms/banner")
@CrossOrigin
public class BannerAdminController {
    @Autowired
    private CrmBannerService bannerService;
    //分页查询
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit){
        Page<CrmBanner> bannerPage = new Page<>(page,limit);
        bannerService.page(bannerPage,null);
        return R.ok().data("items",bannerPage.getRecords()).data("total",bannerPage.getTotal());
    }

    //添加banner
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        boolean save = bannerService.save(crmBanner);
        if(!save){
            throw new CollegeException(20001,"添加banner失败");
        }
        return R.ok();
    }
    //删除banner
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id){
        bannerService.removeById(id);
        return R.ok();
    }
    //根据id查询
    @GetMapping("get/{id}")
    public R get(@PathVariable String id){
        CrmBanner byId = bannerService.getById(id);
        return R.ok().data("item",byId);
    }
    //修改banner
    @PutMapping("update")
    public R update(@RequestBody CrmBanner crmBanner){
        bannerService.updateById(crmBanner);
        return R.ok();
    }
}

