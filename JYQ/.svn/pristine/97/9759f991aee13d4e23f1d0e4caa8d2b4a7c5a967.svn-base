package com.hengyu.common.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.junit.Test;

import com.hengyu.common.util.FTPUtils;
import com.hengyu.common.util.SmsUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonTest {
	@Test
	public void sms() throws UnsupportedEncodingException, IOException {
		log.info("{}", new SmsUtils().sendSms("18914791665", "88402", URLEncoder.encode("#code#=1234", "UTF-8")));
	}

	@Test
	public void ftp() {
		// System.out.println(new FTPUtils().uploadLocalFile("doc",
		// "/Users/zhaojin/Documents/捷易签/捷易签管理系统项目整体计划.xlsx",
		// "捷易签管理系统项目整体计划.xlsx"));
		// System.out.println(new
		// FTPUtils().downloadFile("","f2e9e3f37a2c6b1d024d70783bd738e2.jpg","C:/Users/liuhainan/Desktop/uploadFile"));
		// System.out.println(new
		// FTPUtils().deleteFile("f2e9e3f37a2c6b1d024d70783bd738e2.jpg"));
		byte[] bits = new FTPUtils().getFileBytesByName("contract/file/", "2018090314445383590.html");
		System.out.println(new String(bits));
	}
}
