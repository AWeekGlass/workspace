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
import com.hengyu.user.entity.WorkExperience;
import com.hengyu.user.service.WorkExperienceService;
import com.hengyu.user.vo.WorkExperienceVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 工作经历 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-10-09
 */
@Api(value = "WorkExperienceController", tags = "工作经历Controller")
@Slf4j
@RestController
@RequestMapping("/workExperience")
public class WorkExperienceController {
	@Autowired
	private WorkExperienceService workExperienceService;

	@Autowired
	private JWTUtils jwtUtils;

	@ApiOperation(value = "获取员工工作经历", notes = "获取员工工作经历")
	@ApiImplicitParam(name = "adminId", value = "人员ID", defaultValue = "", dataType = "Integer")
	@GetMapping("queryList")
	public RestResponse<List<WorkExperienceVo>> queryList(Integer adminId) throws Exception {
		return new RestResponse<List<WorkExperienceVo>>().rel(true).data(workExperienceService.queryList(adminId));
	}

	@ApiOperation(value = "新增员工工作经历", notes = "新增工作经历")
	@ApiImplicitParam(name = "workExperience", value = "工作经历", dataTypeClass = WorkExperience.class)
	@PostMapping("add")
	public RestResponse<String> add(@RequestHeader(TOKEN) String token, @RequestBody WorkExperience workExperience)
			throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		workExperience.setCreateId(info.getUserId());
		boolean flag = workExperienceService.insert(workExperience);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(ADD_SUCCESS);
		} else {
			return response.message(ADD_FAILURE);
		}
	}

	@ApiOperation(value = "编辑员工工作经历", notes = "编辑员工工作经历")
	@ApiImplicitParam(name = "workExperience", value = "工作经历", dataTypeClass = WorkExperience.class)
	@PostMapping("update")
	public RestResponse<String> update(@RequestBody WorkExperience workExperience) {
		boolean flag = workExperienceService.updateById(workExperience);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(UPDATE_SUCCESS);
		} else {
			return response.message(UPDATE_FAILURE);
		}
	}

	@ApiOperation(value = "删除员工工作经历", notes = "删除员工工作经历")
	@ApiImplicitParam(name = "id", value = "工作经历ID", required = true, dataTypeClass = Integer.class)
	@DeleteMapping("delete/{ids}")
	public RestResponse<String> delete(@PathVariable String ids) {
		log.info("ids:{}", ids);
		boolean flag = workExperienceService.updateDelFlag(Arrays.asList(ids.split(",")));
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(DELETE_SUCCESS);
		} else {
			return response.message(DELETE_FAILURE);
		}
	}

	@ApiOperation(value = "获取员工工作经历", notes = "员工工作经历")
	@ApiImplicitParam(name = "id", value = "工作经历ID", required = true, dataTypeClass = Integer.class)
	@GetMapping("detail/{id}")
	public RestResponse<WorkExperienceVo> detail(@PathVariable Integer id) {
		return new RestResponse<WorkExperienceVo>().rel(true).data(workExperienceService.queryDetailById(id));
	}
}
