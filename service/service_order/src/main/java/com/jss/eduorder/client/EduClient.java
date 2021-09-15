package com.jss.eduorder.client;

import com.jss.commonutils.ordervo.CourseWebOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-ed")
public interface EduClient {
    @PostMapping("/eduservice/coursefront/getCourseInfoOrder/{id}")
    public CourseWebOrder getCourseInfoOrder(@PathVariable("id") String id);
}
