package com.hengyu.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hengyu.user.entity.Role;
import com.hengyu.user.vo.RoleVo;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
public interface RoleDAO extends BaseMapper<Role> {
	List<RoleVo> queryAllByCompanyId(Pagination pagination, Integer companyId);

	List<RoleVo> queryRoleList(Integer companyId);

	Integer queryCntByName(@Param("name")String name, @Param("companyId")Integer companyId);
}