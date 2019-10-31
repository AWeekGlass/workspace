package com.hengyu.gateway.filter;

import static com.hengyu.common.constant.CommonConstants.TOKEN;
import static com.hengyu.common.constant.CommonConstants.JWT_BLACK_SET;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;

import com.hengyu.common.enums.ExceptionEnum;
import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.BaseResponse;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.common.util.JsonUtils;
import com.hengyu.gateway.service.ResourceService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class LoginFilter implements GlobalFilter {
	@Value("${gate.ignore.startWith}")
	private String startWith;

	@Autowired
	private JWTUtils jwtUtils;

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private ZSetOperations<String, String> zSetOperations;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		final String requestUri = request.getPath().pathWithinApplication().value();
		log.info("requestUri:{}", requestUri);
		ServerHttpRequest.Builder mutate = request.mutate();
		// 不进行拦截的地址
		if (isStartWith(requestUri)) {
			ServerHttpRequest build = mutate.build();
			return chain.filter(exchange.mutate().request(build).build());
		}
		List<String> tokens = request.getHeaders().get(TOKEN);
		log.info("tokens:{}", tokens);

		if (CollectionUtils.isEmpty(tokens)) {
			return getVoidMono(exchange,
					new RestResponse<String>().rel(false).status(ExceptionEnum.NOT_LOGIN_ERROR_CODE.getCode())
							.message(ExceptionEnum.NOT_LOGIN_ERROR_CODE.getDescription()));
		}
		String token = tokens.get(0);

		// 黑名单
		Long result = zSetOperations.rank(JWT_BLACK_SET, token);
		log.info("result:{}", result);

		if (Objects.nonNull(result)) {
			return getVoidMono(exchange,
					new RestResponse<String>().rel(false).status(ExceptionEnum.NOT_LOGIN_ERROR_CODE.getCode())
							.message(ExceptionEnum.NOT_LOGIN_ERROR_CODE.getDescription()));
		}

		// 获取JWT信息
		IJWTInfo info = null;
		try {
			info = jwtUtils.getInfoFromToken(token);
		} catch (Exception e) {
			return getVoidMono(exchange,
					new RestResponse<String>().rel(false).status(ExceptionEnum.EXPIRED_JWT_ERROR_CODE.getCode())
							.message(ExceptionEnum.EXPIRED_JWT_ERROR_CODE.getDescription()));
		}
		log.info("info:{}", info);

//		List<String> resourceList = resourceService.queryPermissionByAdminId(info.getUserId());
//		log.info("resourceList:{}", resourceList);
//		if (CollectionUtils.isEmpty(resourceList) || resourceList.isEmpty()) {
//			return getVoidMono(exchange,
//					new RestResponse<String>().rel(false).status(ExceptionEnum.CLIENT_FORBIDDEN_ERROR_CODE.getCode())
//							.message(ExceptionEnum.CLIENT_FORBIDDEN_ERROR_CODE.getDescription()));
//		}
//
//		if(resourceList.stream().noneMatch(url -> requestUri.startsWith(url))) {
//			return getVoidMono(exchange,
//					new RestResponse<String>().rel(false).status(ExceptionEnum.CLIENT_FORBIDDEN_ERROR_CODE.getCode())
//							.message(ExceptionEnum.CLIENT_FORBIDDEN_ERROR_CODE.getDescription()));
//		}
		
		ServerHttpRequest build = mutate.build();

		return chain.filter(exchange.mutate().request(build).build());
	}

	/**
	 * URI是否以什么打头
	 *
	 * @param requestUri
	 * @return
	 */
	private boolean isStartWith(String requestUri) {
		boolean flag = false;
		for (String s : startWith.split(",")) {
			if (requestUri.startsWith(s)) {
				return true;
			}
		}
		return flag;
	}

	/**
	 * 网关抛异常
	 *
	 * @param body
	 */
	@NotNull
	private Mono<Void> getVoidMono(ServerWebExchange serverWebExchange, BaseResponse baseResponse) {
		ServerHttpResponse serverHttpResponse = serverWebExchange.getResponse();
		// 设置headers
		HttpHeaders httpHeaders = serverHttpResponse.getHeaders();
		httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
		httpHeaders.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
		serverHttpResponse.setStatusCode(HttpStatus.OK);
		byte[] bytes = JsonUtils.toJsonString(baseResponse).getBytes();
		DataBuffer buffer = serverHttpResponse.bufferFactory().wrap(bytes);
		return serverHttpResponse.writeWith(Flux.just(buffer));
	}
}
