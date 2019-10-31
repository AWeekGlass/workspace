package com.hengyu.exam.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(value = { "handler" })
public class TestPaperDetailVo {

	private Integer id;

	/**
	 * 试卷名称
	 */
	private String name;

	/**
	 * 试卷编号
	 */
	private String code;

	/**
	 * 题库城市ID
	 */
	private String cityId;

	/**
	 * 题库城市
	 */
	private String cityName;

	/**
	 * 考试城市ID
	 */
	private String examCityId;

	/**
	 * 考试城市
	 */
	private String examCityName;

	/**
	 * 时长
	 */
	private Integer timeLength;

	/**
	 * 阅卷人ID
	 */
	private Integer checkId;

	/**
	 * 阅卷人ID
	 */
	private Integer score;

	/**
	 * 阅卷人
	 */
	private String checkName;

	/**
	 * 是否关联已有培训
	 */
	private Integer isRelation;

	/**
	 * 考试人员
	 */
	private List<AdminVo> userIds;

	/**
	 * 题目
	 */
	private List<QuestionVo> questions;

	/**
	 * 类型题目
	 */
	private List<QuestionsVo> questionTypes;

	/**
	 * 派发类型（1:随机派发，2:指定派发）
	 */
	private Integer type;

	/**
	 * 及格率
	 */
	private Integer passRate;

	private SubjectVo subject;

	private List<ExamNumVo> examNum;

}
