package com.hengyu.user.dao;

import com.hengyu.user.entity.Education;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-09-30
 */
public interface EducationDAO extends BaseMapper<Education> {
	List<Education> queryList(Integer adminId);
	
	Education queryDetailById(Integer id);
}
