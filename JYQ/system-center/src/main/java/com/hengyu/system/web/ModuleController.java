package com.hengyu.system.web;

import static com.hengyu.common.constant.CommonConstants.PAGE_SIZE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.system.entity.Module;
import com.hengyu.system.service.ModuleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 模块表 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-12-06
 */
@Api(value = "ModuleController", tags = "模块Controller")
@RestController
@RequestMapping("/module")
public class ModuleController {

	@Autowired
	private ModuleService moduleService;

	@Autowired
	private JWTUtils jwtUtils;

	@ApiOperation(value = "获取模块列表", notes = "获取模块列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "current", value = "当前页", defaultValue = "1", dataType = "Integer"),
			@ApiImplicitParam(name = "size", value = "每页行数", defaultValue = PAGE_SIZE, dataType = "Integer") })
	@GetMapping("queryList")
	public RestResponse<Page<Module>> queryList(@RequestHeader("token") String token,
			@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer size)
			throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		Page<Module> page = new Page<>(current, size);
		return new RestResponse<Page<Module>>().rel(true).data(moduleService.queryList(page, info.getCompanyId()));
	}

}
