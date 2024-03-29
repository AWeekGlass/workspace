package com.hengyu.system.dao;

import com.hengyu.system.entity.JoinCompany;
import com.hengyu.system.vo.MessageVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
 * 申请加入企业表 Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-12-04
 */
public interface JoinCompanyDAO extends BaseMapper<JoinCompany> {

	/**
	 * 查询申请类消息列表
	 * @param page
	 * @param companyId
	 * @param status
	 * @return
	 */
	List<MessageVo> queryMessagelist(Pagination page,@Param("companyId")Integer companyId,@Param("status")Integer status);
	
	/**
	 * 查询详情
	 * @param id
	 * @return
	 */
	JoinCompany queryDetail(@Param("id") Integer id);
	
	/**
	 * 修改员工状态
	 * @param userId
	 * @param companyId
	 * @return
	 */
	Integer updateUser(@Param("userId")Integer userId,@Param("companyId")Integer companyId,@Param("storeId")Integer storeId);
}
