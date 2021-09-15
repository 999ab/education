package com.jss.eduservice.client;

import com.jss.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)
public interface VodClient {
    @DeleteMapping("/eduvod/video/removeAlyiVideo/{id}")
    public R removeAlyiVideo(@PathVariable("id") String id);

    @DeleteMapping("/eduvod/video/delete-batch")
    public R deleteBatch(@RequestParam("videoList") List<String> videoList);
}
