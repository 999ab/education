package com.jss.eduorder.service.impl;

import com.jss.commonutils.ordervo.CourseWebOrder;
import com.jss.commonutils.ordervo.UcenterMemberOreder;
import com.jss.eduorder.client.EduClient;
import com.jss.eduorder.client.UcenterClient;
import com.jss.eduorder.entity.TOrder;
import com.jss.eduorder.mapper.TOrderMapper;
import com.jss.eduorder.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jss.eduorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author liu
 * @since 2021-09-09
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {
    @Autowired
    private UcenterClient ucenterClient;

    @Autowired
    private EduClient eduClient;
    @Override
    public String createOrders(String courseId, String memberId) {
        //通过远程调用获取用户信息，通过用户id
        UcenterMemberOreder userInfoOrder = ucenterClient.getUserInfoOrder(memberId);
        //用过远程调用获取课程信息，通过课程id
        CourseWebOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);
        //创建order对象
        TOrder order = new TOrder();
        order.setOrderNo(OrderNoUtil.getOrderNo());//订单号
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);
        order.setPayType(1);
        this.baseMapper.insert(order);
        return order.getOrderNo();
    }
}
