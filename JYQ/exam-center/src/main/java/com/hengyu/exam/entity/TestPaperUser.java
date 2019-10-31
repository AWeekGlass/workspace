package com.hengyu.exam.entity;

import java.io.Serializable;
import java.util.Date;

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
 * @since 2018-10-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("edu_test_paper_user")
public class TestPaperUser extends Model<TestPaperUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 考试人员ID
     */
    private Integer userId;
    
    /**
     * 试卷ID
     */
    private Integer paperId;
    
    /**
     * 阅卷人
     */
    private Integer checkUser;
    
    /**
     * 阅卷人姓名
     */
    @TableField(exist=false)
    private String checkName;
    
    /**
     * 是否通过
     */
    private Integer pass;
    
    /**
     * 考试分数
     */
    private Integer score;
    
    /**
     * 考试状态 0-未考试；1-已考试
     */
    private Integer status;
    
    /**
     * 考试开始时间
     */
    private Date startTime;
    
    /**
     * 考试结束时间
     */
    private Date endTime;
    
    /**
     * 是否批阅 0-未批阅；1-已批阅
     */
    private Integer isCheck;
    
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
