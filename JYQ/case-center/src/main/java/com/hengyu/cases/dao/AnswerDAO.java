package com.hengyu.cases.dao;

import com.hengyu.cases.entity.Answer;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 问答回答 Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-12-17
 */
public interface AnswerDAO extends BaseMapper<Answer> {
	void updateLikeCnt(@Param("id") Integer id);
}
