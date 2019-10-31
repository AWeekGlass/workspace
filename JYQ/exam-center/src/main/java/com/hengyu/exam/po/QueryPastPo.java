package com.hengyu.exam.po;

import static com.hengyu.common.constant.CommonConstants.PAGE_SIZE;

import java.util.List;

import lombok.Data;

@Data
public class QueryPastPo {

	/**
	 * 用户id
	 */
	private Integer userId;
	
	/**
	 * 起始时间
	 */
	private String startTime;
	
	/**
	 * 结束时间
	 */
	private String endTime;
	
	/**
	 * 部门
	 */
	private Integer dptId;
	
	/**
	 * 查询字段
	 */
	private String searchKey;
	
	/**
	 * 页码
	 */
	private Integer current = 1;
	
	/**
	 * 页数
	 */
	private Integer size = Integer.valueOf(PAGE_SIZE);
	
	/**
	 * 试卷id
	 */
	private List<Integer> ids;
	
	/**
	 * 公司id
	 */
	private Integer companyId;
}
