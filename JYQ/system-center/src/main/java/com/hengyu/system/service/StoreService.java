package com.hengyu.system.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.hengyu.system.entity.Store;
import com.hengyu.system.vo.StoreShortVo;
import com.hengyu.system.vo.StoreTreeVo;
import com.hengyu.system.vo.StoreVo;

/**
 * <p>
 * 中介门店表 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
public interface StoreService extends IService<Store> {
	
	List<StoreTreeVo> queryTree(Integer companyId);
	
	Integer queryCntByName(String name, Integer parentId);
	
	List<StoreVo> queryDept(Integer companyId);

	StoreVo queryDetailById(Integer id);
	
	/**
	 * 查询组织架构下所有省市部门
	 * @param type
	 * @return
	 */
	List<Store> queryCity(Integer userId,Integer type,Integer companyId);
	
	/**
	 * 查询组织架构下所有省市部门
	 * @param type
	 * @return
	 */
	List<Store> queryAllCity(Integer type,Integer companyId);
	
	/**
	 * 根据省级id查询组织架构中市级部门
	 * @param type
	 * @return
	 */
	List<Store> queryCityById(Integer provinceId,Integer companyId);
	
	List<StoreShortVo> queryStoreById(Integer id,Integer companyId);

	boolean delete(Integer id);


	/**
	 * 查询当前组织的子树
	 * @param parentId
	 * @return
	 */
	List<StoreTreeVo> queryStoreTree(Integer parentId);
}
