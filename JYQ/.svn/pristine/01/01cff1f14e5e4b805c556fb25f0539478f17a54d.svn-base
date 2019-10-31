package com.hengyu.user.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.hengyu.user.entity.RewardRecord;
import com.hengyu.user.vo.RewardRecordVo;

/**
 * <p>
 * 中介奖惩记录表 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-09-05
 */
public interface RewardRecordService extends IService<RewardRecord> {
	List<RewardRecordVo> queryListByType(Map<String, Object> parameterMap);

	boolean updateDelFlag(List<String> ids);

	RewardRecordVo queryDetailById(Integer id);
}
