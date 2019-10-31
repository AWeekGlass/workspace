package com.hengyu.contract.service;

import com.hengyu.contract.entity.Htenclosure;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 中介合同上传附件表 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-08-31
 */
public interface HtenclosureService extends IService<Htenclosure> {
	
	/**
	 * 新增合同附件
	 * @param htenclosure
	 * @return
	 */
	boolean save(Htenclosure htenclosure);
	
	/**
	 * 根据id删除合同附件
	 * @param id
	 * @return
	 */
	boolean delete(String id);
	
	/**
	 * 更新合同附件
	 * @param htenclosure
	 * @return
	 */
	boolean update(Htenclosure htenclosure);
	
	/**
	 * 查询列表
	 * @param htenclosure
	 * @return
	 */
	List<Htenclosure> queryList(Htenclosure htenclosure);
	
	/**
	 * 根据id查询详情
	 * @param id
	 * @return
	 */
	Htenclosure queryById(Integer id);

}
