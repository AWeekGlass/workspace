package com.hengyu.system.po;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddCompanyPo {
	
	@NotBlank(message="公司名称不能为空")
	@ApiModelProperty(value = "公司名称")
	private String	name;
	
	@NotNull(message="行业id")
	@ApiModelProperty(value = "行业id")
	private Integer industryId;
	
	@NotNull(message="企业代码")
	@ApiModelProperty(value = "企业代码")
	private Integer code;
	
	@NotNull(message="法定代表人")
	@ApiModelProperty(value = "法定代表人")
	private Integer userName;
}
