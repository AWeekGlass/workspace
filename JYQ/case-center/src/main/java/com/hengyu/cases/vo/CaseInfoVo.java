package com.hengyu.cases.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

import lombok.Setter;

import lombok.ToString;

@Getter
@Setter
@ToString
public class CaseInfoVo {
	private Integer id;
	/**
	 * 案例名称
	 */
	private String title;

	/**
	 * 分类ID
	 */
	private Integer categoryId;

	/**
	 * 分类名称
	 */
	private String categoryName;

	/**
	 * 类型（1：原创；2：转发）
	 */
	private Integer type;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 填写时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	/**
	 * 置顶操作人
	 */
	private String topName;

	/**
	 * 置顶时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date topTime;

	/**
	 * 填写人
	 */
	private String adminName;

	/**
	 * 所属部门
	 */
	private String storeName;

	/**
	 * 职务
	 */
	private String roleName;

	/**
	 * 推荐人
	 */
	private String refName;

	/**
	 * 点赞个数
	 */
	private Integer likeCnt;

	/**
	 * 评论个数
	 */
	private Integer commentCnt;

	/**
	 * 是否点赞
	 */
	private Integer isLike;
	
	/**
	 * 头像
	 */
	private String headPortrait;
}
