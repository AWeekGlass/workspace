package com.hengyu.contract.service;

import java.io.IOException;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.hengyu.contract.entity.Template;
import com.hengyu.contract.po.QueryTemplatePo;

/**
 * <p>
 * 中介模板表 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-08-29
 */
public interface TemplateService extends IService<Template> {

	/**
	 * 新增模板
	 * @param template
	 * @return
	 */
	boolean save(Template template);
	
	/**
	 * 删除模板
	 * @param id
	 * @return
	 */
	boolean delete(String id);
	
	/**
	 * 更新模板
	 * @param template
	 * @return
	 */
	boolean update(Template template);
	
	/**
	 * 根据id查询详细信息
	 * @param id
	 * @return
	 */
	Template queryById(Integer id) throws IOException;
	
	/**
	 * 分页查询列表
	 * @param page
	 * @param template
	 * @return
	 */
	Page<Template> queryList(Page<Template> page,QueryTemplatePo queryTemplatePo) throws IOException;
	
	/**
	 * 根据公司id查询公司所在县
	 * @param id
	 * @return
	 */
	Integer queryAreaId(Integer id);
	
	/**
	 * 根据类型查询系统模板
	 * @param type
	 * @return
	 */
	String querySysTempate(Integer type,Integer companyId) throws IOException;
	
}
