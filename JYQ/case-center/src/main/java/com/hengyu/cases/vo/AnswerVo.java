package com.hengyu.cases.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerVo {
	private Integer id;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 点赞个数
	 */
	private Integer likeCnt;

	/**
	 * 是否热门（1：是；0：否）
	 */
	private Integer isHot;

	/**
	 * 是否点赞
	 */
	private Integer isLike;
}
