package com.hengyu.user.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.user.dao.WorkExperienceDAO;
import com.hengyu.user.entity.WorkExperience;
import com.hengyu.user.service.WorkExperienceService;
import com.hengyu.user.vo.WorkExperienceVo;

/**
 * <p>
 * 工作经历 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-10-09
 */
@Service
public class WorkExperienceServiceImpl extends ServiceImpl<WorkExperienceDAO, WorkExperience>
		implements WorkExperienceService {
	@Autowired
	private WorkExperienceDAO workExperienceDAO;

	@Override
	public List<WorkExperienceVo> queryList(Integer adminId) {
		return workExperienceDAO.queryList(adminId);
	}

	@Override
	public boolean updateDelFlag(List<String> ids) {
		List<WorkExperience> list = ids.stream().map(id -> {
			WorkExperience workExperience = new WorkExperience();
			workExperience.setId(Integer.parseInt(id));
			workExperience.setDelFlag(1);
			return workExperience;
		}).collect(Collectors.toList());
		return updateBatchById(list);
	}

	@Override
	public WorkExperienceVo queryDetailById(Integer id) {
		return workExperienceDAO.queryDetailById(id);
	}

}
