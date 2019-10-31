package com.hengyu.user.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hengyu.user.entity.SmsVerify;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
public interface SmsVerifyDAO extends BaseMapper<SmsVerify> {

	/**
	 * 验证短信合法性
	 * 
	 * @param smsVerify
	 * @return
	 */
	SmsVerify verifySmsCode(SmsVerify smsVerify);
}
