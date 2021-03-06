package com.hengyu.system.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
 * 中介公司表
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
@ApiModel(value = "中介公司类")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jyq_company")
public class Company extends Model<Company> {

	private static final long serialVersionUID = 1L;

	/**
	 * 公司id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 公司名称
	 */
	@NotBlank(message="公司名称不能为空")
	@ApiModelProperty(value = "公司名称")
	private String name;

	/**
	 * 公司简称
	 */
	@ApiModelProperty(value = "公司简称")
	private String abb;

	/**
	 * 省
	 */
	@ApiModelProperty(value = "省")
	private Integer provinceId;

	/**
	 * 市
	 */
	@ApiModelProperty(value = "市")
	private Integer cityId;

	/**
	 * 区
	 */
	@ApiModelProperty(value = "区")
	private Integer areaId;
	
	@NotNull(message="行业id不能为空")
	@ApiModelProperty(value = "行业id")
	private Integer industryId;
	
	@NotNull(message="法定代表人不能为空")
	@ApiModelProperty(value = "法人")
	private String  legalPerson;
	
	@NotNull(message="企业代码不能为空")
	@ApiModelProperty(value = "企业代码")
	private String code;
	
	/**
	 * 详细地址
	 */
	@ApiModelProperty(value = "详细地址")
	private String addressDetail;
	
	@ApiModelProperty(value = "授权委托书")
	private String proxy;

	/**
	 * 上传营业执照地址
	 */
	@ApiModelProperty(value = "上传营业执照地址")
	private String businessUrl;

	/**
	 * 公司logo地址
	 */
	@ApiModelProperty(value = "公司logo地址")
	private String logoUrl;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(hidden = true)
	@TableField(fill = FieldFill.INSERT)
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 是否删除 0：未删除 1：已经删除
	 */
	@ApiModelProperty(hidden = true)
	private Integer delFlag;

	/**
	 * 支付记录引用外键
	 */
	@ApiModelProperty(hidden = true)
	private Integer payResultId;

	@ApiModelProperty(hidden = true)
	private Integer questionShare;

	/**
	 * 审核状态 1-待审核；2-已审核；3-已驳回
	 */
	@ApiModelProperty(value = "审核状态 1-待审核；2-已审核；3-已驳回")
	private Integer status;
	
	/**
	 * 审核状态名称(公司列表用)
	 */
	@TableField(exist = false)
	@ApiModelProperty(hidden = true)
	private String statusName;

	/**
	 * 审核员工（引用管理后台员工表）
	 */
	@ApiModelProperty(hidden = true)
	private Integer staffId;

	/**
	 * 申请状态：0：注册用户 1：试用用户
	 */
	@ApiModelProperty(hidden = true)
	private Integer customerStatus;

	/**
	 * 驳回理由
	 */
	@ApiModelProperty(value = "驳回理由")
	private String reason;

	/**
	 * 编号生成启用状态：0.未启用状态 1.启用状态
	 */
	@ApiModelProperty(hidden = true)
	private Integer numberType;

	@TableField(fill = FieldFill.UPDATE)
	@ApiModelProperty(hidden = true)
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	/**
	 * 责任人ID
	 */
	private Integer adminId;
	
	/**
	 * 职能介绍
	 */
	private String introduction;

	/**
	 * 联系电话
	 */
	private String telephone;

	/**
	 * 签租日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date signDate;

	/**
	 * 到期日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date expireDate;

	/**
	 * 机构地址
	 */
	private String organizationAddress;

	/**
	 * 租金
	 */
	private Double rent;

	/**
	 * 租金类型
	 */
	private Integer rentType;

	/**
	 * 付费方式ID
	 */
	private Integer payTypeId;

	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 是否发送短信 0 不发送 1 发送
	 */
	@ApiModelProperty(value = "是否发送短信 0 不发送 1 发送")
	private Integer shortMessage;
	
	/**
	 * 是否使用短信发送考试信息 0 不发送 1 发送
	 */
	@ApiModelProperty(value = "是否使用短信发送考试信息 0 不发送 1 发送")
	private Integer testMessage;
	
	/**
	 * 签约中心是否使用短信通知 0 不使用 1使用
	 */
	@ApiModelProperty(value = "签约中心是否使用短信通知 0 不使用 1使用")
	private Integer contractMessage;
	
	/**
	 * 是否短信接收签约中心的审核通知 0 不使用 1 使用
	 */
	@ApiModelProperty(value = "是否短信接收签约中心的审核通知 0 不使用 1 使用")
	private Integer approvalMessage;
	
	/**
	 * 负责人姓名
	 */
	@ApiModelProperty(value = "负责人姓名")
	@TableField(exist = false)
	private String userName;
	
	@ApiModelProperty(value = "省")
	@TableField(exist = false)
	private String province;
	
	@ApiModelProperty(value = "市")
	@TableField(exist = false)
	private String city;
	
	@ApiModelProperty(value = "门店数量")
	@TableField(exist = false)
	private Integer storeNum;
	
	@ApiModelProperty(value = "员工数量")
	@TableField(exist = false)
	private Integer staffNum;
	
	@ApiModelProperty(value = "审批编号")
	@TableField(exist = false)
	private String number;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
