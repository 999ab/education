package com.jss.eduorder.client;

import com.jss.commonutils.ordervo.UcenterMemberOreder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    @PostMapping("/educenter/member/getUserInfoOrder/{id}")
    public UcenterMemberOreder getUserInfoOrder(@PathVariable("id") String id);
}
