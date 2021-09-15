package com.jss.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-order")
public interface OrdersClient {
    @GetMapping("/eduorder/order/isBuyCourse/{courseId}/{id}")
    public Boolean isBuyCourse(@PathVariable("courseId") String courseId, @PathVariable("id") String id);
}