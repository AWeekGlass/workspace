package com.hengyu.contract.service.impl;

import static com.hengyu.common.constant.CommonConstants.PREFIX_CONTRACT_CONTRACT;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.common.util.FileUtils;
import com.hengyu.common.util.RedisUtils;
import com.hengyu.contract.dao.ContractDAO;
import com.hengyu.contract.entity.Buyer;
import com.hengyu.contract.entity.Contract;
import com.hengyu.contract.entity.Seller;
import com.hengyu.contract.po.AddcontractPo;
import com.hengyu.contract.po.SearchPo;
import com.hengyu.contract.service.ApprovalService;
import com.hengyu.contract.service.ContractService;

/**
 * <p>
 * 中介合同表 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-08-27
 */
@Service
public class ContractServiceImpl extends ServiceImpl<ContractDAO, Contract> implements ContractService {

	/*
	 * 卖家服务实现类
	 */
	@Autowired
	SellerServiceImpl sellerServiceImpl;

	@Autowired
	private FileUtils fileUtils;
	
	@Autowired
	ApprovalService approvalService;
	
	@Autowired
	private RedisUtils<String> redisUtils;

	/*
	 * 买家服务实现类
	 */
	@Autowired
	BuyerServiceImpl buyerServiceImpl;

	@Autowired
	ContractDAO contractDAO;

	/**
	 * 新增合同
	 */
	@Override
	public boolean save(AddcontractPo addcontractPo) {
		Buyer buyer = addcontractPo.getBuyer();
		Seller seller = addcontractPo.getSeller();
		Contract contract = addcontractPo.getContract();
		boolean flag1 = false;
		boolean flag2 = false;
		if(Objects.nonNull(buyer)){
			flag1 = sellerServiceImpl.save(seller);
		}
		if(Objects.nonNull(seller)){
			flag2 = buyerServiceImpl.save(buyer);
		}
		
		if (flag1 && flag2) {
			// 新增买受人
			contract.setMsrId(buyer.getId().toString());
			// 新增出卖人表
			contract.setCmrId(seller.getId().toString());
			contract.setApproval(1);
			return insert(contract);
		}
		return false;
	}

	/**
	 * 根据条件查询最新合同
	 */
	@Override
	public Contract getContractByMax(Contract contract) {
		Contract contract1 = contractDAO.getContractByMax(contract);
		return contract1;
	}

	/**
	 * 更新合同
	 */
	@Override
	public boolean updateContract(Contract contract) {
		// 如果该编号已被使用则更新，没有则新增
		List<Contract> list = contractDAO.selectList(
				new EntityWrapper<Contract>().eq("del_flag", 0).eq("contract_number", contract.getContractNumber()));
		if (Objects.nonNull(list) && list.size() > 0) {
			contract.setId(list.get(0).getId());
		}
		return insertOrUpdate(contract);
	}

	/**
	 * 查询合同列表
	 * @throws IOException 
	 */
	@Override
	public Page<Contract> selectContractList(Page<Contract> page, SearchPo searchPo) throws IOException {
		
		//默认查询通过的合同
		if(Objects.isNull(searchPo.getApproval())){
			searchPo.setApproval(1);
		}
		List<Contract> list = contractDAO.selectContractList(page, searchPo);
		// 根据文件地址读取文件内容
		if (Objects.nonNull(list)) {
			for (Contract contract : list) {
				String path = contract.getPath();
				if (Objects.nonNull(path)) {
					path = redisUtils.getValue(PREFIX_CONTRACT_CONTRACT+contract.getId());
					contract.setDownloadPath(contract.getPath());
					contract.setPath(path);
				}
			}
		}
		return page.setRecords(list);
	}

	/**
	 * 根据id查询合同
	 * @throws IOException 
	 */
	@Override
	public Contract queryById(Integer id) throws IOException {
		Contract contract = contractDAO.selectByID(id);
		String path = contract.getPath();
		// 将文件以流的形式读出
		if (Objects.nonNull(path) && path != null) {
			path = redisUtils.getValue(PREFIX_CONTRACT_CONTRACT+id);
			contract.setPath(path);
		}
		return contract;
	}

	/**
	 * 根据id删除合同
	 */
	@Override
	public boolean deleteByID(String id) {
		if (!ObjectUtils.isEmpty(id) && !id.isEmpty()) {
			List<String> ids = Arrays.asList(id.split(","));
			if (ids != null && !ids.isEmpty()) {
				// 删除文件
				for (String id_ : ids) {
					//删除审批记录
					approvalService.delete(Integer.valueOf(id_));
					Contract contract = selectById(id_);
					fileUtils.deleteFTPFile(contract.getPath());
					redisUtils.deleteKey(PREFIX_CONTRACT_CONTRACT+id_);
				}
				return deleteBatchIds(ids);
			}
		}
		return false;
	}

}