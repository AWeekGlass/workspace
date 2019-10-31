package com.hengyu.system.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.hengyu.system.entity.Address;

/**
 * <p>
 * 公用的地址表 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
public interface AddressService extends IService<Address> {

	/**
	 * 获取省
	 * 
	 * @return
	 */
	List<Address> queryProvince();

	/**
	 * 根据省ID获取市
	 * 
	 * @param provinceId
	 *            省ID
	 * @return
	 */
	List<Address> queryCityByProvinceId(String provinceId);

	/**
	 * 根据市ID获取区域
	 * 
	 * @param cityId
	 *            市ID
	 * @return
	 */
	List<Address> queryDistrictByCityId(String cityId);

	/**
	 * 获取所有县级市和县
	 * 
	 * @param cityId
	 * @return
	 */
	List<Address> querycountyByCityId(String cityId);

}
