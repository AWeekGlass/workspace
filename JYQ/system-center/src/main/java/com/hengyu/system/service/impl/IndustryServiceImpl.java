package com.hengyu.system.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.system.dao.IndustryDAO;
import com.hengyu.system.entity.Industry;
import com.hengyu.system.service.IndustryService;

/**
 * <p>
 * 行业表 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
@Service
public class IndustryServiceImpl extends ServiceImpl<IndustryDAO, Industry> implements IndustryService {

	@Override
	public boolean save(Industry industry) {
		return insert(industry);
	}

	@Override
	public boolean update(Industry industry) {
		return updateById(industry);
	}

	@Override
	public boolean delete(String id) {
		if(!ObjectUtils.isEmpty(id) && !id.isEmpty()){
			List<String> ids = Arrays.asList(id.split(","));
			if(ids!=null && !ids.isEmpty()){
				return deleteBatchIds(ids);
			}
		}
		return false;
	}

	@Override
	public Industry queryById(Integer id) {
		return selectById(id);
	}

	@Override
	public List<Industry> queryList(Industry industry) {
		return selectList(new EntityWrapper<Industry>(industry));
	}

}
