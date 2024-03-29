package com.hengyu.cases.po;

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
 * 
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
@Data
public class Resource extends Model<Resource> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	private Integer categoryId;
	private String name;
	private String url;
	private Integer parentId;
	private Integer sort;
	private Integer rootId;
	
	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}