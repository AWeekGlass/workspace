package com.hengyu.user.service;

import com.hengyu.user.entity.WorkExperience;
import com.hengyu.user.vo.WorkExperienceVo;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 工作经历 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-10-09
 */
public interface WorkExperienceService extends IService<WorkExperience> {

	List<WorkExperienceVo> queryList(Integer adminId);

	boolean updateDelFlag(List<String> ids);

	WorkExperienceVo queryDetailById(Integer id);

}
