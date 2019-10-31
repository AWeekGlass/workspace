package com.hengyu.system.service.impl;

import java.util.Calendar;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.system.dao.UserDiscountDAO;
import com.hengyu.system.entity.Discount;
import com.hengyu.system.entity.UserDiscount;
import com.hengyu.system.service.DiscountService;
import com.hengyu.system.service.UserDiscountService;

/**
 * <p>
 * 用户折扣券表 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-12-11
 */
@Service
public class UserDiscountServiceImpl extends ServiceImpl<UserDiscountDAO, UserDiscount> implements UserDiscountService {

	@Autowired
	private UserDiscountDAO discountDAO;
	
	@Autowired
	private DiscountService discountService;
	
	@Override
	public Page<UserDiscount> queryList(Page<UserDiscount> page, UserDiscount userDiscount) {
		return page.setRecords(discountDAO.queryList(page, userDiscount));
	}

	@Override
	public boolean add(UserDiscount userDiscount) {
		Discount discount = discountService.selectById(userDiscount.getDiscountId());
		if(Objects.nonNull(discount)){
			// 获取N天之后的日期
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, discount.getValidity());
			userDiscount.setEndTime(calendar.getTime());
			return insert(userDiscount);
		}
		return false;
		
	}

}
