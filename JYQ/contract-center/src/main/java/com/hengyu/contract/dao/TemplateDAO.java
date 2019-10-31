package com.hengyu.contract.dao;

import com.hengyu.contract.entity.Template;
import com.hengyu.contract.po.QueryTemplatePo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
 * 中介模板表 Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-08-29
 */
public interface TemplateDAO extends BaseMapper<Template> {

	/**
	 * 分页查询模板
	 */
	List<Template> queryList(Pagination pagination,QueryTemplatePo queryTemplatePo);
	
	/**
	 * 查询详情
	 * @param id
	 * @return
	 */
	Template queryById(@Param("id")Integer id);
	
	/**
	 * 根据公司id查询公司所在县
	 * @param id
	 * @return
	 */
	Integer queryAreaId(@Param("id")Integer id);
	
	/**
	 * 根据类型查询系统模板
	 * @param type
	 * @return
	 */
	Template querySysTempate(@Param("type")Integer type,@Param("companyId")Integer companyId);
}
