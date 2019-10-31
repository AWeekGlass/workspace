package com.hengyu.common.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Weather {
	private List<WeatherData> weatherData;
	private String pm25;
	private List<Index> index;
	private String currentCity;
}

@Getter
@Setter
@ToString
class WeatherData {
	// 日期
	private String date;
	// 白天天气
	private String dayPictureUrl;
	// 晚上天气
	private String nightPictureUrl;
	// 天气
	private String weather;
	// 温度
	private String temperature;
	// 风力
	private String wind;
}

@Getter
@Setter
@ToString
class Index {
	private String des;
	private String zs;
	private String title;
	private String tipt;
}