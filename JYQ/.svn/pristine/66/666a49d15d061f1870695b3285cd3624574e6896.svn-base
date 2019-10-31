package com.hengyu.exam.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestPaperSubordinateVo {
	private Integer id;
	/**
	 * 考试人员
	 */
	private String userName;

	/**
	 * 试卷编号
	 */
	private String code;

	/**
	 * 试卷名称
	 */
	private String name;

	/**
	 * 派发人
	 */
	private String distributeName;

	/**
	 * 派发时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 时长
	 */
	private Integer timeLength;

	/**
	 * 阅卷人
	 */
	private String checkName;

	/**
	 * 考试状态
	 */
	private String statusName;

	/**
	 * 考试成绩
	 */
	private Integer score;

	/**
	 * 考试用时
	 */
	private String time;
}
