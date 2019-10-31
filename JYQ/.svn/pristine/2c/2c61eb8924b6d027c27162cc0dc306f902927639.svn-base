package com.hengyu.report.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 中介报告表
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jyq_report")
public class Report extends Model<Report> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	private String code;
	private String name;
	private Integer type;
	private String content;
	private Integer readCount;
	private Integer praiseCount;
	private Integer companyId;
	private Integer adminId;
	private Integer createAdmin;
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
