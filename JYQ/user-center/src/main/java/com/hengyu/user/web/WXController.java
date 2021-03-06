package com.hengyu.user.web;

import static com.hengyu.common.constant.CommonConstants.TOKEN;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.common.util.SNSUserInfo;
import com.hengyu.common.util.WeiXinUtil;
import com.hengyu.common.util.WeixinOauth2Token;
import com.hengyu.user.dao.AdminDAO;
import com.hengyu.user.entity.Admin;
import com.hengyu.user.service.AdminService;
import com.hengyu.user.vo.AdminShortVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(value = "微信扫码登录中心", tags = "微信扫码登录Controller")
@Slf4j
@RestController
@RequestMapping("/wx")
public class WXController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private AdminDAO adminDAO;
	
	@Autowired
	private JWTUtils jwtUtils;

	@Value("${wx.kfAppid}")
	private String appid;
	
	@Value("${wx.appid}")
	private String gappid;
	
	@Value("${wx.secret}")
	private String gsecret;

	@Value("${wx.kfSecret}")
	private String secret;

	/**
	 * 返回扫码页面
	 * @param token
	 * @return
	 */
	@ApiOperation(value = "网页扫码登录", notes = "网页扫码登录(返回扫码页面)")
	@GetMapping(" ")
	public RestResponse<String> weixin() {
		log.info("微信扫码登");
		return new RestResponse<String>().rel(true).data(appid);
	}

	// 微信获取用户信息
	@ApiOperation(value = "网页扫码登录", notes = "网页扫码登录(返回扫码页面)")
	@ApiImplicitParam(name = "code", value = "扫码成功后返回的code值", dataType = "String")
	@GetMapping("WeiXinLogin")
	public RestResponse<String> WeiXinLogin(String code) throws Exception {

		// 通过code获取access_token
		WeixinOauth2Token oauth2Token = WeiXinUtil.getOauth2AccessToken(appid, secret, code);
		log.info("oauth2Token:{}" + oauth2Token.toString());
		String accessToken = oauth2Token.getAccessToken();
		log.info("微信用户accessToken:{}",accessToken);
		String openId = oauth2Token.getOpenId();
		String unionid = oauth2Token.getUnionid();
		log.info("微信用户id(unionid):{}",unionid);
		// 获取到用户的基本信息
		SNSUserInfo snsUserInfo = null;
		snsUserInfo = WeiXinUtil.getSNSUserInfo(accessToken, openId);
		if (snsUserInfo != null) {
			// 证明获取到了微信用户基本信息
			String id = snsUserInfo.getOpenId();
			// 可用作用户id，此值是微信用户的唯一识别
			// 查询库中是否有user
			AdminShortVo adminShortVo = adminDAO.loginByOpenId(id);
			if(Objects.nonNull(adminShortVo)) {
				//如果有人，则返回根据该用户信息生成token返回给前端
				String token = adminService.login(adminShortVo);
				log.info("用户token:{}",token);
				return new RestResponse<String>().rel(true).data(token);
			}
			//如果没有查到记录则根据微信信息生成一个
			return new RestResponse<String>().rel(false).message("未查询到用户信息");
		}
		return new RestResponse<String>().rel(false).data("未查询到微信信息");
	}
	
	
	// 微信获取用户信息
	@ApiOperation(value = "微信内登录", notes = "微信内登录")
	@ApiImplicitParam(name = "code", value = "扫码成功后返回的code值", dataType = "String")
	@GetMapping("weiXLogin")
	public RestResponse<String> weiXLogin(String code) throws Exception {

		// 通过code获取access_token
		WeixinOauth2Token oauth2Token = WeiXinUtil.getOauth2AccessToken(gappid, gsecret, code);
		log.info("oauth2Token:{}" + oauth2Token.toString());
		String accessToken = oauth2Token.getAccessToken();
		log.info("微信用户accessToken:{}",accessToken);
		String openId = oauth2Token.getOpenId();
		String unionid = oauth2Token.getUnionid();
		log.info("微信用户id(unionid):{}",unionid);
		// 获取到用户的基本信息
		SNSUserInfo snsUserInfo = null;
		snsUserInfo = WeiXinUtil.getSNSUserInfo(accessToken, openId);
		if (snsUserInfo != null) {
			// 证明获取到了微信用户基本信息
			String id = snsUserInfo.getOpenId();
			// 可用作用户id，此值是微信用户的唯一识别
			// 查询库中是否有user
			AdminShortVo adminShortVo = adminDAO.loginByOpenId(id);
			if(Objects.nonNull(adminShortVo)) {
				//如果有人，则返回根据该用户信息生成token返回给前端
				String token = adminService.login(adminShortVo);
				log.info("用户token:{}",token);
				return new RestResponse<String>().rel(true).data(token);
			}
			//如果没有查到记录则根据微信信息生成一个
			return new RestResponse<String>().rel(false).message("未查询到用户信息");
		}
		return new RestResponse<String>().rel(false).data("未查询到微信信息");
	}
	
	
	@ApiOperation(value = "绑定微信扫码", notes = "绑定微信扫码(返回扫码页面)")
	@GetMapping("bindingUrl")
	public RestResponse<String> bindingUrl() {
		log.info("微信扫码登");
		String url = "https://open.weixin.qq.com/connect/qrconnect?appid=wxd77510f64c8a32fe&redirect_uri=http://www.jieyiqian.com&response_type=code&scope=snsapi_login&state=STATE#wechat_redirect";
		return new RestResponse<String>().rel(true).data(url);
	}
	
	@ApiOperation(value = "微信获取code", notes = "微信获取code")
	@GetMapping("weiXinUrl")
	public void weiXinUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("微信获取code");
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd77510f64c8a32fe&redirect_uri=http://m.jieyiqian.com/exam&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";
		response.sendRedirect(url);
	}
	
	@ApiOperation(value = "绑定员工微信", notes = "绑定员工微信")
	@ApiImplicitParams({ @ApiImplicitParam(name = "token", value = "token", dataType = "Integer"),
			@ApiImplicitParam(name = "code", value = "临时登录凭证", dataType = "String") })
	@GetMapping("binding")
	public RestResponse<String> binding(@RequestHeader(TOKEN) String token, String code) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		log.info("adminId:{},code:{}", info.getUserId(), code);
		// 通过code获取access_token
		WeixinOauth2Token oauth2Token = WeiXinUtil.getOauth2AccessToken(appid, secret, code);
		Admin admin = new Admin();
		admin.setWxOpenId(oauth2Token.getOpenId());
		admin.setWxNo(oauth2Token.getUnionid());
		admin.setId(info.getUserId());
		boolean flag = adminService.updateById(admin);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data("绑定成功");
		} else {
			return response.message("绑定失败");
		}
	}

}
