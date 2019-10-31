package com.hengyu.common.util;

import java.util.Objects;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

public class CookieUtils {
	/**
	 * 存放cookie
	 * 
	 * @param response
	 * @param token
	 * @param key
	 */
	public static void addCookie(HttpServletResponse response, String token, String key) {
		Cookie cookie = new Cookie(key, token);
		cookie.setPath("/");
		cookie.setMaxAge(-1);
		response.addCookie(cookie);
	}

	/**
	 * 清除cookie
	 * 
	 * @param request
	 * @param response
	 * @param key
	 */
	public static void cleanCookie(HttpServletRequest request, HttpServletResponse response, String key) {
		Cookie[] c = request.getCookies();
		if (Objects.nonNull(c)) {
			for (Cookie cookie : c) {
				if (key.equals(cookie.getName())) {
					cookie.setMaxAge(0);
					cookie.setValue(null);
					cookie.setPath("/");
					response.addCookie(cookie);
					break;
				}
			}
		}
	}

	/**
	 * 获取cookie value
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getCookie(HttpServletRequest request, String key) {
		Cookie[] c = request.getCookies();
		if (StringUtils.isEmpty(c)) {
			return null;
		}
		for (Cookie cookie : c) {
			if (key.equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
	}
}
