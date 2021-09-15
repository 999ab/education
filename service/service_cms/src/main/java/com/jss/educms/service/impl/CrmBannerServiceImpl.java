package com.jss.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jss.educms.entity.CrmBanner;
import com.jss.educms.mapper.CrmBannerMapper;
import com.jss.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author liu
 * @since 2021-08-31
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {
    @Override
    @Cacheable(key="'selectIndexList'",value = "banner") //redis缓存注解
    public List<CrmBanner> selectAllBanner() {
        //根据id降序，显示前两条记录
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 2");
        List<CrmBanner> list = this.baseMapper.selectList(wrapper);
        return list;
    }
}
