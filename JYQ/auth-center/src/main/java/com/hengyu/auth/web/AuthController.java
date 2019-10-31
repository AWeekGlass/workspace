package com.hengyu.auth.web;

import static com.hengyu.common.constant.CommonConstants.JWT_BLACK_SET;
import static com.hengyu.common.constant.CommonConstants.TOKEN;

import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hengyu.common.msg.RestResponse;
import com.hengyu.service.CompanyService;
import com.hengyu.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(value = "AuthController", tags = "权限Controller")
@Slf4j
@RestController
@RequestMapping("auth")
public class AuthController {
	@Autowired
	private UserService userService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private ZSetOperations<String, String> zSetOperations;

	@ApiOperation(value = "根据手机号、密码登录", notes = "用户登录")
	@ApiImplicitParams({ @ApiImplicitParam(name = "phone", value = "手机号", dataType = "String"),
			@ApiImplicitParam(name = "password", value = "密码", dataType = "String") })
	@GetMapping("login")
	public RestResponse<String> login(String phone, String password) throws Exception {
		log.debug("phone:{},password:{}", phone, password);
		RestResponse<String> response = new RestResponse<>();
		if (Objects.isNull(phone)) {
			response.rel(false).message("手机号不能为空！");
			return response;
		}
		if (Objects.isNull(password)) {
			response.rel(false).message("密码不能为空！");
			return response;
		}

		String result = userService.check(phone);
		log.debug("result:{}", result);
		if (Objects.equals(result, 0)) {
			return response.rel(false).message("手机号不存在");
		}

		String token = userService.login(phone, password);
		log.debug("token:{}", token);

		response.rel(StringUtils.isNotEmpty(token));
		return StringUtils.isNotEmpty(token) ? response.data(token) : response.message("账号或密码不对");
	}

	@GetMapping("logout")
	public RestResponse<String> logout(@RequestHeader(TOKEN) String token) throws Exception {
		zSetOperations.add(JWT_BLACK_SET, token, 1);
		return new RestResponse<String>().rel(true).data("登出成功");
	}
}
