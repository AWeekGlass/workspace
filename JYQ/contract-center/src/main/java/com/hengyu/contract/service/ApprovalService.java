package com.hengyu.contract.service;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.hengyu.contract.entity.Approval;
import com.hengyu.contract.po.SearchApprovalPo;

/**
 * <p>
 * 审批记录表 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-09-19
 */
public interface ApprovalService extends IService<Approval> {
	
	/**
	 * 新增审批记录
	 * @param approval
	 * @return
	 */
	boolean save(Approval approval);
	
	/**
	 * 根据合同id查询审批记录
	 * @param contractId
	 * @return
	 */
	Page<Approval> getRecord(Page<Approval> page,SearchApprovalPo po);
	
	/**
	 * 根据id查询评审记录
	 * @param id
	 * @return
	 */
	Approval getById(Integer id);
	
	/**
	 * 根据id删除审批
	 * @param id
	 * @return
	 */
	boolean delete(Integer id);
	
	/**
	 * 更新审批
	 * @param approval
	 * @return
	 */
	boolean update(Approval approval,MultipartFile[] files);
	
	/**
	 * 更新审状态(批准/驳回/撤销/转交)
	 * @param approval
	 * @return
	 */
	boolean updateState(Approval approval);
	
	/**
	 * 查询用户手机号
	 * @param userId
	 * @return
	 */
	String getPhone(Integer userId);

}
