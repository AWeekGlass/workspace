package com.hengyu.cases.entity;

import java.io.Serializable;
import java.util.Date;

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
 * 评论表
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jyq_comment")
public class Comment extends Model<Comment> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 案例ID
	 */
	private Integer caseId;

	/**
	 * 评论员工ID
	 */
	private Integer adminId;

	/**
	 * 回复评论员工ID
	 */
	private Integer replyAdminId;

	/**
	 * 父级ID
	 */
	private Integer parentId;
	
	/**
	 * 顶级ID
	 */
	private Integer topId;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 发表时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
