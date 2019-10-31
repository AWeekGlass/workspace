package com.hengyu.user.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 教育背景
 * </p>
 *
 * @author allnas
 * @since 2018-09-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jyq_education")
public class Education extends Model<Education> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 员工ID
	 */
	private Integer adminId;

	/**
	 * 员工名称
	 */
	@TableField(exist = false)
	private String adminName;

	/**
	 * 毕业学校
	 */
	private String graduate;

	/**
	 * 教育类型ID
	 */
	private Integer educationId;

	/**
	 * 教育类型
	 */
	@TableField(exist = false)
	private String educationName;

	/**
	 * 入学日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date entranceDate;
	/**
	 * 毕业日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date graduationDate;
	/**
	 * 获得证书
	 */
	private String certificate;

	/**
	 * 删除标记（0:正常；1:删除）
	 */
	private Integer delFlag;

	/**
	 * 创建人ID
	 */
	private Integer createId;

	/**
	 * 创建人
	 */
	@TableField(exist = false)
	private String createName;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
