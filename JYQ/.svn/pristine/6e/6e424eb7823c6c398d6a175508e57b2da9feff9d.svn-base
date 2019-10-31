package com.hengyu.system.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.system.entity.Dictionary;
import com.hengyu.system.service.DictionaryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-09-25
 */
@Api(value = "DictionaryController", tags = "字典表Controller")
@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

	@Autowired
	private DictionaryService dictionaryService;

	@ApiOperation(value = "查询字典表列表", notes = "查询字典表列表")
	@ApiImplicitParam(name = "type", value = "数据类型(不传查询所有)", dataTypeClass = String.class)
	@GetMapping("getList")
	public RestResponse<List<Dictionary>> getList(String type) {
		List<Dictionary> list = new ArrayList<>();
		if (Objects.nonNull(type)) {
			list = dictionaryService.selectList(new EntityWrapper<Dictionary>().eq("type", type));
		} else {
			list = dictionaryService.selectList(new EntityWrapper<Dictionary>());
		}
		return new RestResponse<List<Dictionary>>().rel(true).data(list);
	}

	@GetMapping("querySubject")
	public List<Integer> querySubject() {
		return dictionaryService.querySubject();
	}
}
