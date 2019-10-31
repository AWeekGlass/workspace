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
 * 中介荣誉表
 * </p>
 *
 * @author allnas
 * @since 2018-09-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jyq_honor")
public class Honor extends Model<Honor> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 荣誉称号
	 */
	private String name;

	/**
	 * 授予时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date grantTime;

	/**
	 * 授予单位
	 */
	private String grantCompany;

	/**
	 * 说明
	 */
	private String remark;
	
	private Integer companyId;
	
	private Integer adminId;
	
	private Integer createId;
	
	/**
	 * 是否删除 0、未删除 1、已经删除
	 */
	private Integer delFlag;
	
	@TableField(fill = FieldFill.INSERT)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createTime;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
