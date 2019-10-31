package com.hengyu.user.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.user.dao.OrganizationDAO;
import com.hengyu.user.dao.ResourceDAO;
import com.hengyu.user.entity.Resource;
import com.hengyu.user.service.ResourceService;
import com.hengyu.user.vo.ResourceTreeVo;
import com.hengyu.user.vo.ResourceVo;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceDAO, Resource> implements ResourceService {
	@Autowired
	private ResourceDAO resourceDAO;

	@Autowired
	private OrganizationDAO organizationDAO;

	@Override
	public List<String> queryPermissionByAdminId(Integer adminId) {
		return resourceDAO.queryPermissionByAdminId(adminId);
	}

	@Override
	public List<ResourceVo> queryAllPermission() {
		return resourceDAO.queryAllPermission();
	}

	@Override
	public List<ResourceTreeVo> queryTree(Integer categoryId) {
		List<ResourceTreeVo> resourceTreeVos = resourceDAO.queryRootTree(categoryId);
		resourceTreeVos.forEach(resourceTreeVo -> {
			resourceTreeVo.setRoleList(getChilds(categoryId, resourceTreeVo.getId()));
			resourceTreeVo.setRoleOption(organizationDAO.queryAllByResourceId(resourceTreeVo.getId()));
		});
		return resourceTreeVos;
	}

	private List<ResourceTreeVo> getChilds(Integer categoryId, Integer parentId) {
		List<ResourceTreeVo> resourceTreeVos = resourceDAO.queryTreeByParentId(categoryId, parentId);
		if (!(Objects.isNull(resourceTreeVos) || resourceTreeVos.isEmpty())) {
			resourceTreeVos.forEach(resourceTreeVo -> {
				resourceTreeVo.setRoleList(getChilds(categoryId, resourceTreeVo.getId()));
				resourceTreeVo.setRoleOption(organizationDAO.queryAllByResourceId(resourceTreeVo.getId()));
			});
		}
		return resourceTreeVos;
	}
}
