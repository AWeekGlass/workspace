package com.hengyu.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hengyu.system.entity.Module;

/**
 * <p>
 * 模块表 Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-12-06
 */
public interface ModuleDAO extends BaseMapper<Module> {

	List<Module> queryList(Pagination page,@Param("companyId") Integer companyId);
}
