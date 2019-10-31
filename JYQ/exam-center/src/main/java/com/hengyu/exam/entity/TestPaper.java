package com.hengyu.exam.entity;

import static com.hengyu.common.constant.CommonConstants.PAGE_SIZE;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
@TableName("edu_test_paper")
public class TestPaper extends Model<TestPaper> {

	private static final long serialVersionUID = 1L;

	@TableField(exist = false)
	private Integer current = 1;

	@TableField(exist = false)
	private Integer size = Integer.parseInt(PAGE_SIZE);

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 试卷名称
	 */
	private String name;

	/**
	 * 试卷编号
	 */
	private String code;

	/**
	 * 题库城市ID
	 */
	private String cityId;

	/**
	 * 考试城市ID
	 */
	private String examCityId;

	/**
	 * 时长
	 */
	private Integer timeLength;

	/**
	 * 阅卷人ID
	 */
	private Integer checkId;

	/**
	 * 是否关联已有培训
	 */
	private Integer isRelation;

	/**
	 * 派发类型（1:随机派发，2:指定派发）
	 */
	private Integer type;

	/**
	 * 派发人ID
	 */
	private Integer distributeId;

	/**
	 * 是否考试（0:未考；1:已考）
	 */
	private Integer isExam;

	/**
	 * 是否短信通知（1：已发送；0:未发送）
	 */
	private Integer isSmsNotice;

	/**
	 * 是否删除（0：正常；1：刪除）
	 */
	private Integer delFlag;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;
	
	/**
	 * 及格分数
	 */
	private Integer passScore;

	/**
	 * 及格率
	 */
	private Integer passRate;

	/**
	 * 0 未有人参加考试 1 未批阅 2 已批阅 3 批阅中
	 */
	private Integer state;

	/**
	 * 题目
	 */
	@TableField(exist = false)
	private List<TestPaperQuestion> questionIds;

	/**
	 * 参培人员
	 */
	@TableField(exist = false)
	private List<Integer> userIds;

	/**
	 * 考题类型
	 */
	@TableField(exist = false)
	private List<String[]> subjectList;

	/**
	 * 公司ID
	 */
	private Integer companyId;

	/**
	 * 考试类型（0:试卷；1:自测）
	 */
	private Integer testType;
	
	/**
	 * 是否发送考试 0 未发送 1已发送
	 */
	private Integer isSend;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}