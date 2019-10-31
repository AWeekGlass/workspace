package com.hengyu.user.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminTreeVo {
	private Integer id;
	private String name;
	private Integer parentId;
	private Integer type;
}
