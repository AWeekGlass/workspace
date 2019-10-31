package com.hengyu.user.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.hengyu.user.entity.RolePermission;
import com.hengyu.user.po.RolePermissionPo;
import com.hengyu.user.vo.RolePermissionVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
public interface RolePermissionService extends IService<RolePermission> {
	List<RolePermissionVo> queryPermissionByRoleId(Integer roleId);

	Boolean updateByRoleId(Integer roleId, List<RolePermissionPo> rolePermissions);

	Boolean addByRoleId(List<RolePermission> rolePermissions);
}
