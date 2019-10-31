package com.hengyu.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.hengyu.system.entity.Module;

/**
 * <p>
 * 模块表 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-12-06
 */
public interface ModuleService extends IService<Module> {

	Page<Module> queryList(Page<Module> page,Integer companyId);
}
