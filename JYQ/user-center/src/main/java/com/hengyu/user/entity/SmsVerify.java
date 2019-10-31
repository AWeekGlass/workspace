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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_sms_verify")
public class SmsVerify extends Model<SmsVerify> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	private String phone;
	private Integer type;
	private String smsCode;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date validLastTime;
	private Integer isValid;
	private String extend1;
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
