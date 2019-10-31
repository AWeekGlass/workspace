package com.hengyu.contract.service.impl;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.contract.dao.ContractNumDAO;
import com.hengyu.contract.dao.ContractNumberDAO;
import com.hengyu.contract.entity.ContractNum;
import com.hengyu.contract.entity.ContractNumber;
import com.hengyu.contract.service.ContractNumberService;

/**
 * <p>
 * 中介合同编号表 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
@Service
public class ContractNumberServiceImpl extends ServiceImpl<ContractNumberDAO, ContractNumber> implements ContractNumberService {

	@Autowired
	private ContractNumberDAO contractNumberDAO;
	
	@Autowired
	private ContractNumDAO contractNumDAO;
	
	@Override
	public boolean save(ContractNumber contractNumber) {
		return insert(contractNumber);
	}

	@Override
	public boolean delete(String id) {
		if(!ObjectUtils.isEmpty(id) && !id.isEmpty()){
			List<String> ids = Arrays.asList(id.split(","));
			if(ids!=null && !ids.isEmpty()){
				return deleteBatchIds(ids);
			}
		}
		return false;
	}

	@Override
	public boolean update(ContractNumber contractNumber) {
		return updateById(contractNumber);
	}

	@Override
	public ContractNumber queryById(String id) {
		return selectById(id);
	}

	@Override
	public List<ContractNumber> queryList(ContractNumber contractNumber) {
		return selectList(new EntityWrapper<ContractNumber>(contractNumber));
	}

	@Override
	public String getHtNumberByMax(ContractNumber contractNumber) {
		String htnumber = "当前试用状态：无合同编号";
		Integer type = contractNumber.getType();
		//查询公司合同编号是否启用
		Integer state = contractNumberDAO.getHtNumberState(contractNumber.getCompanyId());
		if(state == 1){
			//根据公司id查询公司编号配置
			ContractNum contractNum = contractNumDAO.selectList(new EntityWrapper<ContractNum>().eq("company_id",contractNumber.getCompanyId())).get(0);
			if(Objects.isNull(contractNum)){
				htnumber = "尚未设置编号";
			}else {
				String numberQ = "";
				String numberS = "";
				//获取合同最大编号
				contractNumber = contractNumberDAO.getHtNumberByMax(contractNumber);
				if (Objects.nonNull(contractNumber) && contractNumber != null) {
					numberQ = contractNumber.getNumber().trim().substring(0, contractNumber.getNumber().trim().length() - 5);
					//获取前缀
					if (type==1) {
						numberS = contractNum.getRentingNum().trim();
					}
					if (type==2) {
						numberS = contractNum.getNewHouseNum().trim();
					}
					if (type==3) {
						numberS = contractNum.getStockRoomNum().trim();
					}
					if (numberQ.equals(numberS)) {//如果起始编号相同，则编号+1
						int a = Integer.parseInt(contractNumber.getNumber().trim().substring(
								contractNumber.getNumber().trim().length() - 5, contractNumber.getNumber().trim().length()));
						a++;
						DecimalFormat df = new DecimalFormat("00000");
						String str2 = df.format(a);
						htnumber = numberQ + str2;
					} else {//如果不同则重新编号
						if (type==1) {
							htnumber = contractNum.getRentingNum().trim() + contractNum.getRentingSta();
						}
						if (type==2) {
							htnumber = contractNum.getNewHouseNum().trim() + contractNum.getNewHouseSta();
						}
						if (type==3) {
							htnumber = contractNum.getStockRoomNum().trim() + contractNum.getStockRoomSta();
						}
					}
				} else {//如果不存在最大编号则，则生成第一个编号
					if (type==1) {
						htnumber = contractNum.getRentingNum().trim() + contractNum.getRentingSta();
					}
					if (type==2) {
						htnumber = contractNum.getNewHouseNum().trim() + contractNum.getNewHouseSta();
					}
					if (type==3) {
						htnumber = contractNum.getStockRoomNum().trim() + contractNum.getStockRoomSta();
					}
				}
			}
		}
		//将新生成的编码新增到数据库中
		ContractNumber number = new ContractNumber();
		number.setCompanyId(contractNumber.getCompanyId());
		number.setStatus(0);
		number.setType(contractNumber.getType());
		number.setNumber(htnumber);
		contractNumberDAO.insert(number);
		return htnumber;
	}

	@Override
	public boolean updateStatus(ContractNumber contractNumber) {
		Integer index = contractNumberDAO.updateStatus(contractNumber);
		if(index==1){
			return true;
		}
		return false;
	}
}
