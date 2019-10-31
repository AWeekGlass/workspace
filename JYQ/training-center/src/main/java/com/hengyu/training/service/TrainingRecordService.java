package com.hengyu.training.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.hengyu.training.entity.TrainingRecord;
import com.hengyu.training.vo.QueryTRecordPo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-09-03
 */
public interface TrainingRecordService extends IService<TrainingRecord> {

	/**
	 * 新增培训关系
	 * 
	 * @param trainingRecord
	 * @return
	 */
	boolean save(TrainingRecord trainingRecord);

	/**
	 * 更新培训关系
	 * @param trainingRecord
	 * @return
	 */
	boolean update(TrainingRecord trainingRecord);
	
	/**
	 * 删除培训关系
	 * @param id
	 * @return
	 */
	boolean delete(String id);
	
	/**
	 * 查询详情
	 * @param id
	 * @return
	 */
	TrainingRecord queryById(Integer id);
	
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	Page<TrainingRecord> queryList(Page<TrainingRecord> page,QueryTRecordPo po);
	
	/**
	 * 根据条件查询所有
	 * @param trainingRecord
	 * @return
	 */
	List<TrainingRecord> queryAll(TrainingRecord trainingRecord);
}
