package com.hengyu.contract.po;

import static com.hengyu.common.constant.CommonConstants.PAGE_SIZE;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SearchPo {
	
	/**
	 * 页码
	 */
	@ApiModelProperty(value = "页码")
	private Integer current = 1;

	/**
	 * 每页数量
	 */
	@ApiModelProperty(value = "每页数量")
	private Integer size = Integer.valueOf(PAGE_SIZE);
    
    /**
     * 签约备案时间：精确到时分秒(起始时间)
     */
	@ApiModelProperty(value = "签约备案时间：精确到时分秒(起始时间) yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date recordDate;
	
	/**
	 * 签约备案时间(结束时间)
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "签约备案时间(结束时间) yyyy-MM-dd")
	private Date endDate;
	
	/**
	 * 模糊搜索字段(甲方，乙方，签约人，房屋坐落等)
	 */
	@ApiModelProperty(value = "模糊搜索字段(甲方，乙方，签约人，房屋坐落等)")
	private String searchKey;
    
    /**
     * 删除合同是否存在：0、存在  1、不存在
     */
	@ApiModelProperty(hidden = true)
    private Integer delFlag;
    
    /**
     * 主用户的编号(引用主用户表)（当前登陆系统的人）
     */
	@ApiModelProperty(hidden = true)
    private Integer mainUserId;
    
    /**
     * 门店编号
     */
	@ApiModelProperty(value = "门店编号")
    private Integer htmdId;
    
    /**
     * 合同编号
     */
	@ApiModelProperty(value = "合同编号")
    private String contractNumber;
    
    /**
     * 合同类型   1：租赁合同 2：一手房合同  3：二手房合同
     */
	@ApiModelProperty(value = "合同类型   1：租赁合同 2：一手房合同  3：二手房合同")
    private Integer type;
    
    /**
     * 当前用户的公司（引用公司表）
     */
	@ApiModelProperty(hidden = true)
    private Integer companyId;
    
    /**
     * 录入方式 1：在线录入  2：补录
     */
	@ApiModelProperty(value = "录入方式 1：在线录入  2：补录")
    private Integer entryMode;
    
    /**
     * 签约价格
     */
	@ApiModelProperty(value = "签约价格")
    private double signPrice;
    
    /**
     * 引用的模板
     */
	@ApiModelProperty(value = "引用的模板")
    private Integer templateId;
    
    /**
     * 审批  1通过 2未通过 3待审批
     */
	@ApiModelProperty(value = "审批  1通过 2未通过 3待审批")
    private Integer approval;
    
}
