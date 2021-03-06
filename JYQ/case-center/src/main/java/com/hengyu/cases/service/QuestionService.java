package com.hengyu.cases.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.hengyu.cases.entity.Question;
import com.hengyu.cases.vo.QuestionVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author allnas
 * @since 2018-12-14
 */
public interface QuestionService extends IService<Question> {
	Page<QuestionVo> queryAll(Page<QuestionVo> page, Integer companyId, Integer userId);
	
	QuestionVo queryDetailById(Integer userId,Integer id);
	
	void sendWXMessage(Question question);
}
