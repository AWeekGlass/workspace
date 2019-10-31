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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 中介用户表
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
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
	 * 密码
	 */
	private String password;

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
    @JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	/**
	 * 民族
	 */
	private String nation;
	/**
	 * 联系电话
	 */
	private String phone;
	/**
	 * 婚姻状况
	 */
	private String maritalStatus;
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
	private String residenceProvinceId;
	/**
	 * 现居地行政区域市ID
	 */
	private String residenceCityId;
	/**
	 * 现居地行政区域区ID
	 */
	private String residenceDistrictId;
	/**
	 * 户籍行政区域省ID
	 */
	private String birthplaceProvinceId;
	/**
	 * 户籍行政区域市ID
	 */
	private String birthplaceCityId;
	/**
	 * 户籍行政区域区ID
	 */
	private String birthplaceDistrictId;
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
	private Date entryDate;

	/**
	 * 转正日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date regularDate;

	/**
	 * 离职日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
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
	
	/**
	 * 邀请人手机号
	 */
	private String inviterPhone;

	/**
	 * 创建人
	 */
	private Integer createId;

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
