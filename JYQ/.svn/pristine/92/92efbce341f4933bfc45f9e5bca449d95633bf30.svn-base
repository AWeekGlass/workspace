package com.hengyu.exam.service;

import com.baomidou.mybatisplus.service.IService;
import com.hengyu.exam.entity.TestAnswer;
import com.hengyu.exam.po.SubmitAnswerPo;

/**
 * <p>
 * 考试答案 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-11-01
 */
public interface TestAnswerService extends IService<TestAnswer> {
	
	boolean submitAnswer(SubmitAnswerPo po);
	
	/**
	 * 批阅试卷
	 * @param po
	 * @param userId 阅卷人id
	 * @return
	 */
	boolean checkQuestion(SubmitAnswerPo po,Integer userId);

}
