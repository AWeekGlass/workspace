package com.hengyu.training.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hengyu.training.entity.Training;
import com.hengyu.training.vo.QueryTrainPo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-09-03
 */
public interface TrainingDAO extends BaseMapper<Training> {
	
	/**
	 * 分页查询培训列表
	 * @param page
	 * @param queryTrainPo
	 * @return
	 */
	List<Training> queryList(Pagination page,QueryTrainPo queryTrainPo);

}
