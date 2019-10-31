package com.hengyu.contract.po;

import com.hengyu.contract.entity.Approval;
import com.hengyu.contract.entity.Buyer;
import com.hengyu.contract.entity.Contract;
import com.hengyu.contract.entity.Seller;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AddcontractPo {

	/**
	 * 买方
	 */
	@ApiModelProperty(value = "买方")
	private Buyer buyer;
	
	/**
	 * 卖方
	 */
	@ApiModelProperty(value = "卖方")
	private Seller seller;
	
	/**
	 * 合同信息
	 */
	@ApiModelProperty(value = "合同信息")
	private Contract contract;
	
	/**
	 * 审批实体对象
	 */
	@ApiModelProperty(value = "审批实体对象(审批的时候传)")
	private Approval approval;
}
