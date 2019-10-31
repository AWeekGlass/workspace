package com.hengyu.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.system.dao.ModuleDAO;
import com.hengyu.system.entity.Module;
import com.hengyu.system.service.ModuleService;

/**
 * <p>
 * 模块表 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-12-06
 */
@Service
public class ModuleServiceImpl extends ServiceImpl<ModuleDAO, Module> implements ModuleService {

	@Autowired
	private ModuleDAO moduleDAO;
	
	@Override
	public Page<Module> queryList(Page<Module> page, Integer companyId) {
		page.setRecords(moduleDAO.queryList(page, companyId));
		return page;
	}

}
