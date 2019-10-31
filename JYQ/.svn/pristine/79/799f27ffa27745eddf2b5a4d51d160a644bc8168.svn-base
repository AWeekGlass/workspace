package com.hengyu.contract.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 中介模板表
 * </p>
 *
 * @author allnas
 * @since 2018-08-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jyq_template")
public class Template extends Model<Template> {

	private static final long serialVersionUID = 1L;

	/**
	 * 模板id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 模板名称
	 */
	@ApiModelProperty(value = "模板名称")
	@NotBlank(message="模板名称不能为空")
	private String name;

	/**
	 * 模板地址
	 */
	@ApiModelProperty(value = "模板地址")
	private String address;

	/**
	 * 省
	 */
	@ApiModelProperty(value = "省")
	private Integer provinceId;

	/**
	 * 市
	 */
	@ApiModelProperty(value = "市")
	private Integer cityId;

	/**
	 * 县级区域地址
	 */
	@ApiModelProperty(value = "县级区域地址")
	private Integer areaId;

	/**
	 * 模板是否存在： 0：存在 1:不存在
	 */
	@ApiModelProperty(hidden = true)
	private Integer delFlag;

	/**
	 * 所属公司（引用公司表）
	 */
	@ApiModelProperty(hidden = true)
	private Integer companyId;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类型")
	private Integer type;
	
	/**
	 * 系统数据 0 是 1 不是
	 */
	@ApiModelProperty(value = "系统数据 0 是 1 不是")
	private Integer systemData;
	
	/**
	 * 类型名称
	 */
	@ApiModelProperty(value = "类型名称")
	@TableField(exist = false)
	private String typeName;

	/**
	 * 行业id
	 */
	private Integer industryId;
	
	/**
	 * 城市名称
	 */
	@ApiModelProperty(value = "城市名称")
	@TableField(exist = false)
	private String  cityName;

	/**
	 * 行业名称
	 */
	@TableField(exist = false)
	private String industryName;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
