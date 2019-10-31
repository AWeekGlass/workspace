package com.hengyu.contract.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.hengyu.contract.entity.ContractNumber;

/**
 * <p>
 * 中介合同编号表 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
public interface ContractNumberService extends IService<ContractNumber> {

	/**
	 * 新增合同编号
	 * @param contractNumber
	 * @return
	 */
	boolean save(ContractNumber contractNumber);
	
	/**
	 * 删除合同编号
	 * @param id
	 * @return
	 */
	boolean delete(String id);
	
	/**
	 * 更新合同编号
	 * @param contractNumber
	 * @return
	 */
	boolean update(ContractNumber contractNumber);
	
	/**
	 * 根据id查询合同编号
	 * @param id
	 * @return
	 */
	ContractNumber queryById(String id);
	
	/**
	 * 查询合同编号列表
	 * @return
	 */
	List<ContractNumber> queryList(ContractNumber contractNumber);
	
	/**
	 * 根据条件查询最新信息
	 * @param contractNumber
	 * @return
	 */
	String getHtNumberByMax(ContractNumber contractNumber);
	
	/**
	 * 修改合同编号使用状态
	 * @param contractNumber
	 * @return
	 */
	boolean updateStatus(ContractNumber contractNumber);
}
