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
 * 问答回答
 * </p>
 *
 * @author allnas
 * @since 2018-12-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jyq_answer")
public class Answer extends Model<Answer> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 提问ID
     */
    private Integer questionId;
    
    /**
     * 内容
     */
    private String content;
    
	/**
	 * 点赞个数
	 */
	@TableField("like_cnt")
	private Integer likeCnt;

	/**
	 * 是否热门（1：是；0：否）
	 */
	private Integer isHot;
    
	private Integer adminId;

	private Integer companyId;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;
	
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
