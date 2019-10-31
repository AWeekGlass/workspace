package com.hengyu.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hengyu.common.jwt.IJWTInfo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

@Component
public class JWTUtils extends JWTHelper {
	@Value("${jwt.expire}")
	private int expire;

	@Value("${jwt.pri-key.path}")
	private String priKeyPath;

	@Value("${jwt.pub-key.path}")
	private String pubKeyPath;

	/**
	 * 密钥加密token
	 * 
	 * @since 2018年8月22日
	 * @author allnas
	 * @param info
	 * @throws Exception
	 */
	public String generateToken(IJWTInfo info) throws Exception {
		return generateToken(info, expire, priKeyPath);
	}

	/**
	 * 解析token
	 * 
	 * @since 2018年8月22日
	 * @author allnas
	 * @param token
	 * @throws Exception
	 */
	public IJWTInfo getInfoFromToken(String token) throws Exception {
		return getInfoFromToken(token, pubKeyPath);
	}

	public Jws<Claims> parserToken(String token) throws Exception {
		return parserToken(token, pubKeyPath);
	}
}
