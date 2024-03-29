package com.hengyu.training.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.hengyu.training.entity.TrainingExperience;
import com.hengyu.training.entity.TrainingLike;

/**
 * <p>
 * 培训心得
 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-12-13
 */
public interface TrainingExperienceService extends IService<TrainingExperience> {
	
	/**
	 * 根据ID查询案例信息
	 * 
	 * @param caseId
	 * @return
	 */
	TrainingExperience detail(Integer id,Integer userId);

	/**
	 * 根据类型查询列表
	 * 
	 * @param companyId
	 * @param categoryId
	 * @return
	 */
	Page<TrainingExperience> queryAllByCategoryId(Page<TrainingExperience> page,TrainingExperience trainingExperience);
	
	List<TrainingExperience> queryDraft(Integer companyId);
	
	boolean delete(String id);
	
	List<TrainingLike> getLike(Integer id);
	
	void sendWXMessage(TrainingExperience t);
}
