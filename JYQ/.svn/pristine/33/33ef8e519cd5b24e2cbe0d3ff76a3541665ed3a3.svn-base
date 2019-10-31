package com.hengyu.user.service;

import com.hengyu.user.entity.Education;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-09-30
 */
public interface EducationService extends IService<Education> {
	List<Education> queryList(Integer adminId);

	boolean updateDelFlag(List<String> ids);

	Education queryDetailById(Integer id);
}
