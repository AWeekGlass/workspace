package com.hengyu.cases.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

import lombok.ToString;

import lombok.Setter;

@Getter
@Setter
@ToString
public class LikeInfoVo {

	/**
	 * 点赞员工姓名
	 */
	private String username;

	/**
	 * 点赞时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createtime;

}
