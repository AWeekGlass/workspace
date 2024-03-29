package com.hengyu.user.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hengyu.common.msg.RestResponse;
import com.hengyu.user.service.ResourceService;
import com.hengyu.user.vo.ResourceTreeVo;
import com.hengyu.user.vo.ResourceVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
@Api(value = "ResourceController", tags = "菜单Controller")
@RestController
@RequestMapping("/resource")
public class ResourceController {
	@Autowired
	private ResourceService resourceService;

	@ApiIgnore
	@GetMapping("queryPermissionByAdminId")
	public List<String> queryPermissionByAdminId(Integer adminId) {
		return resourceService.queryPermissionByAdminId(adminId);
	}

	@ApiOperation(value = "获取所有权限", notes = "获取所有权限")
	@GetMapping("queryAllPermission")
	public RestResponse<List<ResourceVo>> queryAllPermission() {
		return new RestResponse<List<ResourceVo>>().rel(true).data(resourceService.queryAllPermission());
	}
	
	
	@ApiOperation(value = "获取当前所有权限", notes = "获取当前所有权限")
	@GetMapping("queryTree")
	public RestResponse<List<ResourceTreeVo>> queryTree(Integer categoryId) {
		return new RestResponse<List<ResourceTreeVo>>().rel(true).data(resourceService.queryTree(categoryId));
	}
}
