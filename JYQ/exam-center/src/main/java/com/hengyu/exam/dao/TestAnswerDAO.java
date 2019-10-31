package com.hengyu.exam.dao;

import com.hengyu.exam.entity.TestAnswer;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 考试答案 Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-11-01
 */
public interface TestAnswerDAO extends BaseMapper<TestAnswer> {

	/**
	 * 根据用户id试卷id和类型id查询题目数量
	 * 
	 * @param paperId
	 * @param userId
	 * @param typeId
	 * @return
	 */
	Integer countScore(@Param("paperId") Integer paperId, @Param("userId") Integer userId,
			@Param("typeId") Integer typeId);

	Integer querySumScore(@Param("paperId") Integer paperId);
	
	/**
	 * 统计试卷中有多少道填空题和简答题
	 * @param paperId
	 * @return
	 */
	Integer countQuestion(@Param("paperId") Integer paperId);
}
