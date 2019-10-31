package com.hengyu.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.hengyu.system.entity.JoinCompany;
import com.hengyu.system.po.ApproveJoinCompanyPo;
import com.hengyu.system.vo.MessageVo;

/**
 * <p>
 * 申请加入企业表 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-12-04
 */
public interface JoinCompanyService extends IService<JoinCompany> {

	/**
	 * 查询申请类消息列表
	 * @param page
	 * @param status
	 * @param type
	 * @param companyId
	 * @return
	 */
	Page<MessageVo> queryMessagelist(Page<MessageVo> page, Integer status, Integer type, Integer companyId);

	/**
	 * 查询详情
	 * @param id
	 * @return
	 */
	JoinCompany queryDetail(Integer id);
	
	/**
	 * 审批加入企业申请
	 * @param approveJoinCompanyPo
	 * @return
	 */
	String approvalJoinCompany(ApproveJoinCompanyPo approveJoinCompanyPo);
}
