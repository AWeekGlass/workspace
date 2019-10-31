package com.hengyu.system.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hengyu.common.msg.RestResponse;
import com.hengyu.system.entity.Address;
import com.hengyu.system.service.AddressService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 公用的地址表 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
@Api(value = "AddressController", tags = "地址Controller")
@RestController
@RequestMapping("/address")
public class AddressController {
	@Autowired
	private AddressService addressService;

	@ApiOperation(value = "获取所有省", notes = "获取所有省")
	@GetMapping("province")
	public RestResponse<List<Address>> province() {
		return new RestResponse<List<Address>>().rel(true).data(addressService.queryProvince());
	}

	@ApiOperation(value = "获取所有市", notes = "获取所有市")
	@GetMapping("city/{provinceId}")
	public RestResponse<List<Address>> city(@PathVariable String provinceId) {
		return new RestResponse<List<Address>>().rel(true).data(addressService.queryCityByProvinceId(provinceId));
	}

	@ApiOperation(value = "获取所有区", notes = "获取所有区")
	@GetMapping("district/{cityId}")
	public RestResponse<List<Address>> district(@PathVariable String cityId) {
		return new RestResponse<List<Address>>().rel(true).data(addressService.queryDistrictByCityId(cityId));
	}

	@ApiOperation(value = "获取所有县级市和县", notes = "获取所有县级市和县")
	@GetMapping("county/{cityId}")
	public RestResponse<List<Address>> county(@PathVariable String cityId) {
		return new RestResponse<List<Address>>().rel(true).data(addressService.querycountyByCityId(cityId));
	}
}