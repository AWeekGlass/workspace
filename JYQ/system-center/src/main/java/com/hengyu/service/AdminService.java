package com.hengyu.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hengyu.service.impl.AdminServiceImpl;
import com.hengyu.system.entity.Admin;

@FeignClient(value = "USER-CENTER", fallback = AdminServiceImpl.class)
public interface AdminService {
	@GetMapping("admin/queryCntByStoreId")
	Integer queryCntByStoreId(@RequestParam("storeId") Integer storeId);
	
	/**
	 * 新增公司用户
	 * @param token
	 * @param photo
	 * @param admin
	 * @return
	 */
	@PostMapping("admin/addCompany")
	Integer addCompany(@RequestParam(value = "photo", required = false) MultipartFile photo, Admin admin);
	
	@GetMapping("admin/checkPhone")
	Integer checkPhone(@RequestParam("phone") String phone);
}
