package com.hengyu.training.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hengyu.training.entity.TrainingExperience;
import com.hengyu.training.entity.TrainingLike;
import com.hengyu.training.vo.StoreVo;
import com.hengyu.training.vo.UserVo;

/**
 * <p>
 * 培训心得
 Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-12-13
 */
public interface TrainingExperienceDAO extends BaseMapper<TrainingExperience> {
	
	/**
	 * 根据类型查看列表
	 * 
	 * @param companyId
	 * @param categoryId
	 * @return
	 */
	List<TrainingExperience> queryAllByCategoryId(Pagination page,TrainingExperience trainingExperience);

	List<TrainingExperience> queryDraft(@Param("companyId") Integer companyId);
	
	/**
	 * 根据ID查询培训心得信息
	 * 
	 * @param caseId
	 * @return
	 */
	TrainingExperience detail(@Param("id") Integer id);
	
	/**
	 * 获取店铺名称
	 * @param id
	 * @return
	 */
	StoreVo getStoreName(@Param("id")Integer id);
	
	/**
	 * 获取点赞人
	 * @param id
	 * @return
	 */
	List<TrainingLike> getLike(@Param("id")Integer id);
	
	List<UserVo> queryUsers(@Param("key")String key,@Param("companyId")Integer companyId);
	
	UserVo getUserName(@Param("id")Integer id);
}
