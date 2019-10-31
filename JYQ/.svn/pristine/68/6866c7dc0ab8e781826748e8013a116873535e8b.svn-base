package com.hengyu.exam.dao;

import com.hengyu.exam.entity.TestPaperSubject;
import com.hengyu.exam.vo.TypeVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-10-26
 */
public interface TestPaperSubjectDAO extends BaseMapper<TestPaperSubject> {
	List<String> queryScoreByPaperId(@Param("paperId") Integer paperId);
	
	List<TypeVo> queryPaperSubject(@Param("paperId")Integer paperId);
}
