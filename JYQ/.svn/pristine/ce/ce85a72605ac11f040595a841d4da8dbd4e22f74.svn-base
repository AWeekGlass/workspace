package com.hengyu.contract.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hengyu.contract.entity.Contract;
import com.hengyu.contract.po.SearchPo;

/**
 * <p>
 * 中介合同表 Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-08-27
 */
public interface ContractDAO extends BaseMapper<Contract> {
	
	/**
	 * 根据条件查询最新合同
	 * @param contract
	 * @return
	 */
	Contract getContractByMax(Contract contract);
	
	/**
	 * 查询合同列表
	 * @return
	 */
	List<Contract> selectContractList(Pagination pagination,SearchPo searchPo);
	
	/**
	 * 根据当前登录用户查询下属列表
	 * @param userId
	 * @return
	 */
	List<Integer> selectBranch(@Param("userId") Integer userId);
	
	/**
	 * 根据id查询详情
	 * @param id
	 * @return
	 */
	Contract selectByID(@Param("id") Integer id);
}
