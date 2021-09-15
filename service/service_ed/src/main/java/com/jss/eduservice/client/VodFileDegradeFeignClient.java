package com.jss.eduservice.client;

import com.jss.commonutils.R;
import org.springframework.stereotype.Component;
import java.util.List;

//出错后执行的方法
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R removeAlyiVideo(String id) {
        return R.error().message("删除视频出错");
    }

    @Override
    public R deleteBatch(List<String> videoList) {
        return R.error().message("删除多个视频出错");
    }
}
