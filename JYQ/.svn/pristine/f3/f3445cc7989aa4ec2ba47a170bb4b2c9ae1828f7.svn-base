package com.hengyu.contract.po;

import static com.hengyu.common.constant.CommonConstants.PAGE_SIZE;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class QueryTemplatePo {
	
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
     * 类型 1：租赁合同 2：一手房合同  3：二手房合同
     */
	@ApiModelProperty(value = "类型 1：租赁合同 2：一手房合同  3：二手房合同")
    private Integer type;
    
    /**
     * 行业id
     */
	@ApiModelProperty(value = "行业id")
    private Integer industryId;
    
    /**
     * 模板名称
     */
	@ApiModelProperty(value = "模板名称")
    private String name;
    
    /**
     * 县级区域地址
     */
	@ApiModelProperty(value = "县级区域地址")
    private Integer areaId;
    
    /**
     * 省
     */
	@ApiModelProperty(value = "省")
    private String provinceId;
    
    /**
     * 市
     */
	@ApiModelProperty(value = "市")
    private String cityId;
    
    /**
     * 模板地址
     */
	@ApiModelProperty(value = "模板地址")
    private String address;
    
    /**
     * 模板是否存在：  0：存在  1:不存在
     */
	@ApiModelProperty(hidden = true)
    private Integer delFlag;
    
    /**
     * 所属公司（引用公司表）
     */
	@ApiModelProperty(hidden = true)
    private Integer companyId;
    
}
