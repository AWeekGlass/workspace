package com.hengyu.user.vo;

import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResourceVo {
	private Integer id;
	private String name;
	private String url;
	private Integer parentId;
	private Integer categoryId;

	@TableField(exist = false)
	private List<OrganizationVo> organizationList;
}