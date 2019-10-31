package com.hengyu.user.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

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
 * 工作经历
 * </p>
 *
 * @author allnas
 * @since 2018-10-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jyq_work_experience")
public class WorkExperience extends Model<WorkExperience> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 公司名称
	 */
	private String name;

	/**
	 * 公司性质ID
	 */
	private Integer natureId;

	/**
	 * 入职日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date entryDate;

	/**
	 * 离职日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dimissionDate;

	/**
	 * 职位
	 */
	private String position;

	/**
	 * 情况说明
	 */
	private String illustration;

	/**
	 * 员工ID
	 */
	private Integer adminId;
	
	/**
	 * 是否删除 0、未删除 1、已经删除
	 */
	private Integer delFlag;

	/**
	 * 操作人ID
	 */
	private Integer createId;

	/**
	 * 操作人时间
	 */
	@TableField(fill = FieldFill.INSERT)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createTime;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
