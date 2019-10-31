package com.hengyu.exam.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hengyu.exam.entity.QuestionCity;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(value = { "handler" })
@Getter
@Setter
public class QuestionVo {
	/**
	 * 编号
	 */
	private Integer id;

	/**
	 * 题面
	 */
	private String question;

	/**
	 * 类型
	 */
	private Integer type;

	/**
	 * 题面分类
	 */
	private Integer category;
	
    /**
     * 题目编号
     */
    private String code;

	/**
	 * 所属城市
	 */
	private String cityName;

	/**
	 * 上传时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	/**
	 * 出题人
	 */
	private String adminName;

	/**
	 * 审核人
	 */
	private String approveName;

	/**
	 * 答案
	 */
	private String result;

	/**
	 * 审核状态
	 */
	private Integer status;
	
	/**
	 * 审核状态
	 */
	private String statuName;
	
	/**
	 * 题面分类
	 */
	private String categoryName;
	

	/**
	 * 类型名称
	 */
	private String typeName;
	
	/**
	 * 选项
	 */
    private String options;
    
    /**
     * 审核意见
     */
    private String remark;
    
    /**
     * 备注
     */
    private String mark;
    
    /**
     * 分数
     */
    private Integer record;
	
	/**
	 * 城市列表
	 */
	private List<QuestionCity> cityList;
	
	/**
	 * 题目所属城市
	 */
	private String cityInfo;
	
	/**
	 * 是否正确
	 */
	private Integer right;
	
	/**
	 * 用户答案
	 */
	private String userAnswer;
	
	/**
	 * 简答题分数
	 */
	private Integer score;
}
