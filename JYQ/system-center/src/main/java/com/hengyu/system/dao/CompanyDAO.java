package com.hengyu.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hengyu.system.entity.Company;
import com.hengyu.system.vo.CompanyInfoVo;
import com.hengyu.system.vo.CompanyVo;
import com.hengyu.system.vo.UserVo;

/**
 * <p>
 * 中介公司表 Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
public interface CompanyDAO extends BaseMapper<Company> {
	/**
	 * 根据ID查询公司信息
	 * @param id
	 * @return
	 */
	CompanyInfoVo queryCompanyInfoById(Integer id);
	
	CompanyVo queryDetailById(Integer id);
	
	/**
	 * 分页查询公司列表
	 * @param page
	 * @param company
	 * @return
	 */
	List<Company> getList(Pagination page,Company company);
	
	/**
	 * 根据公司手机号查询用户
	 * @param phone
	 * @return
	 */
	Integer deleteAdmin(@Param("phone") String phone);
	
	/**
	 * 根据公司id查询公司门店数量
	 * @param id
	 * @return
	 */
	Integer countStore(@Param("id")Integer id);

	/**
	 * 根据公司id查询公司员工数量
	 * @param id
	 * @return
	 */
	Integer countStaff(@Param("id")Integer id);
	
	UserVo getUserName(@Param("id")Integer id);
	
	List<UserVo> queryUsers(@Param("key")String key,@Param("companyId")Integer companyId);
}
