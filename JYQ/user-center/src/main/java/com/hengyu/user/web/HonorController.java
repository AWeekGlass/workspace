package com.hengyu.user.web;

import static com.hengyu.common.constant.CommonConstants.ADD_FAILURE;
import static com.hengyu.common.constant.CommonConstants.ADD_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.DELETE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.DELETE_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.TOKEN;
import static com.hengyu.common.constant.CommonConstants.UPDATE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.UPDATE_SUCCESS;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.hengyu.user.entity.Honor;
import com.hengyu.user.service.HonorService;
import com.hengyu.user.vo.HonorVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 中介荣誉表 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-09-05
 */
@Api(value = "HonorController", tags = "荣誉Controller")
@Slf4j
@RestController
@RequestMapping("/honor")
public class HonorController {
	@Value("${honor.upload-img-path}")
	private String uploadFilePath;

	@Autowired
	private HonorService honorService;

	@Autowired
	private JWTUtils jwtUtils;

	@ApiOperation(value = "获取对应用户荣誉记录", notes = "获取对应用户荣誉记录")
	@ApiImplicitParam(name = "adminId", value = "员工ID", dataType = "Integer")
	@GetMapping("queryListByAdminId")
	public RestResponse<List<HonorVo>> queryListByAdminId(@RequestHeader(TOKEN) String token, Integer adminId)
			throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		Map<String, Object> parameterMap = new HashMap<>();
		parameterMap.put("companyId", info.getCompanyId());
		parameterMap.put("adminId", adminId);
		log.info("parameterMap:{}", parameterMap);

		return new RestResponse<List<HonorVo>>().rel(true).data(honorService.queryListByAdminId(parameterMap));
	}

	@ApiOperation(value = "新增荣誉记录", notes = "新增荣誉记录")
	@ApiImplicitParam(name = "honor", value = "荣誉记录对象", dataTypeClass = Honor.class)
	@PostMapping("add")
	public RestResponse<String> add(@RequestHeader(TOKEN) String token, @RequestBody Honor honor) throws Exception {
		log.info("honor:{}", honor);
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		honor.setCompanyId(info.getCompanyId());
		honor.setCreateId(info.getUserId());
		boolean flag = honorService.insert(honor);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(ADD_SUCCESS);
		} else {
			return response.message(ADD_FAILURE);
		}
	}

	@ApiOperation(value = "更新荣誉记录", notes = "更新荣誉记录")
	@ApiImplicitParam(name = "honor", value = "荣誉记录对象", dataTypeClass = Honor.class)
	@PostMapping("update")
	public RestResponse<String> update(@RequestBody Honor honor) {
		log.info("honor:{}", honor);
		boolean flag = honorService.updateById(honor);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(UPDATE_SUCCESS);
		} else {
			return response.message(UPDATE_FAILURE);
		}
	}

	@ApiOperation(value = "删除荣誉记录", notes = "删除荣誉记录")
	@ApiImplicitParam(name = "id", value = "荣誉记录ID", required = true, dataTypeClass = Integer.class)
	@DeleteMapping("delete/{ids}")
	public RestResponse<String> delete(@PathVariable String ids) throws Exception {
		log.info("ids:{}", ids);
		boolean flag = honorService.updateDelFlag(Arrays.asList(ids.split(",")));
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(DELETE_SUCCESS);
		} else {
			return response.message(DELETE_FAILURE);
		}
	}

	@ApiOperation(value = "获取员工荣誉记录", notes = "员工荣誉记录")
	@ApiImplicitParam(name = "id", value = "荣誉记录ID", required = true, dataTypeClass = Integer.class)
	@GetMapping("detail/{id}")
	public RestResponse<HonorVo> detail(@PathVariable Integer id) {
		return new RestResponse<HonorVo>().rel(true).data(honorService.queryDetailById(id));
	}
}
