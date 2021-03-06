package com.hengyu.system.web;


import static com.hengyu.common.constant.CommonConstants.PAGE_SIZE;
import static com.hengyu.common.constant.CommonConstants.TOKEN;

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
import com.hengyu.system.entity.Notice;
import com.hengyu.system.service.NoticeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-12-10
 */
@Api(value = "NoticeController", tags = "公司公告Controller")
@RestController
@RequestMapping("/notice")
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private JWTUtils jwtUtils;

	
	@ApiOperation(value = "查询系统公告", notes = "查询代办事项")
	@ApiImplicitParams({ @ApiImplicitParam(name = "current", value = "当前页", defaultValue = "1", dataType = "Integer"),
			@ApiImplicitParam(name = "size", value = "每页行数", defaultValue = PAGE_SIZE, dataType = "Integer"),
			@ApiImplicitParam(name = "token", value = "token", dataTypeClass = String.class), })
	@GetMapping("queryList")
	public RestResponse<Page<Notice>> queryList(@RequestHeader(TOKEN) String token,
			@RequestParam(value = "current", defaultValue = "1", required = false) Integer current,
			@RequestParam(value = "size", defaultValue = PAGE_SIZE, required = false) Integer size) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		Page<Notice> page = new Page<>(current, size);
		return new RestResponse<Page<Notice>>().rel(true).data(noticeService.queryList(page, info.getCompanyId()));
	}

}

