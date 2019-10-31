package com.hengyu.exam.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 城市题目表
 * </p>
 *
 * @author allnas
 * @since 2018-10-18
 */
@JsonIgnoreProperties(value = { "handler" })
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("edu_question_city")
public class QuestionCity extends Model<QuestionCity> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 题目ID
     */
    private Integer questionId;
    
    /**
     * 城市ID
     */
    private Integer cityId;
    
    /**
     * 城市名称
     */
    @TableField(exist=false)
    private String 	cityName;
    
    /**
     * 闲置状态 1 未闲置 2 闲置
     */
    private Integer state;

    /**
     * 公司id
     */
    private Integer companyId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
