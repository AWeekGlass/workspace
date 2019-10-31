package com.hengyu.user.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.user.dao.HonorDAO;
import com.hengyu.user.entity.Honor;
import com.hengyu.user.service.HonorService;
import com.hengyu.user.vo.HonorVo;

/**
 * <p>
 * 中介荣誉表 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-09-05
 */
@Service
public class HonorServiceImpl extends ServiceImpl<HonorDAO, Honor> implements HonorService {
	@Value("${honor.upload-img-path}")
	private String uploadFilePath;

	@Autowired
	private HonorDAO honorDAO;

	@Override
	public List<HonorVo> queryListByAdminId(Map<String, Object> parameterMap) {
		return honorDAO.queryListByAdminId(parameterMap);
	}
	
	@Override
	public boolean updateDelFlag(List<String> ids) {
		List<Honor> list = ids.stream().map(id -> {
			Honor honor = new Honor();
			honor.setId(Integer.parseInt(id));
			honor.setDelFlag(1);
			return honor;
		}).collect(Collectors.toList());
		
		return updateBatchById(list);
	}

	@Override
	public HonorVo queryDetailById(Integer id) {
		return honorDAO.queryDetailById(id);
	}
}
