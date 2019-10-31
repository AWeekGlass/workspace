package com.hengyu.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.hengyu.system.entity.Feedback;

/**
 * <p>
 * 中介反馈表 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
public interface FeedbackService extends IService<Feedback> {
	
	/**
	 * 查询反馈列表
	 * @param page
	 * @param feedback
	 * @return
	 */
	Page<Feedback> getList(Page<Feedback> page,Feedback feedback);
	
	/**
	 * 查询详情
	 * @param id
	 * @return
	 */
	Feedback queryById(Integer id);
	
	/**
	 * 删除反馈
	 * @param id
	 * @return
	 */
	boolean delete(String id);
}
