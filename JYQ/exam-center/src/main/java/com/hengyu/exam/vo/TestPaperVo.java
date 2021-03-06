package com.hengyu.exam.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestPaperVo {
	private Integer id;
	/**
	 * 试卷编号
	 */
	private String code;

	/**
	 * 试卷名称
	 */
	private String name;

	/**
	 * 派发类型（1:随机派发，2:指定派发）
	 */
	private Integer type;

	/**
	 * 派发人ID
	 */
	private String distributeName;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	/**
	 * 阅卷
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date checkTime;

	/**
	 * 阅卷人
	 */
	private String checkName;

	/**
	 * 是否短信通知（1：已发送；0:未发送）
	 */
	private Integer isSmsNotice;

	/**
	 * 批阅人id
	 */
	private Integer checkId;

	/**
	 * 派发类型
	 */
	private String typeName;

	/**
	 * 派发人id
	 */
	private Integer distributeId;

	/**
	 * 分数
	 */
	private Integer score;

	/**
	 * 考试人ID
	 */
	private String userIds;
	
	/**
	 * 考试时长
	 */
	private Integer timeLength;
	
	/**
	 * 考试时长(时分)
	 */
	private String timeLengthInfo;
	
	/**
	 * 阅卷状态
	 */
	private String stateName;
	
	/**
	 * 批阅状态码
	 */
	private Integer state;
	
	/**
	 * 考试状态
	 */
	private String checkState;
	
	/**
	 * 及格率
	 */
	private Integer passRate; 
	
	/**
	 * 参考率
	 */
	private Integer testedRate;
	
	/**
	 * 考试用时
	 */
	private String time;
	
	/**
	 * 及格分数
	 */
	private Integer passScore;
	
	/**
	 * 公司名称
	 */
	private String companyName;
	
	/**
	 * 数据类型 1 考试  2审批
	 */
	private Integer testType;
}
