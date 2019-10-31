package com.hengyu.system.service.impl;

import com.hengyu.system.entity.Dictionary;
import com.hengyu.system.dao.DictionaryDAO;
import com.hengyu.system.service.DictionaryService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-09-25
 */
@Service
public class DictionaryServiceImpl extends ServiceImpl<DictionaryDAO, Dictionary> implements DictionaryService {
	@Autowired
	private DictionaryDAO dictionaryDAO;

	@Override
	public List<Integer> querySubject() {
		return dictionaryDAO.querySubject();
	}
}
