package com.hengyu.user.service;

import com.hengyu.user.entity.ChangePhone;
import com.hengyu.user.po.ApprChaPhonePo;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 更换手机号记录 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-11-30
 */
public interface ChangePhoneService extends IService<ChangePhone> {
	
	String approveChangePhone(ApprChaPhonePo apprChaPhonePo);

}
