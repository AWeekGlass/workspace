package com.hengyu.contract.service.impl;

import com.hengyu.contract.entity.ApprovalRecord;
import com.hengyu.contract.dao.ApprovalRecordDAO;
import com.hengyu.contract.service.ApprovalRecordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 审批记录表 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-09-21
 */
@Service
public class ApprovalRecordServiceImpl extends ServiceImpl<ApprovalRecordDAO, ApprovalRecord> implements ApprovalRecordService {

	@Autowired
	private ApprovalRecordDAO recordDAO;
	
	@Override
	public List<ApprovalRecord> getList(Integer approvalId) {
		return recordDAO.getList(approvalId);
	}

}
