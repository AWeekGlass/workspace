package com.hengyu.user.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * 中介奖惩记录表
 * </p>
 *
 * @author allnas
 * @since 2018-09-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jyq_reward_record")
public class RewardRecord extends Model<RewardRecord> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 奖惩类型（1：奖励，2：惩罚）
	 */
	private Integer type;
	
	/**
	 * 方式（1：文本，2：金额）
	 */
	private Integer mode;

	/**
	 * 金额
	 */
	private BigDecimal amount;

	/**
	 * 文字
	 */
	private String content;

	/**
	 * 发生时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date eventTime;

	/**
	 * 执行时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date rewardTime;

	/**
	 * 说明
	 */
	private String remark;

	/**
	 * 公司ID
	 */
	private Integer companyId;

	/**
	 * 奖惩人ID
	 */
	private Integer adminId;
	
	/**
	 * 是否删除 0、未删除 1、已经删除
	 */
	private Integer delFlag;

	/**
	 * 创建人ID
	 */
	private Integer createId;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createTime;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
