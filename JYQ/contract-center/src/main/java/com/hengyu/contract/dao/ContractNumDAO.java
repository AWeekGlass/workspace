package com.hengyu.contract.dao;

import com.hengyu.contract.entity.ContractNum;
import com.hengyu.contract.vo.SearchVo;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 中介合同编号配置表 Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
public interface ContractNumDAO extends BaseMapper<ContractNum> {

	/**
	 * 根据参数查合同参数配置列表
	 * @param ContractNum
	 * @return
	 */
	List<SearchVo> queryByParams(ContractNum ContractNum);
}
