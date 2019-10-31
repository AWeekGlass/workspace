package com.hengyu.exam.entity;

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
 * @since 2018-09-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("edu_exam_record")
public class ExamRecord extends Model<ExamRecord> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	
	private Integer companyId;
	
	private Integer testId;
	
	private Integer type;
	
	private Integer adminId;
	
	private Integer checkUser;
	
	private Integer pass;
	
	private Integer score;
	
	private Integer status;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	
    /**
     * 1-未批阅；2-已批阅
     */
	private Integer reviewStatus;
	
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}