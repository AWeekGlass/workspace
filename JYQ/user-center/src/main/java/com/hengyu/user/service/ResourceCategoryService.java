package com.hengyu.user.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.hengyu.user.entity.ResourceCategory;
import com.hengyu.user.vo.ResourceCategoryVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
public interface ResourceCategoryService extends IService<ResourceCategory> {
	/**
	 * 根据公司ID获取菜单列表
	 * 
	 * @param companyId
	 * @return
	 */
	List<ResourceCategoryVo> getAllResourceByCompanyId(Integer companyId);
}
