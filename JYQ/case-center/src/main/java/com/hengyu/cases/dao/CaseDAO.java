package com.hengyu.cases.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hengyu.cases.entity.Case;
import com.hengyu.cases.vo.CaseInfoVo;
import com.hengyu.cases.vo.UserVo;

/**
 * <p>
 * 中介案例表 Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
public interface CaseDAO extends BaseMapper<Case> {

	/**
	 * 根据ID查询案例信息
	 * 
	 * @param caseId
	 * @return
	 */
	CaseInfoVo queryCaseById(@Param("id") Integer id);

	/**
	 * 根据案例类型查看列表
	 * 
	 * @param companyId
	 * @param categoryId
	 * @return
	 */
	List<CaseInfoVo> queryAllByCategoryId(Pagination pagination, @Param("companyId") Integer companyId,
			@Param("categoryId") Integer categoryId, @Param("userId") Integer userId);

	List<CaseInfoVo> queryDraft(@Param("companyId") Integer companyId);

	void updateTop(@Param("userId") Integer userId, @Param("id") Integer id);

	void updateRef(@Param("userId") Integer userId, @Param("id") Integer id);

	void cancelTop(@Param("userId") Integer userId, @Param("id") Integer id);

	void cancelRef(@Param("userId") Integer userId, @Param("id") Integer id);
	
	List<UserVo> queryUsers(@Param("key")String key,@Param("companyId")Integer companyId);
}
