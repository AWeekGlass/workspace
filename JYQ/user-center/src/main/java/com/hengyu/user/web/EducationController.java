package com.hengyu.user.web;

import static com.hengyu.common.constant.CommonConstants.ADD_FAILURE;
import static com.hengyu.common.constant.CommonConstants.ADD_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.DELETE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.DELETE_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.TOKEN;
import static com.hengyu.common.constant.CommonConstants.UPDATE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.UPDATE_SUCCESS;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.user.entity.Education;
import com.hengyu.user.service.EducationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-09-30
 */
@Api(value = "EducationController", tags = "教育背景Controller")
@Slf4j
@RestController
@RequestMapping("/education")
public class EducationController {

	@Autowired
	private EducationService educationService;

	@Autowired
	private JWTUtils jwtUtils;

	@ApiOperation(value = "获取员工教育背景", notes = "获取员工教育背景")
	@ApiImplicitParam(name = "adminId", value = "人员ID", defaultValue = "1", dataType = "Integer")
	@GetMapping("queryList")
	public RestResponse<List<Education>> queryList(Integer adminId) throws Exception {
		return new RestResponse<List<Education>>().rel(true).data(educationService.queryList(adminId));
	}

	@ApiOperation(value = "新增员工教育背景", notes = "新增员工教育背景")
	@ApiImplicitParam(name = "education", value = "教育背景对象", dataTypeClass = Education.class)
	@PostMapping("add")
	public RestResponse<String> add(@RequestHeader(TOKEN) String token, @RequestBody Education education)
			throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		education.setCreateId(info.getUserId());
		boolean flag = educationService.insert(education);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(ADD_SUCCESS);
		} else {
			return response.message(ADD_FAILURE);
		}
	}

	@ApiOperation(value = "编辑员工教育背景", notes = "编辑员工教育背景")
	@ApiImplicitParam(name = "education", value = "教育背景对象", dataTypeClass = Education.class)
	@PostMapping("update")
	public RestResponse<String> update(@RequestBody Education education) {
		boolean flag = educationService.updateById(education);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(UPDATE_SUCCESS);
		} else {
			return response.message(UPDATE_FAILURE);
		}
	}

	@ApiOperation(value = "删除员工教育背景", notes = "删除员工教育背景")
	@ApiImplicitParam(name = "id", value = "教育背景ID", required = true, dataTypeClass = Integer.class)
	@DeleteMapping("delete/{ids}")
	public RestResponse<String> delete(@PathVariable String ids) {
		log.info("ids:{}", ids);
		boolean flag = educationService.updateDelFlag(Arrays.asList(ids.split(",")));
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(DELETE_SUCCESS);
		} else {
			return response.message(DELETE_FAILURE);
		}
	}

	@ApiOperation(value = "获取员工教育背景", notes = "员工教育背景详情")
	@ApiImplicitParam(name = "id", value = "教育背景ID", required = true, dataTypeClass = Integer.class)
	@GetMapping("detail/{id}")
	public RestResponse<Education> detail(@PathVariable Integer id) {
		return new RestResponse<Education>().rel(true).data(educationService.queryDetailById(id));
	}
}
