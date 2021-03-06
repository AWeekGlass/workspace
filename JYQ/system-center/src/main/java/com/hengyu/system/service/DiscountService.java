package com.hengyu.system.service;

import com.hengyu.system.entity.Discount;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 折扣优惠表 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-12-07
 */
public interface DiscountService extends IService<Discount> {

	Page<Discount> queryList(Page<Discount> page,Discount discount,Integer userId);
}
