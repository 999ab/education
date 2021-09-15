package com.jss.educenter.controller;

import com.google.gson.Gson;
import com.jss.commonutils.JwtUtils;
import com.jss.educenter.entity.UcenterMember;
import com.jss.educenter.service.UcenterMemberService;
import com.jss.educenter.utils.ConstantWxUtils;
import com.jss.educenter.utils.HttpClientUtils;
import com.jss.servicebase.exceptionhandler.CollegeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class WxApiController {
    @Autowired
    private UcenterMemberService service;
    @GetMapping("callback")
    public String callback(String code,String state){
        //拼接地址
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
        "?appid=%s" +
        "&secret=%s" +
        "&code=%s" +
        "&grant_type=authorization_code";
        String accessTokenUrl = String.format(
                baseAccessTokenUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                ConstantWxUtils.WX_OPEN_APP_SECRET,
                code
        );
        try{
            //拿着code去获取acess_token && openid
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);  //字符串
            //System.out.println("======="+accessTokenInfo);

            //将accessTokenInfo转换成map,根据map的key获得值
            Gson gson = new Gson();
            HashMap mapToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String  accessToken = (String)mapToken.get("access_token");
            String openid = (String)mapToken.get("openid");


            //判断数据库中是否存在相同的微信
            UcenterMember member = service.getOpenIdMember(openid);
            if(member == null){
                //根据access_token && openid 访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String urlInfoUrl = String.format(
                        baseUserInfoUrl,
                        accessToken,
                        openid);

                //发送请求
                String userInfo = HttpClientUtils.get(urlInfoUrl);
                //System.out.println("********"+userInfo);

                //获取返回后的扫码人信息
                HashMap userMap = gson.fromJson(userInfo, HashMap.class);
                String  nickname = (String) userMap.get("nickname");
                String  headimgurl = (String) userMap.get("headimgurl");

                member = new UcenterMember();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                service.save(member);
            }

            //生成token
            String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());

            return "redirect:http://localhost:3000?token="+token;
        }catch (Exception e){
            e.printStackTrace();
            throw new CollegeException(20001,"微信登录失败");
        }
    }

    @GetMapping("login")
    public String getWxCode(){
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
        "?appid=%s" +
        "&redirect_uri=%s" +
        "&response_type=code" +
        "&scope=snsapi_login" +
        "&state=%s" +
        "#wechat_redirect";

        //对redirect_url进行编码
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try{
            redirectUrl = URLEncoder.encode(redirectUrl,"utf-8");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("编码错误");
        }
        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "atguigu"
        );

        return "redirect:"+url;
    }
}
