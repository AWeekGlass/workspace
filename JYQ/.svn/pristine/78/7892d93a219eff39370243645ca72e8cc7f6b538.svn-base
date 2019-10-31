package com.hengyu.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hengyu.user.entity.Resource;
import com.hengyu.user.vo.ResourceTreeVo;
import com.hengyu.user.vo.ResourceVo;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
public interface ResourceDAO extends BaseMapper<Resource> {
	List<String> queryPermissionByAdminId(@Param("adminId") Integer adminId);

	List<ResourceVo> queryAllPermission();

	List<ResourceTreeVo> queryRootTree(@Param("categoryId") Integer categoryId);

	List<ResourceTreeVo> queryTreeByParentId(@Param("categoryId") Integer categoryId,
			@Param("parentId") Integer parentId);
}
