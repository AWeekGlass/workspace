package com.hengyu.contract.dao;

import com.hengyu.contract.entity.ApprovalRecord;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 审批记录表 Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-09-21
 */
public interface ApprovalRecordDAO extends BaseMapper<ApprovalRecord> {
	
	/**
	 * 查询列表
	 * @param approvalId
	 * @return
	 */
	List<ApprovalRecord> getList(@Param("approvalId")Integer approvalId);

}
