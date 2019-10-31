package com.hengyu.user.service.impl;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.user.dao.RolePermissionDAO;
import com.hengyu.user.dao.SysRoleUserDAO;
import com.hengyu.user.entity.RolePermission;
import com.hengyu.user.entity.SysRoleUser;
import com.hengyu.user.po.RolePermissionPo;
import com.hengyu.user.service.RolePermissionService;
import com.hengyu.user.vo.RolePermissionVo;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionDAO, RolePermission>
		implements RolePermissionService {
	@Autowired
	private RolePermissionDAO rolePermissionDAO;

	@Autowired
	private SysRoleUserDAO sysRoleUserDAO;

	@Override
	public List<RolePermissionVo> queryPermissionByRoleId(Integer roleId) {
		return rolePermissionDAO.queryPermissionByRoleId(roleId);
	}

	@Override
	public Boolean updateByRoleId(Integer roleId, List<RolePermissionPo> rolePermissions) {
		if (Objects.isNull(rolePermissions) || rolePermissions.isEmpty()) {
			return false;
		}

		rolePermissionDAO.delete(new EntityWrapper<RolePermission>().eq("role_id", roleId));
		sysRoleUserDAO.delete(new EntityWrapper<SysRoleUser>().eq("role_id", roleId));

		RolePermission rolePermission = null;
		SysRoleUser sysRoleUser = null;
		String[] deptIds = null, adminIds = null;
		for (RolePermissionPo rolePermissionPo : rolePermissions) {
			rolePermission = new RolePermission();
			BeanUtils.copyProperties(rolePermissionPo, rolePermission);
			insert(rolePermission);

			String deptList = rolePermissionPo.getDeptList();
			if (StringUtils.isNoneEmpty(deptList)) {
				deptIds = deptList.split(",");
				for (String deptId : deptIds) {
					sysRoleUser = new SysRoleUser();
					sysRoleUser.setRoleId(rolePermissionPo.getRoleId());
					sysRoleUser.setResourceId(rolePermissionPo.getResourceId());
					sysRoleUser.setStoreId(Integer.parseInt(deptId));
					sysRoleUserDAO.insert(sysRoleUser);
				}
			}

			String adminList = rolePermissionPo.getAdminList();
			if (StringUtils.isNoneEmpty(adminList)) {
				adminIds = adminList.split(",");
				for (String adminId : adminIds) {
					sysRoleUser = new SysRoleUser();
					sysRoleUser.setRoleId(rolePermissionPo.getRoleId());
					sysRoleUser.setResourceId(rolePermissionPo.getResourceId());
					sysRoleUser.setAdminId(Integer.parseInt(adminId));
					sysRoleUserDAO.insert(sysRoleUser);
				}
			}
		}

		return true;
	}

	@Override
	public Boolean addByRoleId(List<RolePermission> rolePermissions) {
		return insertBatch(rolePermissions);
	}
}