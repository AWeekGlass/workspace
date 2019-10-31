package com.hengyu.cases.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.hengyu.cases.entity.CaseCategory;
import com.hengyu.cases.vo.CaseCategoryVo;

/**<p>
 * 案例分类Service
 * </p>
 * @author hongyuan
 * @since  2018年9月6日
 * @version 	
 *
 */
public interface CaseCategoryService extends IService<CaseCategory>{
	List<CaseCategoryVo> queryAllByCompanyId(Integer companyId);
}
