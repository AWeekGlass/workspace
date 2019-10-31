package com.hengyu.system.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.system.dao.FeedbackDAO;
import com.hengyu.system.entity.Feedback;
import com.hengyu.system.service.FeedbackService;

/**
 * <p>
 * 中介反馈表 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackDAO, Feedback> implements FeedbackService {

	@Autowired
	private FeedbackDAO feedbackDAO;
	
	@Override
	public Page<Feedback> getList(Page<Feedback> page, Feedback feedback) {
		return page.setRecords(feedbackDAO.getList(page,feedback));
	}

	@Override
	public Feedback queryById(Integer id) {
		return feedbackDAO.queryById(id);
	}

	@Override
	public boolean delete(String id) {
		if(Objects.nonNull(id)){
			List<String> ids = Arrays.asList(id.split(","));
			if(Objects.nonNull(ids)){
				boolean flag = deleteBatchIds(ids);
				return flag;
			}
		}
		return false;
	}
}
