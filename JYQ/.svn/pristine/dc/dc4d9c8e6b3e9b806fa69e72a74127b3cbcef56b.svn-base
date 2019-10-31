package com.hengyu.user.po;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChangePhonePo {

	@ApiModelProperty(hidden=true)
	private Integer userId;
	
	@ApiModelProperty(hidden=true)
	private Integer companyId;

	@NotBlank(message="原手机号不能为空")
	@ApiModelProperty(value = "原手机号")
	private String oldPhone;
	
	@NotBlank(message="新手机号不能为空")
	@ApiModelProperty(value = "新手机号")
	private String newPhone;
	
	@NotBlank(message="验证码不能为空")
	@ApiModelProperty(value = "验证码")
	private String code;
	
	
}
