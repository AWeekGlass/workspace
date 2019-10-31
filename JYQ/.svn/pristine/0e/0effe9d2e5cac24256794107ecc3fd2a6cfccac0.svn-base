package com.hengyu.user.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.common.util.SmsUtils;
import com.hengyu.user.dao.SmsVerifyDAO;
import com.hengyu.user.entity.SmsVerify;
import com.hengyu.user.service.SmsVerifyService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
@Service
public class SmsVerifyServiceImpl extends ServiceImpl<SmsVerifyDAO, SmsVerify> implements SmsVerifyService {
	@Autowired
	private SmsVerifyDAO smsVerifyDAO;

	@Autowired
	private SmsUtils smsUtils;

	@Override
	public String verifySmsCode(SmsVerify smsVerify, String smsCode) {
		smsVerify = smsVerifyDAO.verifySmsCode(smsVerify);
		if (Objects.isNull(smsVerify)) {
			return "手机号不存在";
		}

		if (Objects.equals(smsVerify.getSmsCode(), smsCode)) {
			smsVerify.setIsValid(0);
			updateById(smsVerify);
			return "";
		} else {
			return "验证码不正确";
		}
	}

	@Override
	public Boolean save(SmsVerify smsVerify) throws UnsupportedEncodingException, IOException {
		String verifyCode = smsUtils.getRandomCode(4);
		smsVerify.setSmsCode(verifyCode);
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, 20);

		smsVerify.setIsValid(1);
		smsVerify.setValidLastTime(cal.getTime());
		insert(smsVerify);
		return smsUtils.sendSms(smsVerify.getPhone(), "88402", URLEncoder.encode("#code#=" + verifyCode, "UTF-8"));
	}
}
