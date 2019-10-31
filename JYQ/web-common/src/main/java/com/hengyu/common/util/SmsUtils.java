package com.hengyu.common.util;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public final class SmsUtils {
	private static String smsUrl = "http://v.juhe.cn/sms/send?mobile=%s&tpl_id=%s&tpl_value=%s&key=1572ea9255730f14ce3d5019a0fafbea";

	public Boolean sendSms(String mobile, String tplId, String tplValue) throws IOException {
		String json = Jsoup.connect(String.format(smsUrl, mobile, tplId, tplValue)).ignoreContentType(true).execute()
				.body();
		log.info("json:{}", json);
		if (Objects.equals("0", JsonUtils.getString(json, "error_code"))) {
			return true;
		}
		log.error("短信发送失败，原因：{}", JsonUtils.getString(json, "reason"));
		return false;
	}

	public String getRandomCode(int len) {
		Random rand = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			sb.append(rand.nextInt(10));
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		try {
			new SmsUtils().sendSms("18851815112", "103489", null);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}