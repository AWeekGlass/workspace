package com.hengyu.exam.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(value = { "handler" })
@Getter
@Setter
public class TestPaperFullVo {
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
	 * 分数
	 */
	private Integer score;

	/**
	 * 阅卷人(试卷指定阅卷人)
	 */
	private String checkName;
	
	/**
	 * 阅卷人(单张试卷阅卷人)
	 */
	private String checkUserName;
	
	/**
	 * 派发人
	 */
	private Integer distributeId;
	
	
	/**
	 * 派发人
	 */
	private String distributeName;
	
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;
	
	/**
	 * 考试状态
	 */
	private Integer isExam;
	
	/**
	 * 参考率
	 */
	private Integer tested;

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
	 * 及格率(及格线)
	 */
	private Integer passRate;
	
	/**
	 * 及格率(本场考试的及格人数比)
	 */
	private Integer passRateTest;

	private SubjectVo subject;

	private List<ExamNumVo> examNum;
	
	private Integer sum;
}
