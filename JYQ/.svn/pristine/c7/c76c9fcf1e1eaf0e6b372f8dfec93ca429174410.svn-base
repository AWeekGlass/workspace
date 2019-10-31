package com.hengyu.training.service;

import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.training.entity.Training;
import com.hengyu.training.vo.QueryTrainPo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author allnas
 * @since 2018-09-03
 */
public interface TrainingService extends IService<Training> {

	/**
	 * 新增培训
	 * @param training
	 * @return
	 */
	boolean save(Training training);
	
	/**
	 * 修改培训
	 * @param training
	 * @return
	 */
	boolean update(Training training);
	
	/**
	 * 修改培训
	 * @param id
	 * @return
	 */
	boolean delete(String id);
	
	/**
	 * 分页查询培训过列表
	 * @param page
	 * @param queryTrainPo
	 * @return
	 */
	Page<Training> queryList(Page<Training> page,QueryTrainPo queryTrainPo);
	
	/**
	 * 根据id查询详情
	 * @param id
	 * @return
	 */
	Training queryById(Integer id);
	
	/**
	 * 签到
	 * @return
	 */
	Integer signIn(Integer trainingId,IJWTInfo info );
}
