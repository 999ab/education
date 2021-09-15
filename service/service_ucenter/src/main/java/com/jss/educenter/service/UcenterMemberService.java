package com.jss.educenter.service;

import com.jss.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jss.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author liu
 * @since 2021-09-03
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    void register(RegisterVo registerVo);

    UcenterMember getOpenIdMember(String openid);

    Integer countRegisterDay(String day);
}
