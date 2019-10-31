package com.hengyu.system.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.system.dao.DiscountDAO;
import com.hengyu.system.entity.Discount;
import com.hengyu.system.service.DiscountService;
import com.hengyu.system.vo.UserVo;

/**
 * <p>
 * 折扣优惠表 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-12-07
 */
@Service
public class DiscountServiceImpl extends ServiceImpl<DiscountDAO, Discount> implements DiscountService {

	@Autowired
	private DiscountDAO discountDAO;

	@Override
	public Page<Discount> queryList(Page<Discount> page, Discount discount, Integer userId) {
		// 查询用户转正信息
		UserVo userVo = discountDAO.queryDate(userId);
		List<Discount> list = discountDAO.queryList(page, discount);
		for (Discount discount_ : list) {
			//转正
			if (userVo.getStatus() == 2) {
				// 如果该用户没有转正日期且已经转正，则无优惠券可用
				if (Objects.isNull(userVo.getRegularDate())) {
					discount_.setState(0);
				} else {
					Integer date = getDay(new Date(),userVo.getRegularDate());
					if(date<=5 && discount_.getId() ==2){//设置转正后五天的优惠券
						discount_.setState(1);
					}else if (date>5 && date<=10 && discount_.getId() ==3) {//设置转正后10天的优惠券
						discount_.setState(1);
					}else if (date>10 && date <=15 && discount_.getId() == 4) {//设置转正后15天的优惠券
						discount_.setState(1);
					}else {
						discount_.setState(0);
					}
				}

			} else if (userVo.getStatus() == 1 && discount_.getId() ==1) {//未转正且id为1 的优惠券
				discount_.setState(1);
			} else {
				discount_.setState(0);
			}

		}
		return page.setRecords(list);
	}

	/**
	 * 获取两个日期之间相差天数
	 * @param time
	 * @param time_
	 * @return
	 */
	private Integer getDay(Date time, Date time_) {
		return (int) ((time.getTime() - time_.getTime()) / (1000 * 3600 * 24));
	}
}
