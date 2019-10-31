package com.hengyu.system.web;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hengyu.common.entity.Weather;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.IpUtils;
import com.hengyu.common.util.WeatherUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/index")
public class IndexController {
	@Autowired
	private WeatherUtils weatherUtils;

	/**
	 * 首页天气
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping("weather")
	public RestResponse<Weather> weather(HttpServletRequest request) throws IOException {
		String ip = IpUtils.getIpAddress(request);
		log.info("ip:{}", ip);
		Weather weather = weatherUtils.getWeaherInfo(ip);
		log.info("weather:{}", weather);
		RestResponse<Weather> response = new RestResponse<>();
		if (Objects.isNull(weather)) {
			return response.rel(false).message("天气服务不可用");
		}
		return response.rel(true).data(weather);
	}
}
