package com.hengyu.user.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.user.dao.RoleDAO;
import com.hengyu.user.dao.RolePermissionDAO;
import com.hengyu.user.entity.Role;
import com.hengyu.user.entity.RolePermission;
import com.hengyu.user.service.RoleService;
import com.hengyu.user.vo.RoleVo;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleDAO, Role> implements RoleService {
	@Autowired
	private RoleDAO roleDAO;

	@Autowired
	private RolePermissionDAO rolePermissionDAO;

	@Override
	public Page<RoleVo> queryAllByCompanyId(Page<RoleVo> page, Integer companyId) {
		return page.setRecords(roleDAO.queryAllByCompanyId(page, companyId));
	}

	@Override
	public List<RoleVo> queryRoleList(Integer companyId) {
		return roleDAO.queryRoleList(companyId);
	}

	@Override
	public boolean save(Role role) {
		return insert(role);
	}

	@Override
	public boolean update(Role role) {
		return updateById(role);
	}

	@Override
	public boolean updateDelFlag(List<String> ids) {
		List<Role> list = ids.stream().map(id -> {
			Role role = new Role();
			role.setId(Integer.parseInt(id));
			role.setDelFlag(1);
			return role;
		}).collect(Collectors.toList());

		rolePermissionDAO.delete(new EntityWrapper<RolePermission>().in("role_id", ids));

		return updateBatchById(list);
	}

	@Override
	public Integer queryCntByName(String name, Integer companyId) {
		return roleDAO.queryCntByName(name, companyId);
	}
}
