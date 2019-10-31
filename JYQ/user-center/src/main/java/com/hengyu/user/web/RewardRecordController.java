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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.user.entity.RewardRecord;
import com.hengyu.user.service.RewardRecordService;
import com.hengyu.user.vo.RewardRecordVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 中介奖惩记录表 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-09-05
 */
@Api(value = "AdminController", tags = "奖罚记录Controller")
@Slf4j
@RestController
@RequestMapping("/rewardRecord")
public class RewardRecordController {
	@Value("${rewardRecord.upload-img-path}")
	private String uploadFilePath;

	@Autowired
	private RewardRecordService rewardRecordService;

	@Autowired
	private JWTUtils jwtUtils;

	@ApiOperation(value = "根据类型获取对应用户奖惩记录", notes = "获取对应用户中介奖惩记录")
	@ApiImplicitParams({ @ApiImplicitParam(name = "adminId", value = "员工ID", dataType = "Integer"),
			@ApiImplicitParam(name = "type", value = "类型", dataType = "Integer") })
	@GetMapping("queryListByType")
	public RestResponse<List<RewardRecordVo>> queryListByType(@RequestHeader(TOKEN) String token,
			@RequestParam(value = "adminId", required = true) Integer adminId,
			@RequestParam(value = "type", defaultValue = "", required = false) Integer type) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		Map<String, Object> parameterMap = new HashMap<>();
		parameterMap.put("companyId", info.getCompanyId());
		parameterMap.put("adminId", adminId);
		parameterMap.put("type", type);
		log.info("parameterMap:{}", parameterMap);

		return new RestResponse<List<RewardRecordVo>>().rel(true)
				.data(rewardRecordService.queryListByType(parameterMap));
	}

	@ApiOperation(value = "新增奖惩记录", notes = "新增奖惩记录")
	@ApiImplicitParam(name = "rewardRecord", value = "奖惩记录对象", dataTypeClass = RewardRecord.class)
	@PostMapping("add")
	public RestResponse<String> add(@RequestHeader(TOKEN) String token, @RequestBody RewardRecord rewardRecord)
			throws Exception {
		log.info("rewardRecord:{}", rewardRecord);
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		rewardRecord.setCompanyId(info.getCompanyId());
		rewardRecord.setCreateId(info.getUserId());
		boolean flag = rewardRecordService.insert(rewardRecord);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(ADD_SUCCESS);
		} else {
			return response.message(ADD_FAILURE);
		}
	}

	@ApiOperation(value = "更新奖惩记录", notes = "更新奖惩记录")
	@ApiImplicitParam(name = "rewardRecord", value = "奖惩记录对象", dataTypeClass = RewardRecord.class)
	@PostMapping("update")
	public RestResponse<String> update(@RequestBody RewardRecord rewardRecord) {
		log.info("rewardRecord:{}", rewardRecord);
		boolean flag = rewardRecordService.updateById(rewardRecord);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(UPDATE_SUCCESS);
		} else {
			return response.message(UPDATE_FAILURE);
		}
	}

	@ApiOperation(value = "删除奖惩记录", notes = "删除奖惩记录")
	@ApiImplicitParam(name = "id", value = "奖惩记录ID", required = true, dataTypeClass = Integer.class)
	@DeleteMapping("delete/{ids}")
	public RestResponse<String> delete(@PathVariable String ids) throws Exception {
		log.info("ids:{}", ids);
		boolean flag = rewardRecordService.updateDelFlag(Arrays.asList(ids.split(",")));
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(DELETE_SUCCESS);
		} else {
			return response.message(DELETE_FAILURE);
		}
	}
	
	@ApiOperation(value = "获取员工奖惩记录", notes = "员工奖惩记录")
	@ApiImplicitParam(name = "id", value = "奖惩记录ID", required = true, dataTypeClass = Integer.class)
	@GetMapping("detail/{id}")
	public RestResponse<RewardRecordVo> detail(@PathVariable Integer id) {
		return new RestResponse<RewardRecordVo>().rel(true).data(rewardRecordService.queryDetailById(id));
	}
}
