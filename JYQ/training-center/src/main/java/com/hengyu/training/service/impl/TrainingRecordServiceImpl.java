package com.hengyu.training.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.training.dao.TrainingRecordDAO;
import com.hengyu.training.entity.TrainingRecord;
import com.hengyu.training.service.TrainingRecordService;
import com.hengyu.training.vo.QueryTRecordPo;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-09-03
 */
@Service
public class TrainingRecordServiceImpl extends ServiceImpl<TrainingRecordDAO, TrainingRecord> implements TrainingRecordService {

	@Autowired
	private TrainingRecordDAO trainingRecordDAO;
	
	@Override
	public boolean save(TrainingRecord trainingRecord) {
		return insert(trainingRecord);
	}

	@Override
	public boolean update(TrainingRecord trainingRecord) {
		if(Objects.nonNull(trainingRecord.getFeedbackContent())){
			//改变评论状态为已评论
			trainingRecord.setFeedbackStatus(2);
		}else{
			//如果不是评论则为签到
			trainingRecord.setSignInStatus(2);
			trainingRecord.setSignInTime(new Date());
		}
		return updateById(trainingRecord);
	}

	@Override
	public boolean delete(String id) {
		if(!ObjectUtils.isEmpty(id) && !id.isEmpty()){
			List<String> ids = Arrays.asList(id.split(","));
			if(ids!=null && !ids.isEmpty()){
				return deleteBatchIds(ids);
			}
		}
		return false;
	}

	@Override
	public TrainingRecord queryById(Integer id) {
		return selectById(id);
	}

	@Override
	public Page<TrainingRecord> queryList(Page<TrainingRecord> page,QueryTRecordPo po) {
		return page.setRecords(trainingRecordDAO.queryList(page,po));
	}

	@Override
	public List<TrainingRecord> queryAll(TrainingRecord trainingRecord) {
		return trainingRecordDAO.queryList(trainingRecord);
	}

}
