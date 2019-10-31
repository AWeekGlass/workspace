package com.hengyu.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 公用的地址表
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_address")
public class Address extends Model<Address> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	private String name;
	private Integer level;
	private String detail;
	private Integer status;
	private String parentId;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
