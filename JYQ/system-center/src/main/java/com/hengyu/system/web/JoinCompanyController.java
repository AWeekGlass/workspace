package com.hengyu.system.web;

import static com.hengyu.common.constant.CommonConstants.PAGE_SIZE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.system.entity.JoinCompany;
import com.hengyu.system.po.ApproveJoinCompanyPo;
import com.hengyu.system.service.JoinCompanyService;
import com.hengyu.system.vo.MessageVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 申请加入企业表 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-12-04
 */
@Api(value = "JoinCompanyController", tags = "申请加入公司Controller")
@RestController
@RequestMapping("/joinCompany")
public class JoinCompanyController {

	@Autowired
	private JoinCompanyService joinCompanyService;

	@Autowired
	private JWTUtils jwtUtils;

	@ApiOperation(value = "查询申请加入企业详情", notes = "查询申请加入企业详情")
	@ApiImplicitParam(name = "id", value = "申请id", dataTypeClass = Integer.class)
	@GetMapping("queryDetail/{id}")
	public RestResponse<JoinCompany> queryDetail(@PathVariable Integer id) {
		return new RestResponse<JoinCompany>().rel(true).data(joinCompanyService.queryDetail(id));
	}

	@ApiOperation(value = "审核申请加入企业", notes = "审核申请加入企业")
	@ApiImplicitParam(name = "id", value = "申请id", dataTypeClass = Integer.class)
	@PostMapping("approvalJoinCompany")
	public RestResponse<String> approvalJoinCompany(@RequestHeader("token") String token,
			@RequestBody ApproveJoinCompanyPo approveJoinCompanyPo) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		approveJoinCompanyPo.setUserId(info.getUserId());
		approveJoinCompanyPo.setCompanyId(info.getCompanyId());
		return new RestResponse<String>().rel(true).data(joinCompanyService.approvalJoinCompany(approveJoinCompanyPo));
	}

	@ApiOperation(value = "获取消息列表", notes = "获取消息列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "current", value = "当前页", defaultValue = "1", dataType = "Integer"),
			@ApiImplicitParam(name = "size", value = "每页行数", defaultValue = PAGE_SIZE, dataType = "Integer"),
			@ApiImplicitParam(name = "status", value = "消息状态 0未读 1已读", dataTypeClass = Integer.class),
			@ApiImplicitParam(name = "type", value = "查询类型 1申请类 2公告类", defaultValue = "1", dataTypeClass = Integer.class) })
	@GetMapping("queryMessagelist")
	public RestResponse<Page<MessageVo>> queryMessagelist(@RequestHeader("token") String token,
			@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer size,
			Integer status, Integer type) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		Page<MessageVo> page = new Page<>(current, size);
		return new RestResponse<Page<MessageVo>>().rel(true)
				.data(joinCompanyService.queryMessagelist(page, status, type, info.getCompanyId()));
	}

}
