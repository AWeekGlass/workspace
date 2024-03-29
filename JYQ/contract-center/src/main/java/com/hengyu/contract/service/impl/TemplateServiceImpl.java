package com.hengyu.contract.service.impl;

import static com.hengyu.common.constant.CommonConstants.PREFIX_CONTRACT_TEMPLATE;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.common.util.FileUtils;
import com.hengyu.common.util.RedisUtils;
import com.hengyu.contract.dao.TemplateDAO;
import com.hengyu.contract.entity.Template;
import com.hengyu.contract.po.QueryTemplatePo;
import com.hengyu.contract.service.TemplateService;

/**
 * <p>
 * 中介模板表 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-08-29
 */
@Service
public class TemplateServiceImpl extends ServiceImpl<TemplateDAO, Template> implements TemplateService {

	@Autowired
	private TemplateDAO templateDAO;
	
	@Autowired
	private FileUtils fileUtils;
	
	@Autowired
	private RedisUtils<String> redisUtils;
	
	@Override
	public boolean save(Template template) {
		template.setDelFlag(0);
		
		
		return insert(template);
	}

	@Override
	public boolean delete(String id) {
		if(!ObjectUtils.isEmpty(id) && !id.isEmpty()){
			List<String> ids = Arrays.asList(id.split(","));
			//循环删除ftp文件
			if(ids!=null && !ids.isEmpty()){
				for (String id_ : ids) {
					//删除ftp文件
					Template temp = selectById(id_);
					//删除ftp文件
					fileUtils.deleteFTPFile(temp.getAddress());
					//删除redis缓存
					redisUtils.deleteKey(PREFIX_CONTRACT_TEMPLATE+id_);
				}
				return deleteBatchIds(ids);
			}
		}
		return false;
	}

	@Override
	public boolean update(Template template) {
		return updateById(template);
	}

	@Override
	public Template queryById(Integer id) throws IOException {
		Template template = templateDAO.queryById(id);
		String address = template.getAddress();
		if(Objects.nonNull(address)){
			address = redisUtils.getValue(PREFIX_CONTRACT_TEMPLATE+template.getId().toString());
			if(Objects.isNull(address)){
				String fileName = template.getAddress().substring(template.getAddress().lastIndexOf("/")+1);
				String ftpPath = template.getAddress().substring(0,template.getAddress().lastIndexOf("/"));
				address = fileUtils.getFileByName(ftpPath, fileName);
				redisUtils.setValue(PREFIX_CONTRACT_TEMPLATE+template.getId().toString(), address);
			}
			template.setAddress(address);
		}
		return template;
	}

	@Override
	public Page<Template> queryList(Page<Template> page, QueryTemplatePo queryTemplatePo) throws IOException {
		List<Template> list = templateDAO.queryList(page,queryTemplatePo);
		//根据路径读取文件内容
		if(Objects.nonNull(list) && !list.isEmpty()){
			for (Template template : list) {
				String address = template.getAddress();
				if(Objects.nonNull(address)){
					address = redisUtils.getValue(PREFIX_CONTRACT_TEMPLATE+template.getId());
					if(Objects.isNull(address)){
						String fileName = template.getAddress().substring(template.getAddress().lastIndexOf("/")+1);
						String ftpPath = template.getAddress().substring(0,template.getAddress().lastIndexOf("/"));
						address = fileUtils.getFileByName(ftpPath, fileName);
						redisUtils.setValue(PREFIX_CONTRACT_TEMPLATE+template.getId().toString(), address);
					}
					template.setAddress(address);
				}
			}
		}
		return page.setRecords(list);
	}

	@Override
	public Integer queryAreaId(Integer id) {
		return templateDAO.queryAreaId(id);
	}

	@Override
	public String querySysTempate(Integer type,Integer companyId) throws IOException {
		//根据文件地址读取文件内容
		Template template = templateDAO.querySysTempate(type,companyId);
		String address = template.getAddress();
     	if(Objects.nonNull(address)){
			address = redisUtils.getValue(PREFIX_CONTRACT_TEMPLATE+template.getId());
			if(Objects.isNull(address)){
				String fileName = template.getAddress().substring(template.getAddress().lastIndexOf("/")+1);
				String ftpPath = template.getAddress().substring(0,template.getAddress().lastIndexOf("/"));
				address = fileUtils.getFileByName(ftpPath, fileName);
				redisUtils.setValue(PREFIX_CONTRACT_TEMPLATE+template.getId().toString(), address);
			}
		}
		return address;
	}
	
}
