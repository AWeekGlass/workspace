package com.hengyu.user.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.user.dao.RewardRecordDAO;
import com.hengyu.user.entity.RewardRecord;
import com.hengyu.user.service.RewardRecordService;
import com.hengyu.user.vo.RewardRecordVo;

/**
 * <p>
 * 中介奖惩记录表 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-09-05
 */
@Service
public class RewardRecordServiceImpl extends ServiceImpl<RewardRecordDAO, RewardRecord> implements RewardRecordService {
	@Value("${rewardRecord.upload-img-path}")
	private String uploadFilePath;

	@Autowired
	private RewardRecordDAO rewardRecordDAO;

	@Override
	public List<RewardRecordVo> queryListByType(Map<String, Object> parameterMap) {
		return rewardRecordDAO.queryListByType(parameterMap);
	}

	@Override
	public boolean updateDelFlag(List<String> ids) {
		List<RewardRecord> list = ids.stream().map(id -> {
			RewardRecord rewardRecord = new RewardRecord();
			rewardRecord.setId(Integer.parseInt(id));
			rewardRecord.setDelFlag(1);
			return rewardRecord;
		}).collect(Collectors.toList());
		return updateBatchById(list);
	}

	@Override
	public RewardRecordVo queryDetailById(Integer id) {
		return rewardRecordDAO.queryDetailById(id);
	}
}
