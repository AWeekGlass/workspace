package com.hengyu.exam.po;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import static com.hengyu.common.constant.CommonConstants.PAGE_SIZE;

@Data
@Accessors(chain = true)
public class SearchQuestionPo {

	@ApiModelProperty(value = "页码")
	private Integer current = 1;
	
	@ApiModelProperty(hidden = true)
	private Integer companyId ;

	@ApiModelProperty(value = "每页个数")
	private Integer size = Integer.valueOf(PAGE_SIZE);

	@ApiModelProperty(value = "题型:1单选 2多选 3判断 4填空 5简答")
	private Integer type;

	@ApiModelProperty(value = "城市id")
	private String cityId;

	@ApiModelProperty(value = "题目类别")
	private Integer category;

	@ApiModelProperty(value = "关键字")
	private String searchKey;
	
	@ApiModelProperty(value = "搜多类型 1 公司题库 2 我的题库")
	private Integer searchType;
	
	@ApiModelProperty(value = "审核状态")
	private Integer status;
	
	@ApiModelProperty(value = "题目状态： 1 未闲置 2闲置")
	private Integer state;
	
	@ApiModelProperty(value = "编号")
	private Integer code;
	
	/**
	 * 题目id查询回收题目专用
	 */
	@ApiModelProperty(hidden = true)
	private List<String> ids;
	
	@ApiModelProperty(hidden = true)
	private List<String> cityIds;
	
	@ApiModelProperty(hidden=true)
	private Integer userId;
	
	@ApiModelProperty(value = "排序规则: asc:正序  desc:倒叙")
	private String orderRule;

}
