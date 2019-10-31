package com.hengyu.system.entity;

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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 中介反馈表
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
@ApiModel(value = "中介反馈类")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_feedback")
public class Feedback extends Model<Feedback> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 反馈人
	 */
	@ApiModelProperty(hidden = true)
	private Integer adminId;

	/**
	 * 内容
	 */
	@ApiModelProperty(value = "内容")
	private String content;

	/**
	 * 电话
	 */
	@ApiModelProperty(value = "电话")
	private String phone;

	/**
	 * 记录状态：1：记录 2：未记录
	 */
	@ApiModelProperty(value = "记录状态：1：记录 2：未记录")
	private Integer status;

	/**
	 * 公司ID
	 */
	@ApiModelProperty(hidden = true)
	private Integer companyId;

	@ApiModelProperty(hidden = true)
	@TableField(fill = FieldFill.INSERT)
	@JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	@TableField(exist = false)
	private String statusName;
	
	@TableField(exist = false)
	private String userName;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
