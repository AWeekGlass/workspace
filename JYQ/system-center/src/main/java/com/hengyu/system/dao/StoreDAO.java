package com.hengyu.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hengyu.system.entity.Store;
import com.hengyu.system.vo.StoreShortVo;
import com.hengyu.system.vo.StoreTreeVo;
import com.hengyu.system.vo.StoreVo;

/**
 * <p>
 * 中介门店表 Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
public interface StoreDAO extends BaseMapper<Store> {
	Integer queryCntByName(@Param("name") String name, @Param("parentId") Integer parentId);

	List<StoreVo> queryDept(@Param("companyId") Integer companyId);

	StoreVo queryDetailById(@Param("id") Integer id);

	List<StoreShortVo> queryStoreById(@Param("id") Integer id);

	void updateFlagByParentId(@Param("id") Integer id);

	List<Store> queryCity(@Param("userId") Integer userId, @Param("type") int type,
			@Param("companyId") Integer companyId);

	List<Store> queryAllCity(@Param("type") int type, @Param("companyId") Integer companyId);

	/**
	 * 根据用户id查询用户所属部门类型
	 * 
	 * @param userId
	 * @param companyId
	 * @return
	 */
	Store getParentId(@Param("userId") Integer userId, @Param("companyId") Integer companyId);

	/**
	 * 根据公司查询所有城市
	 * 
	 * @param companyId
	 * @return
	 */
	List<StoreShortVo> queryAllCityByCity(@Param("companyId") Integer companyId);

	/**
	 * 根据父级id查询市级城市
	 * 
	 * @param parentId
	 * @return
	 */
	List<StoreShortVo> queryCityByParentId(@Param("parentId") Integer parentId);

	List<StoreTreeVo> queryRootTree(@Param("companyId") Integer companyId);

	List<StoreTreeVo> queryTree(@Param("companyId") Integer companyId, @Param("parentId") Integer parentId);
	
	StoreTreeVo queryRootStoreTree(@Param("parentId") Integer parentId);
	
	List<StoreTreeVo> queryStoreTree(@Param("parentId") Integer parentId);
}
