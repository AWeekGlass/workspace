package com.hengyu.cases.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(value = { "handler" })
@Getter
@Setter
public class QuestionVo {
	private Integer id;

	/**
	 * 问题内容
	 */
	private String content;

	/**
	 * 截止日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date endTime;

	private List<AnswerVo> answerList;
}
