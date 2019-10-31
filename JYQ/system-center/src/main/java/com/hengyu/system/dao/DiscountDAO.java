package com.hengyu.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hengyu.system.entity.Discount;
import com.hengyu.system.vo.UserVo;

/**
 * <p>
 * 折扣优惠表 Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-12-07
 */
public interface DiscountDAO extends BaseMapper<Discount> {

	List<Discount> queryList(Pagination page, Discount discount);
	
	/**
	 * 查询用户转正日期
	 * @param userId
	 * @return
	 */
	UserVo queryDate(@Param("userId")Integer userId);
}
