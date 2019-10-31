package com.hengyu.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hengyu.service.impl.CompanyServiceImpl;

@FeignClient(value = "SYSTEM-CENTER", fallback = CompanyServiceImpl.class)
public interface CompanyService {
	@GetMapping("company/checkPhone")
	Integer checkPhone(@RequestParam("phone") String phone);
}
