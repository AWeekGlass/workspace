package com.hengyu.user.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminMiddleVo extends AdminShortVo {
	/**
	 * 所在部门
	 */
	private String deptName;
	
	/**
	 * 员工角色
	 */
	private String roleName;
	
	/**
	 * 性别（1:男；2:女）
	 */
	private Integer sex;
	
	/**
	 * 联系电话
	 */
	private String telephone;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createTime;
	
	private String statusName;
}
