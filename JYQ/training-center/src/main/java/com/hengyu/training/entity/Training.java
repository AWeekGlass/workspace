package com.hengyu.training.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
@TableName("edu_training")
public class Training extends Model<Training> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	private Integer companyId;

	private String code;

	@NotBlank(message = "培训名称不能为空")
	private String trainingName;

	private String teacher;

	private String cover;

	@NotNull(message = "签到开始时间不能为空")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date signInStartTime;

	@NotNull(message = "签到结束时间不能为空")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date signInEndTime;

	@NotNull(message = "培训开始时间不能为空")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date trainingStartTime;

	@NotNull(message = "培训结束时间不能为空")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date trainingEndTime;

	private String introduce;

	private Integer feedback;

	private Integer selectUser;

	private Integer smsNotify;

	private Integer adminId;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	/**
	 * 培训人员
	 */
	@TableField(exist = false)
	private String users;

	/**
	 * 管理员名称
	 */
	@TableField(exist = false)
	private String userName;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public Integer getTrainingStatus() {
		Date now = Calendar.getInstance().getTime();
		// 未开始
		if (now.before(signInStartTime)) {
			return 1;
		}
		// 签到中
		else if (now.after(signInStartTime) && now.before(signInEndTime)) {
			return 2;
		}
		// 进行中
		else if (now.after(trainingStartTime) && now.before(trainingEndTime)) {
			return 3;
		}
		// 已结束
		else {
			return 4;
		}
	}

}
