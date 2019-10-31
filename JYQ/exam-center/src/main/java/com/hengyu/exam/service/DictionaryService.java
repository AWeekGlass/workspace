package com.hengyu.exam.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.hengyu.exam.service.impl.DictionaryServiceImpl;

@FeignClient(value = "SYSTEM-CENTER", fallback = DictionaryServiceImpl.class)
public interface DictionaryService {
	@GetMapping("dictionary/querySubject")
	List<Integer> querySubject();
}
