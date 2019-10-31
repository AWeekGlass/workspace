package com.hengyu.cases.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hengyu.cases.entity.CaseCategory;
import com.hengyu.cases.po.Resource;
import com.hengyu.cases.vo.CaseCategoryVo;

/**
 * <p>
 * 案例分类DAO
 * </p>
 * 
 * @author hongyuan
 * @since 2018年9月6日
 * @version
 *
 */
public interface CaseCategoryDAO extends BaseMapper<CaseCategory> {
	List<CaseCategoryVo> queryAllByCompanyId(@Param("companyId") Integer companyId);
	
	Integer updateSource(Resource resource);
	
	Integer insertSource(Resource resource);
	
	Integer insertOrganization(@Param("id")Integer id);
	
	List<Integer> getSourceId(@Param("id")Integer id);
	
	Integer deleteSource(@Param("id")Integer id);
	
	Integer deleteOrganization(@Param("id")Integer id);
}
