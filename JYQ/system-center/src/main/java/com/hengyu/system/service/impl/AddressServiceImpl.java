package com.hengyu.system.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.system.dao.AddressDAO;
import com.hengyu.system.entity.Address;
import com.hengyu.system.service.AddressService;

/**
 * <p>
 * 公用的地址表 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressDAO, Address> implements AddressService {

	@Override
	public List<Address> queryProvince() {
		return selectList(new EntityWrapper<Address>().setSqlSelect("id,name").eq("level", "1"));
	}

	@Override
	public List<Address> queryCityByProvinceId(String provinceId) {
		return selectList(
				new EntityWrapper<Address>().setSqlSelect("id,name").eq("level", "2").eq("parent_id", provinceId));
	}

	@Override
	public List<Address> queryDistrictByCityId(String cityId) {
		return selectList(
				new EntityWrapper<Address>().setSqlSelect("id,name").eq("level", "3").eq("parent_id", cityId));
	}

	@Override
	public List<Address> querycountyByCityId(String cityId) {
		return selectList(new EntityWrapper<Address>().setSqlSelect("id,name").eq("level", "3").eq("parent_id", cityId)
				.notLike("name", "区"));
	}

}
