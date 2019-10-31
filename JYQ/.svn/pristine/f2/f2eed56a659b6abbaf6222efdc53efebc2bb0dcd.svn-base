package com.hengyu.user.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.user.dao.EducationDAO;
import com.hengyu.user.entity.Education;
import com.hengyu.user.service.EducationService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-09-30
 */
@Service
public class EducationServiceImpl extends ServiceImpl<EducationDAO, Education> implements EducationService {
	@Autowired
	private EducationDAO educationDAO;

	@Override
	public List<Education> queryList(Integer adminId) {
		return educationDAO.queryList(adminId);
	}

	@Override
	public boolean updateDelFlag(List<String> ids) {
		List<Education> list = ids.stream().map(id -> {
			Education education = new Education();
			education.setId(Integer.parseInt(id));
			education.setDelFlag(1);
			return education;
		}).collect(Collectors.toList());
		return updateBatchById(list);
	}

	@Override
	public Education queryDetailById(Integer id) {
		return educationDAO.queryDetailById(id);
	}

}
