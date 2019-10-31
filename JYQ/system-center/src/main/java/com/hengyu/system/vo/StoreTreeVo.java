package com.hengyu.system.vo;

import lombok.Setter;

import java.util.List;

import lombok.Getter;

@Getter
@Setter
public class StoreTreeVo {
	private Integer id;

	private String name;

	private Integer type;

	private List<StoreTreeVo> childs;
}
