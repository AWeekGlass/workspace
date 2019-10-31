package com.hengyu.user.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hengyu.user.entity.Honor;
import com.hengyu.user.vo.HonorVo;

/**
 * <p>
 * 中介荣誉表 Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-09-05
 */
public interface HonorDAO extends BaseMapper<Honor> {
	List<HonorVo> queryListByAdminId(Map<String, Object> parameterMap);

	HonorVo queryDetailById(Integer id);
}
