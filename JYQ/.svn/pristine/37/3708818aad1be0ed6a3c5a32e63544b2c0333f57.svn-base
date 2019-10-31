package com.hengyu.gateway.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hengyu.gateway.service.impl.ResourceServiceImpl;

@FeignClient(value = "USER-CENTER", fallback = ResourceServiceImpl.class)
public interface ResourceService {
	@GetMapping("resource/queryPermissionByAdminId")
	List<String> queryPermissionByAdminId(@RequestParam("adminId") Integer adminId);
}
