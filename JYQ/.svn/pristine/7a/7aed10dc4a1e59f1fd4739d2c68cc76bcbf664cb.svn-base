package com.hengyu.report.web;

import static com.hengyu.common.constant.CommonConstants.ADD_FAILURE;
import static com.hengyu.common.constant.CommonConstants.ADD_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.DELETE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.DELETE_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.PAGE_SIZE;
import static com.hengyu.common.constant.CommonConstants.TOKEN;
import static com.hengyu.common.constant.CommonConstants.UPDATE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.UPDATE_SUCCESS;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.report.entity.Report;
import com.hengyu.report.service.ReportService;
import com.hengyu.report.vo.ReportVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 中介报告表 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
@Api(value = "ReportController", tags = "中介报告Controller")
@Slf4j
@RestController
@RequestMapping("/report")
public class ReportController {
	@Autowired
	private ReportService reportService;

	@Autowired
	private JWTUtils jwtUtils;

	@ApiOperation(value = "根据区域获取对应用户", notes = "获取对应用户（请求头添加token）")
	@ApiImplicitParams({ @ApiImplicitParam(name = "current", value = "当前页", defaultValue = "1", dataType = "Integer"),
			@ApiImplicitParam(name = "size", value = "每页行数", defaultValue = PAGE_SIZE, dataType = "Integer"),
			@ApiImplicitParam(name = "type", value = "日报类型", dataType = "Integer") })
	@GetMapping("queryListByType")
	public RestResponse<Page<ReportVo>> queryListByType(@RequestHeader(TOKEN) String token,
			@RequestParam(value = "current", defaultValue = "1", required = false) Integer current,
			@RequestParam(value = "size", defaultValue = PAGE_SIZE, required = false) Integer size,
			@RequestParam(value = "type", defaultValue = "", required = false) Integer type) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);

		return new RestResponse<Page<ReportVo>>().rel(true)
				.data(reportService.queryListByType(new Page<ReportVo>(current, size), info.getCompanyId(), type));
	}

	@ApiOperation(value = "获取中介报告详情", notes = "中介报告详情")
	@ApiImplicitParam(name = "id", value = "中介报告ID", required = true, dataTypeClass = Integer.class)
	@GetMapping("detail/{id}")
	public RestResponse<ReportVo> detail(@PathVariable Integer id) {
		return new RestResponse<ReportVo>().rel(true).data(reportService.queryDetailById(id));
	}

	@ApiOperation(value = "新增中介报告", notes = "新增中介报告（请求头添加token）")
	@ApiImplicitParam(name = "report", value = "中介报告对象", dataTypeClass = Report.class)
	@PostMapping("add")
	public RestResponse<String> add(@RequestHeader(TOKEN) String token, Report report) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		report.setCompanyId(info.getCompanyId());
		boolean flag = reportService.insert(report);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(ADD_SUCCESS);
		} else {
			return response.message(ADD_FAILURE);
		}
	}

	@ApiOperation(value = "更新中介报告", notes = "更新中介报告")
	@ApiImplicitParam(name = "report", value = "中介报告对象", dataTypeClass = Report.class)
	@PostMapping("update")
	public RestResponse<String> update(Report report) {
		boolean flag = reportService.updateById(report);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(UPDATE_SUCCESS);
		} else {
			return response.message(UPDATE_FAILURE);
		}
	}

	@ApiOperation(value = "删除中介报告", notes = "删除中介报告")
	@ApiImplicitParam(name = "id", value = "中介报告ID", required = true, dataTypeClass = Integer.class)
	@DeleteMapping("delete/{id}")
	public RestResponse<String> delete(@PathVariable String id) {
		log.info("id:{}", id);
		boolean flag = reportService.deleteBatchIds(Arrays.asList(id.split(",")));
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(DELETE_SUCCESS);
		} else {
			return response.message(DELETE_FAILURE);
		}
	}
}
