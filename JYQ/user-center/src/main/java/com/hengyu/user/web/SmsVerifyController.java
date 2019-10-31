package com.hengyu.user.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.user.entity.Admin;
import com.hengyu.user.entity.SmsVerify;
import com.hengyu.user.service.AdminService;
import com.hengyu.user.service.SmsVerifyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
@Api(value = "SmsVerifyController", tags = "短信Controller")
@RestController
@RequestMapping("/smsVerify")
public class SmsVerifyController {

	@Autowired
	private SmsVerifyService smsVerifyService;

	@Autowired
	private AdminService adminService;

	@ApiOperation(value = "发送短信", notes = "发送短信")
	@ApiImplicitParams({ @ApiImplicitParam(name = "type", value = "类型", dataType = "Integer"),
			@ApiImplicitParam(name = "phone", value = "手机号", dataType = "String"),
			@ApiImplicitParam(name = "kind", value = "发送类型： 1验证手机号是否在存在 ", dataType = "Integer")})
	@GetMapping("sendCode")
	public RestResponse<String> sendSmsCode(Integer type, String phone,Integer kind)
			throws UnsupportedEncodingException, IOException {
		RestResponse<String> response = new RestResponse<>();
		if(Objects.isNull(kind)){
			int count = adminService.selectCount(new EntityWrapper<Admin>().eq("telephone", phone));
			if (count == 0) {
				return response.rel(false).message("手机号码不存在");
			}
		}
		SmsVerify smsVerify = new SmsVerify();
		smsVerify.setPhone(phone);
		smsVerify.setType(type);
		boolean flag = smsVerifyService.save(smsVerify);
		return response.rel(flag).data(flag ? "发送成功" : "发送失败");
	}

	@ApiOperation(value = "验证短信", notes = "验证短信")
	@ApiImplicitParams({ @ApiImplicitParam(name = "type", value = "类型", dataType = "Integer"),
			@ApiImplicitParam(name = "phone", value = "手机号", dataType = "String"),
			@ApiImplicitParam(name = "smsCode", value = "验证码", dataType = "String") })
	@GetMapping("verifySmsCode")
	public RestResponse<String> verifySmsCode(Integer type, String phone, String smsCode) {
		RestResponse<String> response = new RestResponse<>();
		SmsVerify smsVerify = new SmsVerify();
		smsVerify.setPhone(phone);
		smsVerify.setType(type);
		smsVerify.setValidLastTime(new Date());
		String result = smsVerifyService.verifySmsCode(smsVerify, smsCode);
		if (StringUtils.isEmpty(result)) {
			return response.rel(true).data("成功");
		} else {
			return response.rel(false).message(result);
		}

	}
}
