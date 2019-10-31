package com.hengyu.common.util;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.hengyu.common.entity.Weather;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WeatherUtils {
	private String weatherUrl = "http://api.map.baidu.com/telematics/v3/weather?location=%s&output=json&ak=12dc2d45fb7576d8be170652857261d4";

	private String cityNameUrl = "http://api.map.baidu.com/location/ip?ip=%s&ak=12dc2d45fb7576d8be170652857261d4";

	private String getCityName(String ip) throws IOException {
		String json = Jsoup.connect(String.format(cityNameUrl, ip)).ignoreContentType(true).execute().body();
		log.info("json:{}", json);

		Map<String, Object> ipInfo = JsonUtils.getMap4Json(json);
		if (Objects.equals(ipInfo.get("status"), 0)) {
			String data = ipInfo.get("content").toString();
			return JSON.parseObject(data).getJSONObject("address_detail").getString("city");
		}
		return "";
	}

	public Weather getWeaherInfo(String ip) throws IOException {
		String cityName = getCityName(ip);
		if (StringUtils.isEmpty(cityName)) {
			return null;
		}

		String json = Jsoup.connect(String.format(weatherUrl, cityName)).execute().body();
		log.info("json:{}", json);

		if (Objects.equals(JsonUtils.getString(json, "status"), "success")) {
			String results = JsonUtils.getjsonArray(json, "results").getString(0);
			log.info("results:{}", results);
			
			Weather weather = JsonUtils.getObject4JsonString(results, Weather.class);
			log.info("weather:{}", weather);
			return weather;
		}
		return null;
	}
}