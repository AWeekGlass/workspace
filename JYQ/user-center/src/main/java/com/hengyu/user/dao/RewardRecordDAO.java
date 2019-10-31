package com.hengyu.user.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hengyu.user.entity.RewardRecord;
import com.hengyu.user.vo.RewardRecordVo;

/**
 * <p>
 * 中介奖惩记录表 Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-09-05
 */
public interface RewardRecordDAO extends BaseMapper<RewardRecord> {
	List<RewardRecordVo> queryListByType(Map<String, Object> parameterMap);

	RewardRecordVo queryDetailById(Integer id);
}
