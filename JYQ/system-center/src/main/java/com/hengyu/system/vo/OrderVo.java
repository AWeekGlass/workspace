package com.hengyu.system.vo;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hengyu.system.entity.Discount;
import com.hengyu.system.entity.Module;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderVo {

	/**
	 * 流水编号
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty(value = "订单号")
	private String orderCode;

	@ApiModelProperty(value = "公司id")
	private Integer companyId;

	@NotNull(message="产品id不能为空")
	@ApiModelProperty(value = "产品id")
	private String productId;

	@ApiModelProperty(value = "用户id")
	private Integer adminId;

	@NotNull(message="支付方式不能为空")
	@ApiModelProperty(value = "支付方式 1支付宝 2微信 3银联")
	private Integer payType;

	@NotNull(message="产品价格（单位分）不能为空")
	@ApiModelProperty(value = "产品价格（单位分）")
	private Integer productPrice;

	@NotNull(message="支付价格(单位分)")
	@ApiModelProperty(value = "支付价格(单位分)")
	private Integer payPrice;

	@NotNull(message="人数")
	@ApiModelProperty(value = "人数")
	private Integer number;

	@ApiModelProperty(value = "订单结束时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endTime;
	
	@ApiModelProperty(value = "订单状态 0未支付 1已支付 2关闭")
	private Integer status;

	@ApiModelProperty(value = "外部系统订单编码")
	private String outerCode;

	@ApiModelProperty(value = "单创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	@ApiModelProperty(value = "折扣id(关联用户折扣表id)")
	private String discountId;
	
	@ApiModelProperty(value = "优惠价格(分)")
	private Integer discountPrice;
	
	@ApiModelProperty(value = "支付时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date payTime;
	
	/**
	 * 折扣列表
	 */
	private List<Discount> discounts;
	
	/**
	 * 模块表
	 */
	private List<Module> modules;
}
