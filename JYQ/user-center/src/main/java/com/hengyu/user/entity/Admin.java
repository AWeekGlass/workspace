package com.hengyu.user.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

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
 * 中介用户表
 * </p>
 *
 * @author allnas
 * @since 2018-09-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jyq_admin")
public class Admin extends Model<Admin> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 姓名
	 */
	private String userName;

	/**
	 * 原密码
	 */
	@TableField(exist = false)
	private String oldPassword;

	@NotBlank
	@ApiModelProperty(value = "密码")
	private String password;

	@NotBlank
	@ApiModelProperty(value = "重复密码")
	@TableField(exist = false)
	private String rePassword;

	/**
	 * 性别（1:男；2:女）
	 */
	private Integer sex;
	/**
	 * 身份证号
	 */
	private String idCard;
	/**
	 * 出生日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date birthday;
	/**
	 * 民族
	 */
	private Integer nationId;

	@NotBlank
	@ApiModelProperty(value = "联系电话")
	private String telephone;

	/**
	 * 婚姻状况（1:未婚；2:已婚）
	 */
	private Integer maritalStatus;

	/**
	 * 毕业院校
	 */
	private String university;

	/**
	 * 政治面貌（1:党员；2:团员；3:群众）
	 */
	private Integer politicalId;

	/**
	 * 专业
	 */
	private String major;
	/**
	 * 学历
	 */
	private Integer educationId;

	/**
	 * 现居地行政区域省ID
	 */
	private Integer residenceProvinceId;

	/**
	 * 现居地行政区域市ID
	 */
	private Integer residenceCityId;

	/**
	 * 现居地行政区域区ID
	 */
	private Integer residenceDistrictId;

	/**
	 * 户籍行政区域省ID
	 */
	private Integer birthplaceProvinceId;

	/**
	 * 户籍行政区域市ID
	 */
	private Integer birthplaceCityId;
	/**
	 * 户籍行政区域区ID
	 */
	private Integer birthplaceDistrictId;
	/**
	 * 现居地址
	 */
	private String residenceAddress;
	/**
	 * 户籍详细地址
	 */
	private String birthplaceAddress;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * QQ
	 */
	private String qq;
	/**
	 * 紧急联系人
	 */
	private String contactPhone;
	/**
	 * 工资银行卡号
	 */
	private String cardNo;
	/**
	 * 员工的头像地址
	 */
	private String headPortrait;
	/**
	 * 公司ID
	 */
	private Integer companyId;
	/**
	 * 门店ID
	 */
	private Integer storeId;
	/**
	 * 角色ID
	 */
	private Integer roleId;
	/**
	 * 状态（1:试用；2:在职；3:离职；4:禁用）
	 */
	private Integer status;

	/**
	 * 员工来源
	 */
	private Integer originId;

	/**
	 * 入职类型
	 */
	private Integer entryType;

	/**
	 * 入职日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date entryDate;

	/**
	 * 转正日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date regularDate;

	/**
	 * 离职日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date quitDate;

	/**
	 * 微信号
	 */
	private String wxNo;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 是否删除 0、未删除 1、已经删除
	 */
	private Integer delFlag;

	private String wxOpenId;

	@NotBlank
	@ApiModelProperty(value = "邀请人手机号")
	private String inviterPhone;

	/**
	 * 创建人
	 */
	private Integer createId;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date updateTime;
	
	@NotBlank
	@ApiModelProperty(value = "验证码")
	@TableField(exist = false)
	private String code;
	
	@ApiModelProperty(value = "实名认证 0 未认证 1 认证")
	private Integer authentication;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
