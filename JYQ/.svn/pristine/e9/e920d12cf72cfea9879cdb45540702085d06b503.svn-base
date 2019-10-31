package com.hengyu.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hengyu.system.entity.Feedback;

/**
 * <p>
 * 中介反馈表 Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
public interface FeedbackDAO extends BaseMapper<Feedback> {
	
	/**
	 * 查询反馈列表
	 * @param page
	 * @param feedback
	 * @return
	 */
	List<Feedback> getList(Pagination page,Feedback feedback);
	
	/**
	 * 查询详情
	 * @param id
	 * @return
	 */
	Feedback queryById(@Param("id")Integer id);
}
