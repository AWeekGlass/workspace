package com.hengyu.contract.po;

import static com.hengyu.common.constant.CommonConstants.PAGE_SIZE;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SearchApprovalPo {
	
	/**
	 * 查询类型(1我的申请2待我申请)
	 */
	@NotNull
	@ApiModelProperty(value = "查询类型(1我的申请2待我申请)")
	private Integer type;
	
	/**
	 * 查询状态1通过2驳回3撤销4待审批
	 */
	@ApiModelProperty(value = "查询状态1通过2驳回3撤销4待审批")
	private Integer state;
	
	@ApiModelProperty(hidden = true)
	private Integer userId;
	
	@ApiModelProperty(value = "页码")
	private Integer current = 1;
	
	@ApiModelProperty(value = "每页数量")
	private Integer size = Integer.valueOf(PAGE_SIZE);
	
	

}
