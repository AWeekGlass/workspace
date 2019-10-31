package com.hengyu.common.util;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import com.hengyu.common.constant.CommonConstants;
import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.jwt.JWTInfo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JWTHelper {
	private static RsaKeyHelper rsaKeyHelper = new RsaKeyHelper();

	/**
	 * 密钥加密token
	 * 
	 * @since 2018年8月22日
	 * @author allnas
	 * @param info
	 * @return
	 * @throws Exception
	 */
	protected String generateToken(IJWTInfo info, int expire, String priKeyPath) throws Exception {
		JwtBuilder builder = Jwts.builder().setSubject(info.getUserName())
				.claim(CommonConstants.JWT_USER_ID, info.getUserId())
				.claim(CommonConstants.JWT_USERNAME, info.getUserName());
		if (Objects.nonNull(info.getCompanyId())) {
			builder.claim(CommonConstants.JWT_COMPANY_ID, info.getCompanyId());
		}
		if (Objects.nonNull(info.getCompanyName())) {
			builder.claim(CommonConstants.JWT_COMPANY_NAME, info.getCompanyName());
		}

		String compactJws = builder.setExpiration(Date.from(Instant.now().plusSeconds(expire)))
				.signWith(SignatureAlgorithm.RS256, rsaKeyHelper.getPrivateKey(priKeyPath)).compact();
		log.info("compactJws:{}", compactJws);
		return compactJws;
	}

	/**
	 * 解析token
	 * 
	 * @since 2018年8月22日
	 * @author allnas
	 * @param token
	 * @throws Exception
	 */
	protected Jws<Claims> parserToken(String token, String pubKeyPath) throws Exception {
		return Jwts.parser().setSigningKey(rsaKeyHelper.getPublicKey(pubKeyPath)).parseClaimsJws(token);
	}

	protected JWTInfo getInfoFromToken(String token, String pubKeyPath) throws Exception {
		Jws<Claims> claimsJws = parserToken(token, pubKeyPath);
		Claims body = claimsJws.getBody();
		JWTInfo jwtInfo = new JWTInfo();
		jwtInfo.setUserName(body.getSubject());
		jwtInfo.setId(Integer.parseInt(Optional.ofNullable(body.get(CommonConstants.JWT_USER_ID)).get().toString()));
		jwtInfo.setCompanyId(Objects.isNull(body.get(CommonConstants.JWT_COMPANY_ID)) ? -1
				: Integer.parseInt(body.get(CommonConstants.JWT_COMPANY_ID).toString()));
		jwtInfo.setCompanyName(Objects.isNull(body.get(CommonConstants.JWT_COMPANY_NAME)) ? ""
				: body.get(CommonConstants.JWT_COMPANY_NAME).toString());
		return jwtInfo;
	}
}
