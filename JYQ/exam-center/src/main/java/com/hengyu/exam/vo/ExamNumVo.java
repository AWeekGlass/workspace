package com.hengyu.exam.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExamNumVo {
	private Integer category;
	private List<NumVo> num;
}
