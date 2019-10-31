package com.hengyu.exam.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(value = { "handler" })
@Getter
@Setter
public class SubjectVo {
	private List<ExamNumVo> examNum;
	private List<String> records;
}
