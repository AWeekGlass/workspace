package com.hengyu.contract.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 根据参数查询合同参数配置对象
 * @author liuhainan
 *
 */
@Data
public class SearchVo {
	
	 /**
     * 编号
     */
    private Integer id;
    /**
     * 公司编号（引用公司表）
     */
    private String companyId;
    /**
     * 租房设置的编号
     */
    private String rentingNum;
    /**
     * 新房的编号设置
     */
    /**
     * 存量房的编号设置
     */
    private String stockRoomNum;
    /**
     * “租房设置”自增长的起点
     */
    private String rentingSta;
    /**
     * “新房设置”自增长的起点
     */
    private String newHouseSta;
    /**
     * “存量房设置”设置自增长的起点
     */
    private String stockRoomSta;
    
	/**
	 * 公司名称
	 */
	private String companyName;

	/**
	 * 公司简称
	 */
	private String companyAbb;

	/**
	 * 省
	 */
	private String province;

	/**
	 * 市
	 */
	private String city;

	/**
	 * 区
	 */
	private String area;

	/**
	 * 详细地址
	 */
	private String addressDetail;

	/**
	 * 上传营业执照地址
	 */
	private String businessUrl;

	/**
	 * 公司logo地址
	 */
	private String logoUrl;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createTime;

	/**
	 * 到期时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date endTime;

	/**
	 * 是否删除 0：未删除 1：已经删除
	 */
	private Integer delFlag;

	/**
	 * 支付记录引用外键
	 */
	private Integer payResultId;

	private Integer questionShare;

	/**
	 * 审核状态 1-待审核；2-已审核；3-已驳回
	 */
	private Integer status;

	/**
	 * 审核员工（引用管理后台员工表）
	 */
	private Integer staffId;

	/**
	 * 申请状态：0：注册用户 1：试用用户
	 */
	private String customerStatus;

	/**
	 * 驳回理由
	 */
	private String remark;

	/**
	 * 编号生成启用状态：0.未启用状态 1.启用状态
	 */
	private Integer numberType;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date updateTime;
	
}
