package com.hengyu.contract.dao;

import com.hengyu.contract.entity.ContractNumber;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 中介合同编号表 Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
public interface ContractNumberDAO extends BaseMapper<ContractNumber> {

	/**
	 * 根据条件查询最新信息
	 * @param contractNumber
	 * @return
	 */
	ContractNumber getHtNumberByMax(ContractNumber contractNumber);
	
	/**
	 * 修改合同编号使用状态
	 * @param contractNumber
	 * @return
	 */
	Integer updateStatus(ContractNumber contractNumber);
	
	/**
	 * 根据公司id查询公司的合同编号是否启用
	 * @param id
	 * @return
	 */
	Integer getHtNumberState(@Param("id")Integer id);
}
