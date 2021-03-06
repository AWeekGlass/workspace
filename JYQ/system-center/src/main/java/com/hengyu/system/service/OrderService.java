package com.hengyu.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.system.entity.Order;
import com.hengyu.system.vo.OrderVo;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-12-05
 */
public interface OrderService extends IService<Order> {
	
	/**
	 * 创建订单
	 * @param order
	 * @return
	 */
	RestResponse<Order> createOrder(Order order);
	
	/**
	 * 创建订单
	 * @param order
	 * @return
	 */
	RestResponse<Order> renew(Order order);
	
	/**
	 * 查询订单详情
	 * @param order
	 * @return
	 */
	Order queryDetail(Order order);
	
	/**
	 * 查询列表
	 * @param order
	 * @return
	 */
	Page<OrderVo> queryList(Page<OrderVo> page,Order order);
}
