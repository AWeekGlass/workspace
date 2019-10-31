package com.hengyu.user.service;

import com.hengyu.user.entity.Transfer;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 人员调动 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-09-29
 */
public interface TransferService extends IService<Transfer> {

	/**
	 * 新增调动记录
	 * @param transfer
	 * @return
	 */
	boolean add(Transfer transfer);
	
	/**
	 * 删除,多选删除
	 * @param id
	 * @return
	 */
	boolean delete(String id);
	
	/**
	 * 获取列表
	 * @param userId
	 * @return
	 */
	List<Transfer> getList(Integer userId);
}
