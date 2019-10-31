package com.hengyu.common.util;

import java.util.List;

import lombok.Data;

/**
 * 通过网页授权获取用户信息
 * @author Administrator
 *
 */
@Data
public class SNSUserInfo {
	
	//用户标识
	private String openId;
	
	//用户唯一标识 
	private String unionid;
	
	//用户昵称
	private String nickname;
	
	//性别(1 是男性 2 是女性  0 是未知)
	private int sex;
	
	//国家
	private String country;
	
	//省份
	private String province;
	
	//城市
	private String city;
	
	//头像
	private String headImgUrl;
	
	//用户特权信息
	private List<String> privilegeList;

}
