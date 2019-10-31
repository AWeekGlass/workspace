package com.hengyu.exam.po;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class UpdateStatePo {
	
	@NotNull
	@ApiModelProperty(value = "要恢复的城市id,以逗号隔开")
	private String cityIds;
	
	@NotNull
	@ApiModelProperty(value = "题目id")
	private Integer questionId;
	
	@ApiModelProperty(hidden = true)
	private Integer companyId;
	
}
