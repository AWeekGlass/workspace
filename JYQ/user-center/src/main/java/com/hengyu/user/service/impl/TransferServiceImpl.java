package com.hengyu.user.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.user.dao.TransferDAO;
import com.hengyu.user.entity.Admin;
import com.hengyu.user.entity.Transfer;
import com.hengyu.user.service.AdminService;
import com.hengyu.user.service.TransferService;

/**
 * <p>
 * 人员调动 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-09-29
 */
@Service
public class TransferServiceImpl extends ServiceImpl<TransferDAO, Transfer> implements TransferService {

	@Autowired
	private TransferDAO transferDAO;
	
	@Autowired
	private AdminService adminService;
	
	@Override
	public boolean add(Transfer transfer) {
		boolean flag = insert(transfer);
		if(flag){
			Admin admin = new Admin();
			admin.setId(transfer.getUserId());
			admin.setRoleId(transfer.getPosition());
			admin.setStoreId(transfer.getDivision());
			adminService.updateById(admin);
		}
		return flag;
	}

	@Override
	public boolean delete(String id) {
		if(Objects.nonNull(id)){
			List<String> ids = Arrays.asList(id.split(","));
			boolean flag = deleteBatchIds(ids);
			return flag;
		}
		return false;
	}

	@Override
	public List<Transfer> getList(Integer userId) {
		List<Transfer> list = transferDAO.getList(userId);
		return list;
	}

}
