package com.hengyu.contract.dao;

import com.hengyu.contract.entity.Partner;
import com.hengyu.contract.po.QueryPartnerPo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
 * 中介签约附件表 Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-08-29
 */
public interface PartnerDAO extends BaseMapper<Partner> {

	/**
	 * 分页查询签约附件列表
	 * @param partner
	 * @return
	 */
	List<Partner> queryList(Pagination page,QueryPartnerPo po);
	
	/**
	 * 查询详情
	 * @param id
	 * @return
	 */
	Partner queryById(@Param("id")Integer id);
}
