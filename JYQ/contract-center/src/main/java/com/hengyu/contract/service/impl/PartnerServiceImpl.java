package com.hengyu.contract.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.common.util.FileUtils;
import com.hengyu.contract.dao.PartnerDAO;
import com.hengyu.contract.entity.Partner;
import com.hengyu.contract.po.QueryPartnerPo;
import com.hengyu.contract.service.PartnerService;

/**
 * <p>
 * 中介签约附件表 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-08-29
 */
@Service
public class PartnerServiceImpl extends ServiceImpl<PartnerDAO, Partner> implements PartnerService {

	@Autowired
	private PartnerDAO partnerDAO;
	
	@Autowired
	private FileUtils fileUtils;
	
	@Override
	public boolean save(Partner partner) {
		partner.setUploadTime(new Date());
		return insert(partner);
	}

	@Override
	public boolean update(Partner partner) {
		partner.setUploadTime(new Date());
		return updateById(partner);
	}

	@Override
	public boolean delete(String id) {
		if(!ObjectUtils.isEmpty(id) && !id.isEmpty()){
			List<String> ids = Arrays.asList(id.split(","));
			if(ids!=null && !ids.isEmpty()){
				ids.forEach( id2 ->{
					//删除ftp文件
					Partner partner = selectById(id2);
					fileUtils.deleteFTPFile(partner.getAttachmentAddress());
				});
				return deleteBatchIds(ids);
			}
		}
		return false;
	}

	@Override
	public Partner queryById(Integer id) {
		return partnerDAO.queryById(id);
	}

	@Override
	public Page<Partner> queryList(Page<Partner> page, QueryPartnerPo po) {
		return page.setRecords(partnerDAO.queryList(page,po));
	}

}
