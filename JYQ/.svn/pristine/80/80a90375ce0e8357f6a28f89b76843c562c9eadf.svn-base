package com.hengyu.user.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RewardRecordVo {
	private Integer id;

	/**
	 * 奖惩类型（1：奖励，2：惩罚）
	 */
	private Integer type;

	/**
	 * 员工姓名
	 */
	private String adminName;

	/**
	 * 方式（1：文本，2：金额）
	 */
	private Integer mode;

	/**
	 * 金额
	 */
	private BigDecimal amount;

	/**
	 * 文字
	 */
	private String content;

	/**
	 * 发生时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date eventTime;

	/**
	 * 执行时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date rewardTime;

	/**
	 * 说明
	 */
	private String remark;

	/**
	 * 操作人
	 */
	private String createName;
}
