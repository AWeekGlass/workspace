package com.hengyu.user.web;

import static com.hengyu.common.constant.CommonConstants.ADD_FAILURE;
import static com.hengyu.common.constant.CommonConstants.ADD_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.DELETE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.DELETE_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.PAGE_SIZE;
import static com.hengyu.common.constant.CommonConstants.TOKEN;
import static com.hengyu.common.constant.CommonConstants.UPDATE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.UPDATE_SUCCESS;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.user.entity.Role;
import com.hengyu.user.service.RoleService;
import com.hengyu.user.vo.RoleVo;

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
@Api(value = "RoleController", tags = "角色Controller")
@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController {
	@Autowired
	private RoleService roleService;

	@Autowired
	private JWTUtils jwtUtils;

	@ApiOperation(value = "根据公司获取对应角色", notes = "获取对应角色（请求头添加token）")
	@ApiImplicitParams({ @ApiImplicitParam(name = "current", value = "当前页", defaultValue = "1", dataType = "Integer"),
			@ApiImplicitParam(name = "size", value = "每页行数", defaultValue = PAGE_SIZE, dataType = "Integer") })
	@GetMapping("list")
	public RestResponse<Page<RoleVo>> list(@RequestHeader(TOKEN) String token,
			@RequestParam(value = "current", defaultValue = "1", required = false) Integer current,
			@RequestParam(value = "size", defaultValue = "30", required = false) Integer size) throws Exception {
		log.info("current:{},size:{}", current, size);
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		return new RestResponse<Page<RoleVo>>().rel(true)
				.data(roleService.queryAllByCompanyId(new Page<RoleVo>(current, size), info.getCompanyId()));
	}

	@ApiOperation(value = "公司下保存角色", notes = "保存角色（请求头添加token）")
	@ApiImplicitParam(name = "name", value = "角色名称", dataTypeClass = String.class)
	@PostMapping("add/{name:.+}")
	public RestResponse<String> add(@RequestHeader(TOKEN) String token, @PathVariable String name) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		Integer companyId = info.getCompanyId();
		Integer cnt = roleService.queryCntByName(name, companyId);
		RestResponse<String> response = new RestResponse<String>();
		if (cnt > 0) {
			return response.message("角色名称重复");
		}

		Role role = new Role();
		role.setCompanyId(info.getCompanyId());
		role.setCreateAdmin(info.getUserId());
		role.setName(name);
		boolean flag = roleService.save(role);
		response.rel(flag);
		if (flag) {
			return response.data(ADD_SUCCESS);
		} else {
			return response.message(ADD_FAILURE);
		}
	}

	@ApiOperation(value = "公司下更新角色", notes = "更新角色")
	@ApiImplicitParam(name = "role", value = "角色对象", dataTypeClass = Role.class)
	@PostMapping("update")
	public RestResponse<String> update(@RequestHeader(TOKEN) String token, @RequestBody Role role) throws Exception {
		RestResponse<String> response = new RestResponse<String>();
		Integer cnt = roleService
				.selectCount(new EntityWrapper<Role>().eq("id", role.getId()).eq("name", role.getName()));
		if (cnt > 0) {
			return response.message("角色名称相同");
		}

		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		Integer companyId = info.getCompanyId();
		cnt = roleService.queryCntByName(role.getName(), companyId);
		if (cnt > 0) {
			return response.message("角色名称重复");
		}

		role.setUpdateUser(info.getUserId());
		role.setUpdateTime(new Date());
		boolean flag = roleService.update(role);
		response.rel(flag);
		if (flag) {
			return response.data(UPDATE_SUCCESS);
		} else {
			return response.message(UPDATE_FAILURE);
		}
	}

	@ApiOperation(value = "公司下删除角色", notes = "删除角色")
	@ApiImplicitParam(name = "id", value = "角色对象", dataTypeClass = Integer.class)
	@DeleteMapping("delete/{ids}")
	public RestResponse<String> delete(@PathVariable String ids) throws Exception {
		log.info("ids:{}", ids);
		boolean flag = roleService.updateDelFlag(Arrays.asList(ids.split(",")));
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(DELETE_SUCCESS);
		} else {
			return response.message(DELETE_FAILURE);
		}
	}

	@ApiOperation(value = "公司下角色下拉框", notes = "公司下角色下拉框（请求头添加token）")
	@GetMapping("roleDropDown")
	public RestResponse<List<RoleVo>> roleDropDown(@RequestHeader(TOKEN) String token) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		return new RestResponse<List<RoleVo>>().rel(true).data(roleService.queryRoleList(info.getCompanyId()));
	}
}
