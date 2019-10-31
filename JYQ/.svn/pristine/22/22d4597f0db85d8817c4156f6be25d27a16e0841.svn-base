package com.hengyu.user.dao;

import com.hengyu.user.entity.Organization;
import com.hengyu.user.vo.OrganizationVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-09-19
 */
public interface OrganizationDAO extends BaseMapper<Organization> {
	List<OrganizationVo> queryAllByResourceId(@Param("resourceId") Integer resourceId);
}
