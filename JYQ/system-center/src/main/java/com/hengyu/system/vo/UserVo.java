package com.hengyu.system.vo;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserVo {

	
	private Integer id;
	
	@ApiModelProperty("转正日期")
	private Date regularDate;
	
	@ApiModelProperty("状态（1:试用；2:在职；3:离职；4:禁用）")
	private Integer status;
	
	private String userName;
	
	private String wxOpenId;
	
	private String telephone;
}
