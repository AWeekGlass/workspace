package com.hengyu.cases.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hengyu.cases.entity.Question;
import com.hengyu.cases.vo.QuestionVo;
import com.hengyu.cases.vo.UserVo;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-12-14
 */
public interface QuestionDAO extends BaseMapper<Question> {
	List<QuestionVo> queryAll(Pagination pagination,@Param("companyId") Integer companyId, @Param("userId") Integer userId);

	QuestionVo queryDetailById(@Param("userId") Integer userId, @Param("id") Integer id);
	
	List<UserVo> queryUsers(@Param("companyId")Integer companyId);
	
	UserVo getUserName(@Param("id")Integer id);
}
