package com.hengyu.system.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreVo extends StoreShortVo{	
	private String parentId;
	
	/**
	 * 1：部门；2:区域；3:门店；4:小组
	 */
	private Integer type;
	
	/**
	 * 部门负责人ID
	 */
	private Integer leadingId;
	
	/**
	 * 部门负责人
	 */
	private String userName;

	/**
	 * 职能介绍
	 */
	private String introduction;
	/**
	 * 联系电话
	 */
	private String telephone;

	/**
	 * 签租日期
	 */
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date signDate;

	/**
	 * 到期日期
	 */
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date expireDate;

	/**
	 * 机构地址
	 */
	private String organizationAddress;
	
	/**
	 * 租金
	 */
	private Double rent;

	/**
	 * 租金类型（1:年；2:月）
	 */
	private Integer rentType;

	/**
	 * 付款方式
	 */
	private Integer payTypeId;

	/**
	 * 备注
	 */
	private String remark;
}
