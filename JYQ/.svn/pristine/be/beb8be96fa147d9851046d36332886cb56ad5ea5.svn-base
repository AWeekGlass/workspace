package com.hengyu.training.service.impl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.common.jwt.IJWTInfo;
//import com.hengyu.common.util.FileUtils;
import com.hengyu.training.dao.TrainingDAO;
import com.hengyu.training.dao.TrainingRecordDAO;
import com.hengyu.training.entity.Training;
import com.hengyu.training.entity.TrainingRecord;
import com.hengyu.training.service.TrainingService;
import com.hengyu.training.vo.QueryTrainPo;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-09-03
 */
@Service
public class TrainingServiceImpl extends ServiceImpl<TrainingDAO, Training> implements TrainingService {

//	@Autowired
//	private FileUtils fileUtils;

	@Autowired
	private TrainingDAO trainingDAO;

	@Autowired
	private TrainingRecordDAO trainingRecordDAO;

	@Override
	public boolean save(Training training) {
		boolean flag = insert(training);
		// 添加培训人员关系表
		if (Objects.nonNull(training.getUsers())) {
			List<String> ids = Arrays.asList(training.getUsers().split(","));
			ids.forEach(id -> {
				TrainingRecord trainingRecord = new TrainingRecord();
				trainingRecord.setCompanyId(training.getCompanyId());
				trainingRecord.setAdminId(Integer.valueOf(id));
				trainingRecord.setTrainingId(training.getId());
				trainingRecord.setCreateTime(new Date());
				trainingRecordDAO.insert(trainingRecord);
			});
		}
		return flag;
	}

	@Override
	public boolean update(Training training) {
		return updateById(training);
	}

	@Override
	public boolean delete(String id) {
		if (!ObjectUtils.isEmpty(id) && !id.isEmpty()) {
			List<String> ids = Arrays.asList(id.split(","));
			if (ids != null && !ids.isEmpty()) {
				ids.forEach(actionId -> {
					Training train = selectById(actionId);
					// 删除原有图片
					if (Objects.nonNull(train.getCover())) {
//						fileUtils.deleteFTPFile(train.getCover());
					}
					// 删除培训人员关系
					trainingRecordDAO.delete(new EntityWrapper<TrainingRecord>().eq("training_id", train.getId()));
				});
				return deleteBatchIds(ids);
			}
		}
		return false;
	}

	@Override
	public Page<Training> queryList(Page<Training> page, QueryTrainPo queryTrainPo) {
		return page.setRecords(trainingDAO.queryList(page, queryTrainPo));
	}

	@Override
	public Training queryById(Integer id) {
		return selectById(id);
	}

	@Override
	public Integer signIn(Integer trainingId, IJWTInfo info) {
		Map<String, Object> map = new HashMap<String, Object>();
		Training training = trainingDAO.selectById(trainingId);
		// 在签到时间范围内
		if (training.getTrainingStatus() == 2) {
			Date now = Calendar.getInstance().getTime();
			Integer signInStatus = 2;
			// 迟到
			if (now.after(training.getTrainingStartTime())) {
				signInStatus = 3;
			}

			TrainingRecord criteria = new TrainingRecord();
			criteria.setTrainingId(trainingId);
			criteria.setAdminId(info.getUserId());
			List<TrainingRecord> recordList = trainingRecordDAO.selectList(new EntityWrapper<TrainingRecord>(criteria));

			if (recordList != null && recordList.size() > 0) {
				TrainingRecord record = recordList.get(0);
				if (record.getSignInStatus() == 1) {
					record.setSignInStatus(signInStatus);
					record.setSignInTime(now);
					trainingRecordDAO.updateById(record);
					return 0;
				} else {
					// 已经签到过
					return 2;
				}
			} else {
				// 新增的签到记录
				TrainingRecord obj = new TrainingRecord();
				obj.setCompanyId(info.getCompanyId());
				obj.setAdminId(info.getUserId());
				obj.setTrainingId(trainingId);
				obj.setSignInStatus(signInStatus);
				obj.setSignInTime(now);
				obj.setFeedbackStatus(1);
				trainingRecordDAO.insert(obj);
				return 0;
			}
		}
		return 1;
	}

}
