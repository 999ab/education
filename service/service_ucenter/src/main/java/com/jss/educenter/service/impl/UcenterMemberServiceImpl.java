package com.jss.educenter.service.impl;

import com.alibaba.nacos.common.util.Md5Utils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jss.commonutils.JwtUtils;
import com.jss.commonutils.MD5Utils;
import com.jss.educenter.entity.UcenterMember;
import com.jss.educenter.entity.vo.RegisterVo;
import com.jss.educenter.mapper.UcenterMemberMapper;
import com.jss.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jss.servicebase.exceptionhandler.CollegeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author liu
 * @since 2021-09-03
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate; //验证码

    @Override
    public String login(UcenterMember member) {
        String mobile = member.getMobile();
        String password = member.getPassword();
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new CollegeException(20001,"登录失败1");
        }

        //判断手机号是否正确
        QueryWrapper<UcenterMember>  wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        if(mobileMember == null){
            throw new CollegeException(20001,"登录失败2");
        }
        //判断密码是否正确,在数据库中不存储明文密码
        if(!MD5Utils.encrypt(password).equals(mobileMember.getPassword())){
            throw new CollegeException(20001,"登录失败3");
        }
        if(mobileMember.getIsDeleted()){
            throw new CollegeException(20001,"登录失败4");
        }
        //登录成功后,生成token
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());

        return jwtToken;
    }

    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        if(StringUtils.isEmpty(code)||StringUtils.isEmpty(mobile)||StringUtils.isEmpty(nickname)||StringUtils.isEmpty(password)){
            throw new CollegeException(20001,"注册失败");
        }

        //获取redis中的验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)){
            throw new CollegeException(20001,"注册失败");
        }
        //判断手机号是否一样
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer integer = baseMapper.selectCount(wrapper);
        if(integer > 0){
            throw new CollegeException(20001,"注册失败");
        }

        //添加到数据库
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setPassword(MD5Utils.encrypt(password));
        member.setNickname(nickname);
        member.setIsDeleted(false);
        baseMapper.insert(member);
    }

    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
