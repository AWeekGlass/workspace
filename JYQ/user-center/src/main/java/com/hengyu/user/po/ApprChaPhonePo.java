package com.hengyu.user.po;

import lombok.Data;

@Data
public class ApprChaPhonePo {

	/**
	 * id
	 */
	private Integer id;
	
	/**
	 * 是否通过审核 0 未审核 1 通过 2 未通过
	 */
	private Integer state;
}
