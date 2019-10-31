package com.hengyu.system.po;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ApplyPo {

	@NotNull(message="审批记录id不能为空")
	private Integer id;
	
	/**
	 *公司id 
	 */
	@NotNull(message="公司id不能为空")
	private Integer companyId;
	
	/**
	 * 审核状态
	 */
	@NotNull(message="审核状态不能为空")
	private Integer state;
	
	/**
	 * 驳回理由
	 */
	private String reason;

	/**
	 * 试用时间
	 */
	private Integer day;
}
