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
 * 中介门店表
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jyq_store")
@ApiModel(value="store",subTypes = {Store.class})
public class Store extends Model<Store> {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 名称
	 */
	@ApiModelProperty(value="name")
	private String name;

	/**
	 * 1：部门；2:省；3:市；4:区域; 5:门店；6:小组 
	 */
	private Integer type;

	/**
	 * 父级ID
	 */
	private Integer parentId;

	/**
	 * 是否删除：0、否 1、是
	 */
	private Integer delFlag;

	/**
	 * 部门负责人ID
	 */
	private Integer leadingId;

	/**
	 * 职能介绍
	 */
	private String introduction;

	/**
	 * 创建人ID
	 */
	private Integer createId;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	@JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 更新人ID
	 */
	private Integer updateId;

	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.UPDATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	/**
	 * 公司ID
	 */
	private Integer companyId;

	/**
	 * 联系电话
	 */
	private String telephone;

	/**
	 * 签租日期
	 */
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date signDate;

	/**
	 * 到期日期
	 */
	@JsonFormat(pattern="yyyy-MM-dd")
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
	 * 租金类型(1:年；2:月)
	 */
	private Integer rentType;

	/**
	 * 付款方式
	 */
	private Integer payTypeId;

	/**
	 * 备注
	 */
	private String remark;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
