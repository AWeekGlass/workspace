package com.hengyu.service.impl;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.hengyu.service.AdminService;
import com.hengyu.system.entity.Admin;

@Component
public class AdminServiceImpl implements AdminService {

	@Override
	public Integer queryCntByStoreId(Integer storeId) {
		return -1;
	}

	@Override
	public Integer addCompany(MultipartFile photo, Admin admin) {
		return -1;
	}

	@Override
	public Integer checkPhone(String phone) {
		return -1;
	}

}
