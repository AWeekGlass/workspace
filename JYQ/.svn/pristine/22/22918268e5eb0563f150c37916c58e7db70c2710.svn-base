package com.hengyu.exam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hengyu.exam.entity.Question;
import com.hengyu.exam.entity.TestPaperSubject;
import com.hengyu.exam.po.SearchQuestionPo;
import com.hengyu.exam.vo.CountVo;
import com.hengyu.exam.vo.QuestionVo;
import com.hengyu.exam.vo.QuestionsVo;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-09-04
 */
public interface QuestionDAO extends BaseMapper<Question> {
	
	/**
	 * 根据公司获取题库
	 * 
	 * @param pagination
	 * @param parameterMap 参数
	 * @return
	 */
	List<QuestionVo> queryQuestionsByCompanyId(Pagination pagination, SearchQuestionPo po);

	/**
	 * 根据题目ID获取详情
	 * @param id 题目ID
	 * @return
	 */
	QuestionVo queryDetailById(@Param("id") Integer id);
	
	/**
	 * 根据试卷id和考生id获取考生试题和答案
	 * @param paperId
	 * @param userId
	 * @return
	 */
	List<QuestionsVo> queryTypeChecked(@Param("paperId")Integer paperId,@Param("userId")Integer userId);
	
	/**
	 * 统计题目数量
	 * @param companyId
	 * @return
	 */
	List<CountVo> count(@Param("companyId")Integer companyId,@Param("ids")List<String> ids);
	
	/**
	 * 根据公司获取题库
	 * 
	 * @param pagination
	 * @param parameterMap 参数
	 * @return
	 */
	List<QuestionVo> queryRecovery(Pagination pagination, SearchQuestionPo po);
	
	List<Integer> queryQuestions(@Param("type")Integer type,@Param("category")Integer category,@Param("limit")Integer limit);

	List<TestPaperSubject> queryQuestionInfo(List<Integer> list);
}
