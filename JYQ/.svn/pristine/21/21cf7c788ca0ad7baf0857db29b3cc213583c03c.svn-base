package com.hengyu.system.service.impl;

import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.system.dao.JoinCompanyDAO;
import com.hengyu.system.entity.JoinCompany;
import com.hengyu.system.po.ApproveJoinCompanyPo;
import com.hengyu.system.service.JoinCompanyService;
import com.hengyu.system.vo.MessageVo;

/**
 * <p>
 * 申请加入企业表 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-12-04
 */
@Service
public class JoinCompanyServiceImpl extends ServiceImpl<JoinCompanyDAO, JoinCompany> implements JoinCompanyService {

	@Autowired
	private JoinCompanyDAO joinCompanyDAO;

	@Override
	public Page<MessageVo> queryMessagelist(Page<MessageVo> page, Integer status, Integer type, Integer companyId) {
		return page.setRecords(joinCompanyDAO.queryMessagelist(page, companyId, status));
	}

	@Override
	public JoinCompany queryDetail(Integer id) {
		//设置消息为已读
		JoinCompany joinCompany = new JoinCompany();
		joinCompany.setId(id);
		joinCompany.setStatus(1);
		updateById(joinCompany);
		return joinCompanyDAO.queryDetail(id);
	}

	@Override
	public String approvalJoinCompany(ApproveJoinCompanyPo approveJoinCompanyPo) {
		JoinCompany joinCompany = selectById(approveJoinCompanyPo.getId());
		if(Objects.isNull(joinCompany)) {
			return "查无此记录";
		}
		joinCompany.setApproveUser(approveJoinCompanyPo.getUserId());
		joinCompany.setApproveTime(new Date());
		joinCompany.setState(approveJoinCompanyPo.getState());
		//设置为已读
		joinCompany.setStatus(1);
		boolean flag = updateById(joinCompany);

		if(Objects.equals(approveJoinCompanyPo.getState(), 1)) {
			joinCompanyDAO.updateUser(joinCompany.getUserId(), joinCompany.getCompanyId(), approveJoinCompanyPo.getStoreId());
		}
		if(flag){
			return "审批成功";
		}
		return "审核失败";
	}

}
