package com.hengyu.cases.service;

import com.baomidou.mybatisplus.service.IService;
import com.hengyu.cases.entity.Answer;

/**
 * <p>
 * 问答回答 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-12-17
 */
public interface AnswerService extends IService<Answer> {
	void updateLikeCnt(Integer id);
	
	boolean sendMessage();
}
