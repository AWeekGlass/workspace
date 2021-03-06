package com.hengyu.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.hengyu.system.entity.Company;
import com.hengyu.system.po.CompanyPo;
import com.hengyu.system.vo.CompanyInfoVo;
import com.hengyu.system.vo.CompanyVo;

/**
 * 公司信息service
 * 
 * @author hongyuan
 * @since 2018-08-21
 */
public interface CompanyService extends IService<Company> {
	
	boolean addCompany(CompanyPo companyPo);
	
	/**
	 * 创建认证企业
	 * @param company
	 * @return
	 */
	boolean createCompany(Company company);
	
	/**
	 * 加入企业
	 * @param userId
	 * @param companyName
	 * @return
	 */
	String joinCompany(Integer userId,String companyName,String userName);
	
	/**
	 * 根据ID查询公司信息
	 * 
	 * @param id
	 * @return
	 */
	CompanyInfoVo queryCompanyInfoById(Integer id);

	/**
	 * 根据ID修改公司信息
	 * 
	 * @param companyInfoVo
	 * @return
	 */
	boolean update(Company company);

	/**
	 * 根据ID获取公司详情
	 * 
	 * @param id
	 * @return
	 */
	Company queryCompanyDetailById(Integer id);
	
	CompanyVo queryDetailById(Integer id);
	
	/**
	 * 分页查询公司列表
	 * @param page
	 * @param company
	 * @return
	 */
	Page<Company> getList(Page<Company> page,Company company);
	
	/**
	 * 根据公司手机号查询公司负责人
	 * @param phone
	 * @return
	 */
	boolean deleteAdmin(String phone);
}
