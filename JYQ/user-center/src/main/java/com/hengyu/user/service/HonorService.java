package com.hengyu.user.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.hengyu.user.entity.Honor;
import com.hengyu.user.vo.HonorVo;

/**
 * <p>
 * 中介荣誉表 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-09-05
 */
public interface HonorService extends IService<Honor> {
	List<HonorVo> queryListByAdminId(Map<String, Object> parameterMap);

	boolean updateDelFlag(List<String> asList);

	HonorVo queryDetailById(Integer id);
}
