package com.hengyu.user.dao;

import com.hengyu.user.entity.WorkExperience;
import com.hengyu.user.vo.WorkExperienceVo;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 工作经历 Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-10-09
 */
public interface WorkExperienceDAO extends BaseMapper<WorkExperience> {

	List<WorkExperienceVo> queryList(Integer adminId);

	WorkExperienceVo queryDetailById(Integer id);

}
