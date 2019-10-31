package com.hengyu.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hengyu.system.entity.Notice;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-12-10
 */
public interface NoticeDAO extends BaseMapper<Notice> {

	List<Notice> queryList(Pagination page, @Param("companyId")Integer companyId);
}
