package com.hengyu.contract.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hengyu.contract.entity.Approval;
import com.hengyu.contract.po.SearchApprovalPo;

/**
 * <p>
 * 审批记录表 Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-09-19
 */
public interface ApprovalDAO extends BaseMapper<Approval> {

	/**
	 * 查询上级id
	 * @param id
	 * @return
	 */
	Integer getParent(@Param("id") Integer id);
	
	/**
	 * 查询列表
	 * @param type
	 * @param state
	 * @return
	 */
	List<Approval> getRecord(Pagination page,SearchApprovalPo po);
	
	/**
	 * 查询用户手机号
	 * @param userId
	 * @return
	 */
	String getPhone(@Param("userId")Integer userId);
	
	/**
	 * 根据id查询详情
	 * @param id
	 * @return
	 */
	Approval queryById(@Param("id")Integer id);
}
