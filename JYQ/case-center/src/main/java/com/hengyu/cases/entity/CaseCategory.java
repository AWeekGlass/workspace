package com.hengyu.cases.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 案例分类表
 * </p>
 * 
 * @author hongyuan
 * @since 2018年9月3日
 * @version
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jyq_case_category")
public class CaseCategory extends Model<CaseCategory> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty("ID")
	private Integer id;

	/**
	 * 分类名称
	 */
	@ApiModelProperty("分类名称")
	private String name;

	/**
	 * 公司ID
	 */
	@ApiModelProperty(hidden = true)
	private Integer companyId;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(hidden = true)
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}
