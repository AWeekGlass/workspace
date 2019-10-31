package com.hengyu.exam.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
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
 * @since 2018-10-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("edu_test_paper_subject")
public class TestPaperSubject extends Model<TestPaperSubject> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 试题类别ID
	 */
	private Integer categoryId;

	/**
	 * 题目类型ID
	 */
	private Integer subjectId;

	/**
	 * 数量
	 */
	private Integer num;

	/**
	 * 分数
	 */
	private Integer score;

	/**
	 * 试卷ID
	 */
	private Integer paperId;
	
	/**
	 * 个数
	 */
	@TableField(exist = false)
	private Integer count;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
