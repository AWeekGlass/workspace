package com.hengyu.user.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.hengyu.user.entity.Resource;
import com.hengyu.user.vo.ResourceTreeVo;
import com.hengyu.user.vo.ResourceVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
public interface ResourceService extends IService<Resource> {
	List<String> queryPermissionByAdminId(Integer adminId);
	List<ResourceVo> queryAllPermission();
	
	List<ResourceTreeVo> queryTree(Integer categoryId);
}
