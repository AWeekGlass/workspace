package com.hengyu.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.hengyu.system.dao.StoreDAO;
import com.hengyu.system.entity.Store;
import com.hengyu.system.service.StoreService;
import com.hengyu.system.vo.StoreShortVo;
import com.hengyu.system.vo.StoreTreeVo;
import com.hengyu.system.vo.StoreVo;

import io.jsonwebtoken.lang.Collections;

/**
 * <p>
 * 中介门店表 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
@Service
public class StoreServiceImpl extends ServiceImpl<StoreDAO, Store> implements StoreService {
	@Autowired
	private StoreDAO storeDAO;

	@Override
	public List<StoreTreeVo> queryTree(Integer companyId) {
		List<StoreTreeVo> rootList = storeDAO.queryRootTree(companyId);
		rootList.forEach(storeTreeVo -> {
			storeTreeVo.setChilds(childs(storeTreeVo.getId(), companyId));
		});
		return rootList;
	}

	private List<StoreTreeVo> childs(Integer parentId, Integer companyId) {
		List<StoreTreeVo> storeList = storeDAO.queryTree(companyId, parentId);
		if (!(Objects.isNull(storeList) || storeList.isEmpty())) {
			storeList.forEach(store -> {
				store.setChilds(childs(store.getId(), companyId));
			});
		}
		return storeList;
	}

	@Override
	public Integer queryCntByName(String name, Integer parentId) {
		return storeDAO.queryCntByName(name, parentId);
	}

	@Override
	public List<StoreVo> queryDept(Integer companyId) {
		return storeDAO.queryDept(companyId);
	}

	@Override
	public StoreVo queryDetailById(Integer id) {
		return storeDAO.queryDetailById(id);
	}

	@Override
	public List<Store> queryCity(Integer userId, Integer type, Integer companyId) {
		List<Store> list = new ArrayList<>();
		if (type == 1) {
			list = storeDAO.queryCity(userId, 2, companyId);
		} else if (type == 2) {
			list = storeDAO.queryCity(userId, 3, companyId);
		}
		return list;
	}

	@Override
	public List<Store> queryAllCity(Integer type, Integer companyId) {
		List<Store> list = new ArrayList<>();
		if (type == 1) {
			list = storeDAO.queryAllCity(2, companyId);
		} else if (type == 2) {
			list = storeDAO.queryAllCity(3, companyId);
		}
		return list;
	}

	@Override
	public List<Store> queryCityById(Integer provinceId, Integer companyId) {
		return selectList(new EntityWrapper<Store>().eq("type", 3).eq("company_id", companyId)
				.eq("parent_id", provinceId).eq("del_flag", "0"));
	}

	@Override
	public List<StoreShortVo> queryStoreById(Integer id, Integer companyId) {
		List<StoreShortVo> list = new ArrayList<>();
		Store store = storeDAO.getParentId(id, companyId);
		if (Objects.isNull(store) || Objects.isNull(store.getParentId())) {
			list = storeDAO.queryAllCityByCity(companyId);
		} else {
			if (store.getType() == 1) {
				list = storeDAO.queryCityByParentId(store.getParentId());
				return list;
			}
			list = storeDAO.queryStoreById(id);
		}
		return list;
	}

	@Override
	public boolean delete(Integer id) {
		Store store = new Store();
		store.setId(id);
		store.setDelFlag(1);
		storeDAO.updateById(store);

		storeDAO.updateFlagByParentId(id);
		return true;
	}
	
	@Override
	public List<StoreTreeVo> queryStoreTree(Integer parentId)
	{
		List<StoreTreeVo> storeList = Lists.newArrayList();
		storeList.add(storeDAO.queryRootStoreTree(parentId));
		if (!(Objects.isNull(storeList) || storeList.isEmpty())) {
			storeList.forEach(store -> {
				store.setChilds(childs(store.getId()));
			});
		}
		return storeList;
	}
	
	private List<StoreTreeVo> childs(Integer parentId) {
		List<StoreTreeVo> storeList = storeDAO.queryStoreTree(parentId);
		if (!(Objects.isNull(storeList) || storeList.isEmpty())) {
			storeList.forEach(store -> {
				store.setChilds(childs(store.getId()));
			});
		}
		return storeList;
	}
}
