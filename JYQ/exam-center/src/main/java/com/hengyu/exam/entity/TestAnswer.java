package com.hengyu.exam.entity;

import java.io.Serializable;
import java.util.Date;

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
 * 考试答案
 * </p>
 *
 * @author allnas
 * @since 2018-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("edu_test_answer")
public class TestAnswer extends Model<TestAnswer> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 考试记录id
     */
    private Integer testUserId;
    
    /**
     * 试卷id
     */
    private Integer paperId;
    
    /**
     * 试题id
     */
    private Integer questionId;
    
    /**
     * 是否正确
     */
    private Integer right;
    
    /**
     * 员工答案
     */
    private String userAnswer;
    
    /**
     * 注释
     */
    private String mark;
    
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    /**
     * 简答题分数
     */
    private Integer score;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
