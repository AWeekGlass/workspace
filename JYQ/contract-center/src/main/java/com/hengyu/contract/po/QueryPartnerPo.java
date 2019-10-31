package com.hengyu.contract.po;

import static com.hengyu.common.constant.CommonConstants.PAGE_SIZE;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class QueryPartnerPo {
	
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
     * 关联模板id
     */
	@ApiModelProperty(value = "关联模板id")
    private String contractType;
    
    /**
     * 上传时间
     */
	@ApiModelProperty(hidden = true)
    private Date uploadTime;
    
    /**
     * 公司id（引用公司表）
     */
	@ApiModelProperty(hidden = true)
    private String companyId;
    
    /**
     * 文件名称
     */
	@ApiModelProperty(value = "文件名称")
    private String fileName;
    
    /**
     * 行业id
     */
	@ApiModelProperty(value = "行业id")
    private String industryId;
	
	/**
	 * 城市id
	 */
	@ApiModelProperty(value = "城市id")
	private String cityId;
	
	/**
	 * 合同类型
	 */
	@ApiModelProperty(value = "合同类型")
	private Integer type;

}
