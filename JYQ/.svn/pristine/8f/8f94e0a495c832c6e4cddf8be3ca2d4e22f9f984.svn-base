package com.hengyu.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.user.dao.ResourceCategoryDAO;
import com.hengyu.user.entity.ResourceCategory;
import com.hengyu.user.service.ResourceCategoryService;
import com.hengyu.user.vo.ResourceCategoryVo;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
@Service
public class ResourceCategoryServiceImpl extends ServiceImpl<ResourceCategoryDAO, ResourceCategory>
		implements ResourceCategoryService {
	@Autowired
	private ResourceCategoryDAO resourceCategoryDAO;

	@Override
	public List<ResourceCategoryVo> getAllResourceByCompanyId(Integer companyId) {
		return resourceCategoryDAO.getAllResourceByCompanyId(companyId);
	}
}
