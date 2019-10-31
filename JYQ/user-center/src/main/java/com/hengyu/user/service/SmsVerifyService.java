package com.hengyu.user.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.baomidou.mybatisplus.service.IService;
import com.hengyu.user.entity.SmsVerify;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
public interface SmsVerifyService extends IService<SmsVerify> {
	String verifySmsCode(SmsVerify smsVerify, String smsCode);

	Boolean save(SmsVerify smsVerify) throws UnsupportedEncodingException, IOException;
}
