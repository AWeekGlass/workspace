package com.hengyu.contract.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.hengyu.contract.entity.ContractNum;
import com.hengyu.contract.vo.SearchVo;

/**
 * <p>
 * 中介合同编号配置表 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
public interface ContractNumService extends IService<ContractNum> {

	/**
	 * 根据公司查询合同编号配置
	 * @param contractNum
	 * @return
	 */
	ContractNum queryByCompany(Integer companyId);
	
	/**
	 * 根据id删除合同编号配置
	 * @param id
	 * @return
	 */
	boolean deletebyId(String id);
	
	/**
	 * 根据id更新合同编号配置
	 * @param contractNum
	 * @return
	 */
	boolean update(ContractNum contractNum);
	
	/**
	 * 新增合同编号配置
	 * @param contractNum
	 * @return
	 */
	boolean save(ContractNum contractNum);
	
	/**
	 * 根据id查询合同编号配置
	 * @param id
	 * @return
	 */
	ContractNum queryById(Integer id);
	
	/**
	 * 根据参数查合同参数配置列表(包含公司信息)
	 * @param contractNum
	 * @return
	 */
	List<SearchVo> queryByParams(ContractNum contractNum);
	
	/**
	 * 查询合同配置参数列表
	 * @param contractNum
	 * @return
	 */
	List<ContractNum> queryList(ContractNum contractNum);
}
