package com.hengyu.contract.po;

import lombok.Data;

@Data
public class PagePo {
	
	/**
	 * 页码
	 */
	private Integer current;

	/**
	 * 每页个数
	 */
	private Integer size;
}
