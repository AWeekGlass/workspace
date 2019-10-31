package com.hengyu.contract.service;

import java.io.IOException;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.hengyu.contract.entity.Contract;
import com.hengyu.contract.po.AddcontractPo;
import com.hengyu.contract.po.SearchPo;

/**
 * <p>
 * 中介合同表 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-08-27
 */
public interface ContractService extends IService<Contract> {

	/**
	 * 添加合同
	 * @return
	 */
	boolean save(AddcontractPo addcontractPo);

	/**
	 * 根据条件查询最新合同
	 * @param contract
	 * @return
	 */
	Contract getContractByMax(Contract contract);
	
	/**
	 * 修改合同
	 * @param contract
	 * @return
	 */
	boolean updateContract(Contract contract);
	
	/**
	 * 查询合同列表
	 * @param searchPo
	 * @return
	 */
	Page<Contract> selectContractList(Page<Contract> page,SearchPo searchPo) throws IOException;
	
	/**
	 * 根据id查询合同
	 * @param id
	 * @return
	 */
	Contract queryById(Integer id) throws IOException;
	
	/**
	 * 根据id删除合同
	 * @param id
	 * @return
	 */
	boolean deleteByID(String id);
}
