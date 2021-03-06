package com.hengyu.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hengyu.user.entity.Admin;
import com.hengyu.user.vo.AdminFullVo;
import com.hengyu.user.vo.AdminMiddleVo;
import com.hengyu.user.vo.AdminShortVo;
import com.hengyu.user.vo.AdminSmallVo;
import com.hengyu.user.vo.AdminTreeVo;
import com.hengyu.user.vo.AdminVo;
import com.hengyu.user.vo.MyInfoVo;

/**
 * <p>
 * 中介用户表 Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
public interface AdminDAO extends BaseMapper<Admin> {
	List<AdminMiddleVo> queryList(Pagination pagination, Map<String, Object> parameterMap);

	AdminVo queryDetailById(Integer id);

	Integer queryCntByStoreId(Integer storeId);

	AdminShortVo login(@Param("phone") String phone, @Param("password") String password);
	
	AdminShortVo loginByOpenId(@Param("openId") String openId);

	Boolean checkPwd(@Param("id") Integer adminId, @Param("password") String password);

	AdminFullVo queryResumeById(Integer id);
	
	Integer checkPhone(@Param("phone") String phone);

	List<AdminSmallVo> queryByUserName(@Param("companyId") Integer companyId, @Param("userName") String userName);
	
	/**
	 * 查询我的信息
	 * @param userId
	 * @return
	 */
	MyInfoVo queryMyInfo(@Param("userId")Integer userId);
	
	List<Admin> queryUsers(@Param("key")String key,@Param("companyId")Integer companyId);
	
	List<AdminTreeVo> queryRootTree(@Param("companyId") Integer companyId);
}