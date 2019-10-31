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
 * @since 2018-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("edu_exam_answer")
public class ExamAnswer extends Model<ExamAnswer> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("exam_record_id")
    private Integer examRecordId;
    @TableField("test_id")
    private Integer testId;
    @TableField("question_id")
    private Integer questionId;
    @TableField("question_mark")
    private Integer questionMark;
    @TableField("user_answer")
    private String userAnswer;
    private Integer right;
    private Integer mark;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
