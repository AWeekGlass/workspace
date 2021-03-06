package com.hengyu.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hengyu.user.entity.RolePermission;
import com.hengyu.user.vo.RolePermissionVo;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
public interface RolePermissionDAO extends BaseMapper<RolePermission> {
	List<Integer> queryCategoryByRoleId(@Param("roleId") Integer roleId);

	List<RolePermissionVo> queryPermissionByRoleId(@Param("roleId") Integer roleId);
}