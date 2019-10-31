package com.hengyu.system.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.hengyu.system.entity.Industry;

/**
 * <p>
 * 行业表 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
public interface IndustryService extends IService<Industry> {
	
	/**
	 * 新增行业
	 * @param industry
	 * @return
	 */
	boolean save(Industry industry);
	
	/**
	 * 修改行业
	 * @param industry
	 * @return
	 */
	boolean update(Industry industry);
	
	/**
	 * 删除行业
	 * @param ids
	 * @return
	 */
	boolean delete(String id);
	
	/**
	 * 查询行业详情
	 * @param id
	 * @return
	 */
	Industry queryById(Integer id);
	
	/**
	 * 
	 * @param industry
	 * @return
	 */
	List<Industry> queryList(Industry industry);

}
