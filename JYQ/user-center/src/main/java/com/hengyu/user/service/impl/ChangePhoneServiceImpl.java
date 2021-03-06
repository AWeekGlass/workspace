package com.hengyu.user.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.user.dao.ChangePhoneDAO;
import com.hengyu.user.entity.Admin;
import com.hengyu.user.entity.ChangePhone;
import com.hengyu.user.po.ApprChaPhonePo;
import com.hengyu.user.service.AdminService;
import com.hengyu.user.service.ChangePhoneService;

/**
 * <p>
 * 更换手机号记录 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-11-30
 */
@Service
public class ChangePhoneServiceImpl extends ServiceImpl<ChangePhoneDAO, ChangePhone> implements ChangePhoneService {

	@Autowired
	private AdminService adminService;

	@Override
	public String approveChangePhone(ApprChaPhonePo apprChaPhonePo) {
		ChangePhone changePhone = selectById(apprChaPhonePo.getId());
		if (Objects.nonNull(changePhone)) {
			changePhone.setState(apprChaPhonePo.getState());
			// 通过
			if (apprChaPhonePo.getState() == 1) {
				//更新手机号
				Admin admin = adminService.selectById(changePhone.getUserId());
				admin.setTelephone(changePhone.getNewPhone());
				adminService.updateById(admin);
			}
			//设置为已读
			changePhone.setStatus(1);
			updateById(changePhone);
			return "审核成功";
		}
		return "查无此记录";
	}

}
