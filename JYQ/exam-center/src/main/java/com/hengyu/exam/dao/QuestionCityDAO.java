package com.hengyu.exam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hengyu.exam.entity.QuestionCity;

/**
 * <p>
 * 城市题目表 Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-10-18
 */
public interface QuestionCityDAO extends BaseMapper<QuestionCity> {
	
	/**
	 * 根据题目id查询省份
	 * @param questionId
	 * @return
	 */
	List<QuestionCity> getListByQuestionId(@Param("questionId")Integer questionId,@Param("state")Integer state);

	/**
	 * 根据题目id查询关联城市id
	 * @param questionId
	 * @return
	 */
	List<QuestionCity> queryId(@Param("questionId")String questionId);
}
