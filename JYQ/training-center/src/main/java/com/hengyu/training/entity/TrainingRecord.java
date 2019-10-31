package com.hengyu.training.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

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
 * 
 * </p>
 *
 * @author allnas
 * @since 2018-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("edu_training_record")
public class TrainingRecord extends Model<TrainingRecord> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    private Integer companyId;
    
    private Integer trainingId;
    
    private Integer adminId;
    
    private Integer signInStatus;
    
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date signInTime;
    
    private Integer feedbackStatus;
    
    private String feedbackContent;
    
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(exist = false)
    private Date trainingStartTime;

    @TableField(exist = false)
    private Date trainingEndTime;
    
    @TableField(exist = false)
    private String trainingName;
    
    @TableField(exist = false)
    private String introduce;
    
    @TableField(exist = false)
    private String userName;
    
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
