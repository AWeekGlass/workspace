package com.hengyu.user.web;

import static com.hengyu.common.constant.CommonConstants.UPDATE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.UPDATE_SUCCESS;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hengyu.common.msg.RestResponse;
import com.hengyu.user.po.RolePermissionPo;
import com.hengyu.user.service.RolePermissionService;
import com.hengyu.user.vo.RolePermissionVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
@Api(value = "RolePermissionController", tags = "角色权限Controller")
@Slf4j
@RestController
@RequestMapping("/rolePermission")
public class RolePermissionController {
	@Autowired
	private RolePermissionService rolePermissionService;

	@ApiOperation(value = "获取角色权限", notes = "获取角色权限")
	@ApiImplicitParam(name = "roleId", value = "角色ID", required = true, dataTypeClass = Integer.class)
	@GetMapping("queryPermissionByRoleId/{roleId}")
	public RestResponse<List<RolePermissionVo>> queryPermissionByRoleId(@PathVariable Integer roleId) {
		return new RestResponse<List<RolePermissionVo>>().rel(true)
				.data(rolePermissionService.queryPermissionByRoleId(roleId));
	}

	@ApiOperation(value = "更新角色权限", notes = "更新角色权限")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "roleId", value = "角色ID", required = true, dataTypeClass = Integer.class),
			@ApiImplicitParam(name = "rolePermissions", value = "权限列表", dataTypeClass = List.class) })
	@PostMapping("updateByRoleId/{roleId}")
	public RestResponse<String> updateByRoleId(@PathVariable Integer roleId,
			@RequestBody List<RolePermissionPo> rolePermissions) {
		log.info("roleId:{},rolePermissions:{}", roleId, rolePermissions);
		Boolean flag = rolePermissionService.updateByRoleId(roleId, rolePermissions);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(UPDATE_SUCCESS);
		} else {
			return response.message(UPDATE_FAILURE);
		}
	}
}
