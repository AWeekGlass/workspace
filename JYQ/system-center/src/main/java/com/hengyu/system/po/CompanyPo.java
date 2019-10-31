package com.hengyu.system.po;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@ApiModel(value = "新增中介公司")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CompanyPo {
	

	/**
	 * 公司名称
	 */
	@ApiModelProperty(value = "公司名称")
	@NotBlank(message="公司名称不能为空")
	private String name;
	
	/**
	 * 上传营业执照地址
	 */
	private String businessUrl;


	/**
	 * 省
	 */
	@ApiModelProperty(value = "省")
	@NotNull(message="省不能为空")
	private Integer provinceId;

	/**
	 * 市
	 */
	@ApiModelProperty(value = "市")
	private Integer cityId;

	/**
	 * 区
	 */
	@ApiModelProperty(value = "区")
	private Integer areaId;

	/**
	 * 详细地址
	 */
	@ApiModelProperty(value = "详细地址")
	@NotBlank(message="详细地址不能为空")
	private String addressDetail;

	/**
	 * 申请状态：0：注册用户 1：试用用户
	 */
	@ApiModelProperty(value = "0：注册用户 1：试用用户")
	private Integer customerStatus;

	/**
	 * 姓名
	 */
	@ApiModelProperty(value = "姓名")
	@NotBlank(message="姓名不能为空")
	private String userName;
	
	/**
	 * 手机号
	 */
	@ApiModelProperty(value = "手机号")
	@NotBlank(message="手机号不能为空")
	private String phone;
	
	/**
	 * 邀请人手机号
	 */
	@ApiModelProperty(value = "邀请人手机号")
	private String inviterPhone;
	
	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	@NotBlank(message="密码不能为空")
	private String password;

	/**
	 * 确认密码
	 */
	@ApiModelProperty(value = "确认密码")
	@NotBlank(message="确认密码不能为空")
	private String rePassword;
	
	/**
	 * 付费等级
	 */
	@ApiModelProperty(value = "付费等级")
	private Integer level;
}
