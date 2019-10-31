package com.hengyu.cases.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.cases.dao.CaseCategoryDAO;
import com.hengyu.cases.entity.CaseCategory;
import com.hengyu.cases.service.CaseCategoryService;
import com.hengyu.cases.vo.CaseCategoryVo;

/**
 * <p>
 * 	案例分类Impl实现类
 * </p>
 * @author hongyuan
 * @since  2018年9月6日
 * @version 	
 *
 */
@Service
public class CaseCategoryServiceImpl extends ServiceImpl<CaseCategoryDAO, CaseCategory> implements CaseCategoryService{
	@Autowired
	private CaseCategoryDAO caseCategoryDAO;
	
	@Override
	public List<CaseCategoryVo> queryAllByCompanyId(Integer companyId) {
		return caseCategoryDAO.queryAllByCompanyId(companyId);
	}
	
}
