package com.hengyu.exam.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.hengyu.exam.entity.Question;
import com.hengyu.exam.po.SearchQuestionPo;
import com.hengyu.exam.po.UpdateStatePo;
import com.hengyu.exam.vo.CountVo;
import com.hengyu.exam.vo.QuestionVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-09-04
 */
public interface QuestionService extends IService<Question> {
	
	/**
	 * 查询公司题目
	 * @param page
	 * @param parameterMap
	 * @return
	 */
	Page<QuestionVo> queryQuestionsByCompanyId(Page<QuestionVo> page, SearchQuestionPo po);
	
	/**
	 * 查询公司题目
	 * @param page
	 * @param parameterMap
	 * @return
	 */
	Page<QuestionVo> queryRecovery(Page<QuestionVo> page, SearchQuestionPo po);

	/**
	 * 查询详情
	 * @param id
	 * @return
	 */
	QuestionVo queryDetailById(Integer id,Integer state);
	
	/**
	 * 新增题目
	 * @param question
	 * @return
	 */
	boolean add (Question question);
	
	/**
	 * 删除题目
	 * @param id
	 * @return
	 */
	boolean delete(String id,String cityId,Integer type,Integer companyId);
	
	/**
	 * 更新题目
	 * @param question
	 * @return
	 */
	boolean update(Question question);
	
	/**
	 * 统计题目数量
	 * @param companyId
	 * @return
	 */
	List<CountVo> count(Integer companyId,String cityIds);
	
	/**
	 * 恢复回收站内的题目
	 * @param question
	 * @return
	 */
	boolean updateState(UpdateStatePo po);
	
}
