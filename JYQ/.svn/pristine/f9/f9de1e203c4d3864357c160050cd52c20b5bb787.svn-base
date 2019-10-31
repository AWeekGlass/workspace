package com.hengyu.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hengyu.user.entity.ResourceCategory;
import com.hengyu.user.vo.ResourceCategoryVo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
public interface ResourceCategoryDAO extends BaseMapper<ResourceCategory> {
	/**
	 * 根据公司ID获取资源
	 * 
	 * @param companyId
	 * @return
	 */
	List<ResourceCategoryVo> getAllResourceByCompanyId(@Param("companyId") Integer companyId);
}
