package com.hengyu.log.interceptor;

import static com.hengyu.common.constant.CommonConstants.TOKEN;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.util.IpUtils;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.common.util.JsonUtils;
import com.hengyu.log.dao.LogInfoDAO;
import com.hengyu.log.entity.LogInfo;

import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Order(1)
@Aspect
@Component
public class WebLogAspect {
	@Autowired
	private JWTUtils jwtUtils;

	@Autowired
	private LogInfoDAO logInfoDAO;

	@Pointcut("execution(public * com.hengyu.*.web..*.*(..))")
	public void webLog() {
	}

	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		// 接收到请求，记录请求内容
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		String requestUri = request.getRequestURL().toString();
		String requestParams = JsonUtils.getObjectToJsonObject(request.getParameterMap());
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		String os = userAgent.getOperatingSystem().getName(); // 获取客户端操作系统
		String browser = userAgent.getBrowser().getName(); // 获取客户端浏览器
		Version version = userAgent.getBrowserVersion();
		String browserVersion = Objects.nonNull(version) ? version.getVersion() : "";
		String ip = IpUtils.getIpAddress(request);

		LogInfo.LogInfoBuilder logInfoBuilder = LogInfo.builder().requestUri(requestUri).ip(ip)
				.method(request.getMethod()).requestParams(requestParams).os(os).browser(browser)
				.browserVersion(browserVersion);

		List<String> ignoreUrls = new ArrayList<>();
		ignoreUrls.add("/admin/login");
		ignoreUrls.add("/resource/getAllPermissionInfo");
		if (ignoreUrls.stream().noneMatch(url -> requestUri.endsWith(url))) {
			String token = request.getHeader(TOKEN);
			if (Objects.nonNull(token)) {
				IJWTInfo info = jwtUtils.getInfoFromToken(token);
				if (Objects.nonNull(info)) {
					logInfoBuilder.companyId(info.getCompanyId()).operationId(info.getUserId());
				}
			}
		}
		LogInfo logInfo = logInfoBuilder.build();
		log.info("logInfo:{}", logInfo);
		logInfoDAO.insert(logInfo);
	}
}
