package com.hengyu.user.web;

import com.hengyu.common.util.AccessToken;
import com.hengyu.common.util.CommonUtil;

import net.sf.json.JSONObject;

public class Test {
	public final static String access_token_url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	public final static String oauth2_1_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	public final static String oauth2_2_url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	public final static String get_userInfo_url="https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
	public final static String get_hangye_url = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=ACCESS_TOKEN";
	//上面那五个，不用改。把下边的appid和appsecret改了就行
	public final static String appid="wxe793d3255d7c3af6";
	public final static String appSecret="e15fd11c286e6acd990c068e8704c8b0";

	public static void main(String[] args) {
		String url="https://open.weixin.qq.com/connect/qrconnect?appid=wxa601cf26a6886324&redirect_uri=http://www.jieyiqian.com&response_type=code&scope=snsapi_login&state=STATE#wechat_redirect";
		AccessToken accesstoken=new AccessToken();
		//得到json对象
		JSONObject jsonObject = CommonUtil.httpsRequest(url, "GET", null);
		
		//将得到的json对象的属性值，存到accesstoken中
		accesstoken.setToken(jsonObject.getString("access_token"));
		accesstoken.setExpiresIn(jsonObject.getInt("expires_in"));
		
		String requestUrl1 = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=ACCESS_TOKEN";
		
		//将更新后的access_token,替换上去
		requestUrl1 = requestUrl1.replace("ACCESS_TOKEN",accesstoken.getToken());
		
		
		String jsonMsg = "{\"action\":\"long2short\",\"long_url\":\"%s\"}";
        //格式化url
        String.format(jsonMsg, url);
        JSONObject jsonobject = CommonUtil.httpsRequest(requestUrl1, "POST",String.format(jsonMsg, url));
        String shorturl = jsonobject.getString("short_url");
        System.out.println(shorturl);
	}
}
