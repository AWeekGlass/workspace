package com.hengyu.system.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 折扣优惠表
 * </p>
 *
 * @author allnas
 * @since 2018-12-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jyq_discount")
public class Discount extends Model<Discount> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty(value = "折扣名称")
	private String name;

	@ApiModelProperty(value = "折扣种类 1 满减 2折扣")
	private Integer type;

	@ApiModelProperty(value = "折扣力度  分/折扣")
	private Integer discount;

	@ApiModelProperty(value = "优惠券种类 1新人折扣 2推荐奖励 3活动优惠")
	private Integer kind;

	@ApiModelProperty(value = "生效时间")
	@TableField(fill = FieldFill.INSERT)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	@ApiModelProperty(value = "失效时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endTime;

	@ApiModelProperty(value = "公司id")
	private Integer companyId;

	@ApiModelProperty(value = "领取后的有效期(天)")
	private Integer validity;

	@TableField(exist = false)
	@ApiModelProperty(value = "状态: 1可领取 0不可领取")
	private Integer state;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
