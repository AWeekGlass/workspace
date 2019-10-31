package com.hengyu.contract.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.contract.dao.ContractNumDAO;
import com.hengyu.contract.entity.ContractNum;
import com.hengyu.contract.service.ContractNumService;
import com.hengyu.contract.vo.SearchVo;

/**
 * <p>
 * 中介合同编号配置表 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
@Service
public class ContractNumServiceImpl extends ServiceImpl<ContractNumDAO, ContractNum> implements ContractNumService {

	@Autowired
	private ContractNumDAO contractNumDAO;
	
	/**
	 * 根据公司查询合同编号配置
	 */
	@Override
	public ContractNum queryByCompany(Integer companyId) {
		return selectOne(new EntityWrapper<ContractNum>().eq("company_id", companyId));
	}

	/**
	 * 根据id删除合同编号配置
	 */
	@Override
	public boolean deletebyId(String id) {
		if(!ObjectUtils.isEmpty(id) && !id.isEmpty()){
			List<String> ids = Arrays.asList(id.split(","));
			if(ids!=null && !ids.isEmpty()){
				return deleteBatchIds(ids);
			}
		}
		return false;
	}

	/**
	 * 根据id更新合同编号配置
	 */
	@Override
	public boolean update(ContractNum contractNum) {
		return insertOrUpdate(contractNum);
	}

	/**
	 * 新增合同编号配置
	 */
	@Override
	public boolean save(ContractNum contractNum) {
		return insert(contractNum);
	}

	/**
	 * 根据id查询合同编号配置
	 */
	@Override
	public ContractNum queryById(Integer id) {
		return selectById(id);
	}

	/**
	 * 根据参数查合同参数配置列表
	 */
	@Override
	public List<SearchVo> queryByParams(ContractNum contractNum) {
		List<SearchVo> list = contractNumDAO.queryByParams(contractNum);
		return list;
	}

	/**
	 * 查询合同配置参数列表
	 */
	@Override
	public List<ContractNum> queryList(ContractNum contractNum) {
		return selectList(new EntityWrapper<ContractNum>(contractNum));
	}

}
