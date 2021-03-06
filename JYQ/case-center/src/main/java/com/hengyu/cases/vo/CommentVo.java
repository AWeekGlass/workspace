package com.hengyu.cases.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentVo {
	
	private Integer id;

	/**
	 * 评论内容
	 */
	private String content;

	/**
	 * 评论员工ID
	 */
	private Integer adminId;
	
	/**
	 * 评论员工姓名
	 */
	private String userName;
	
	/**
	 * 回复评论员工ID
	 */
	private Integer replyAdminId;

	/**
	 * 回复评论员工姓名
	 */
	private String replyName;

	/**
	 * 父级ID
	 */
	private Integer parentId;

	/**
	 * 顶级ID
	 */
	private Integer topId;
	
	/**
	 * 发表时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	private List<CommentVo> childs;
}
