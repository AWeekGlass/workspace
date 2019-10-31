package com.hengyu.user.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.hengyu.user.entity.Role;
import com.hengyu.user.vo.RoleVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
public interface RoleService extends IService<Role> {
	/**
	 * 根据公司ID获取角色列表
	 * 
	 * @param page
	 * @param companyId
	 * @return
	 */
	Page<RoleVo> queryAllByCompanyId(Page<RoleVo> page, Integer companyId);

	/**
	 * 根据公司ID获取角色列表
	 * 
	 * @param companyId
	 * @return
	 */
	List<RoleVo> queryRoleList(Integer companyId);

	/**
	 * 保存角色
	 * 
	 * @param role
	 * @return
	 */
	boolean save(Role role);

	/**
	 * 更新角色
	 * 
	 * @param role
	 * @return
	 */
	boolean update(Role role);

	/**
	 * 删除角色
	 * 
	 * @param ids
	 * @return
	 */
	boolean updateDelFlag(List<String> ids);
	
	Integer queryCntByName(String name, Integer companyId);
}
