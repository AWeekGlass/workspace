package com.hengyu.training.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hengyu.training.entity.TrainingRecord;
import com.hengyu.training.vo.QueryTRecordPo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-09-03
 */
public interface TrainingRecordDAO extends BaseMapper<TrainingRecord> {

	/**
	 * 分页查询列表
	 * @param page
	 * @param po
	 * @return
	 */
	List<TrainingRecord> queryList(Pagination page,QueryTRecordPo queryTRecordPo);
	
	/**
	 * 分页查询列表
	 * @param po
	 * @return
	 */
	List<TrainingRecord> queryList(TrainingRecord trainingRecord);
}
