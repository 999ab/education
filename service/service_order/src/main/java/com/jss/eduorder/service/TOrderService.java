package com.jss.eduorder.service;

import com.jss.eduorder.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author liu
 * @since 2021-09-09
 */
public interface TOrderService extends IService<TOrder> {

    String createOrders(String  courseId, String memberIdByJwtToken);
}
