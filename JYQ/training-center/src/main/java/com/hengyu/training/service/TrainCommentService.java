package com.hengyu.training.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.hengyu.training.entity.TrainComment;

/**
 * <p>
 * 培训心得回复表 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-12-14
 */
public interface TrainCommentService extends IService<TrainComment> {
	
	/**
	 * 查询
	 * @param companyId
	 * @param id
	 * @return
	 */
	List<TrainComment> getCommentTree(Integer companyId,Integer id);
	
	void sendWXMessage(TrainComment t);

}
