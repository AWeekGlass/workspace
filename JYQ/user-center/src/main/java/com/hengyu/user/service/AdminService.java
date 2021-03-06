package com.hengyu.user.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.user.entity.Admin;
import com.hengyu.user.po.ChangePhonePo;
import com.hengyu.user.vo.AdminFullVo;
import com.hengyu.user.vo.AdminMiddleVo;
import com.hengyu.user.vo.AdminShortVo;
import com.hengyu.user.vo.AdminSmallVo;
import com.hengyu.user.vo.AdminTreeVo;
import com.hengyu.user.vo.AdminVo;
import com.hengyu.user.vo.MyInfoVo;

/**
 * <p>
 * 中介用户表 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
public interface AdminService extends IService<Admin> {
	/**
	 * 根据区域获取用户列表
	 * 
	 * @param page
	 * @param parameterMap
	 * @return
	 */
	Page<AdminMiddleVo> queryList(Page<AdminMiddleVo> page, Map<String, Object> parameterMap);

	/**
	 * 登录
	 * 
	 * @param phone    手机号
	 * @param password 密码
	 * @return
	 * @throws Exception
	 */
	String login(String phone, String password) throws Exception;
	
	/**
	 * 登录
	 * 
	 * @param adminShortVo 用户信息
	 * @return
	 * @throws Exception
	 */
	String login(AdminShortVo adminShortVo) throws Exception;

	/**
	 * 获取用户简历
	 * 
	 * @param id
	 * @return
	 */
	AdminVo queryDetailById(Integer id);

	/**
	 * 获取门店下员工数量
	 * 
	 * @param storeId
	 * @return
	 */
	Integer queryCntByStoreId(Integer storeId);

	/**
	 * 删除员工
	 * 
	 * @param ids
	 * @return
	 */
	boolean updateDelFlag(List<String> ids);

	Boolean checkPwd(Integer adminId, String password);

	AdminFullVo queryResumeById(Integer id);

	List<AdminSmallVo> queryByUserName(Integer companyId, String userName);

	Integer checkPhone(String phone);
	
	String check(String phone);
	
	/**
	 * 注册新用户
	 * @param registePo
	 * @return
	 */
	RestResponse<String> registe(Admin admin);
	
	/**
	 * 
	 * @param changePhonePo
	 * @return
	 */
	String changePhone(ChangePhonePo changePhonePo);
	
	/**
	 * 查询我的信息
	 * @param userId
	 * @return
	 */
	MyInfoVo queryMyInfo(Integer userId);
	
	/**
	 * 根据模快名称查询该模块管理员
	 * @param key
	 * @return
	 */
	List<Admin> queryUsers(String key,Integer companyId);
	
	List<AdminTreeVo> queryTree(Integer companyId);
}
