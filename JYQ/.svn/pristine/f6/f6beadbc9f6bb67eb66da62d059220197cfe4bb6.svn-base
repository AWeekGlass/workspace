package com.hengyu.common.util;

import lombok.Data;

/**
 * 网页授权信息
 * @author Administrator
 *
 */
@Data
public class WeixinOauth2Token {
	
	//网页授权接口调用凭证
	private  String accessToken;
	
	//凭证有效时长
	private int expiresIn;
	
	//用于刷新凭证
	private String refreshToken;
	
	//用户标识
	private String openId;
	
	//用户唯一标识 
	private String unionid;
	
	//用户授权作用域（当scope=snsapi_base时，不弹出授权页面,直接跳转 只能获取到openId,当scope=snsapi=userinfo时弹出授权页面,获取用户信息）
	private String scope;

}
