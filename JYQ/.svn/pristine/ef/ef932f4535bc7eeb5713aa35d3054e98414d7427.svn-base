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
 * @since 2018-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("edu_test_paper_question")
public class TestPaperQuestion extends Model<TestPaperQuestion> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 题目ID
     */
    @TableField("question_id")
    private Integer questionId;
    /**
     * 分数
     */
    private Integer record;
    /**
     * 试卷ID
     */
    @TableField("paper_id")
    private Integer paperId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
