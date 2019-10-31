package com.hengyu.gateway.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.hengyu.gateway.service.ResourceService;

@Component
public class ResourceServiceImpl implements ResourceService {

	@Override
	public List<String> queryPermissionByAdminId(Integer adminId) {
		return Lists.newArrayList();
	}

}
