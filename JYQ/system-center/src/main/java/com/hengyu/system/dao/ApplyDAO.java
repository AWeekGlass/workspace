package com.hengyu.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hengyu.system.entity.Apply;
import com.hengyu.system.vo.ApplyVo;

/**
 * <p>
 * 公司申请表 Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-10-16
 */
public interface ApplyDAO extends BaseMapper<Apply> {

	/**
	 * 查询认证公司申请
	 * @param page
	 * @param state
	 * @param searchKey
	 * @param companyId
	 * @return
	 */
	List<ApplyVo> queryList(Pagination page, @Param("state") Integer state, @Param("searchKey") String searchKey,
			@Param("companyId") Integer companyId);

	/**
	 * 设置公司负责人id
	 * @param userId
	 * @param companyId
	 * @return
	 */
	Integer setAdminId(@Param("userId")Integer userId,@Param("companyId")Integer companyId);
	
	/**
	 * 设置公司id
	 * @param userId
	 * @param companyId
	 * @return
	 */
	Integer setCompanyId(@Param("userId")Integer userId,@Param("companyId")Integer companyId);
	
	/**
	 * 根据id查询详情
	 * @param id
	 * @return
	 */
	ApplyVo queryDetail(@Param("id")Integer id);
}
