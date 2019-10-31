package com.hengyu.user.dao;

import com.hengyu.user.entity.Transfer;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 人员调动 Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-09-29
 */
public interface TransferDAO extends BaseMapper<Transfer> {
	
	/**
	 * 查询列表
	 * @param userId
	 * @return
	 */
	List<Transfer> getList(@Param("userId")Integer userId);

}
