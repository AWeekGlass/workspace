package com.hengyu.system.service.impl;

import com.hengyu.system.entity.Apply;
import com.hengyu.system.entity.Company;
import com.hengyu.system.po.ApplyPo;
import com.hengyu.system.dao.ApplyDAO;
import com.hengyu.system.service.ApplyService;
import com.hengyu.system.service.CompanyService;
import com.hengyu.system.vo.ApplyVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.Calendar;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 公司申请表 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-10-16
 */
@Service
public class ApplyServiceImpl extends ServiceImpl<ApplyDAO, Apply> implements ApplyService {

	@Autowired
	private ApplyDAO applyDAO;
	
	@Autowired
	private CompanyService companyService;

	@Override
	public Page<ApplyVo> queryList(Page<ApplyVo> page, Integer state, String searchKey, Integer companyId) {
		page.setRecords(applyDAO.queryList(page, state, searchKey, companyId));
		return page;
	}

	@Override
	public String approvalApply(ApplyPo applyPo) {
		Apply apply = selectById(applyPo.getId());
		if(Objects.nonNull(apply)){
			apply.setState(applyPo.getState());
			//设置公司信息
			Company company = new Company();
			company.setId(applyPo.getCompanyId());
			//通过
			if(Objects.equals(applyPo.getState(), 2)){
				company.setStatus(2);
				// 获取N天之后的日期
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DAY_OF_MONTH, applyPo.getDay());
				company.setExpireDate(calendar.getTime());
				//设置公司负责人
				Integer count = applyDAO.setAdminId(apply.getUserId(), applyPo.getCompanyId());
				Integer count_ = applyDAO.setCompanyId(apply.getUserId(), applyPo.getCompanyId());
				if(count==1 && count_ ==1 ){
					boolean flag = companyService.update(company);
					boolean flag_ = updateById(apply);
					if(flag && flag_){
						return "审批成功";
					}
				}
				return "审批失败";
			}else {
				company.setStatus(3);
				apply.setReason(applyPo.getReason());
				return "审批成功";
			}
		}
		return "未查询到审核信息";
	}

	@Override
	public ApplyVo queryDetail(Integer id) {
		return applyDAO.queryDetail(id);
	}

}
