package com.hengyu.user.po;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RolePermissionPo {
	private Integer roleId;

	private Integer resourceId;
	
	private Integer organizationId;
	
	private String deptList;
	
	private String adminList;
}
