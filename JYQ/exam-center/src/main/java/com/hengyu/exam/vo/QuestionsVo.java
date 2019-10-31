package com.hengyu.exam.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionsVo {
	private Integer type;
	private List<QuestionVo> questions;
}
