package com.hengyu.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hengyu.system.entity.Order;
import com.hengyu.system.vo.OrderVo;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-12-05
 */
public interface OrderDAO extends BaseMapper<Order> {

	Integer queryMaxNum(@Param("companyId")Integer companyId);
	
	Order queryDetaile(Order order);
	
	/**
	 * 查询订单列表
	 * @param order
	 * @return
	 */
	List<OrderVo> queryList(Pagination page,Order order);
}
