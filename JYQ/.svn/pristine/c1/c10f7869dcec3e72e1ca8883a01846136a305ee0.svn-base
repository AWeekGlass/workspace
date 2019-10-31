package com.hengyu.contract.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.common.util.FileUtils;
import com.hengyu.contract.dao.HtenclosureDAO;
import com.hengyu.contract.entity.Htenclosure;
import com.hengyu.contract.service.HtenclosureService;

/**
 * <p>
 * 中介合同上传附件表 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-08-31
 */
@Service
public class HtenclosureServiceImpl extends ServiceImpl<HtenclosureDAO, Htenclosure> implements HtenclosureService {

	@Autowired
	private FileUtils fileUtils;
	
	@Override
	public boolean save(Htenclosure htenclosure) {
		return insert(htenclosure);
	}

	@Override
	public boolean delete(String id) {
		if(!ObjectUtils.isEmpty(id) && !id.isEmpty()){
			List<String> ids = Arrays.asList(id.split(","));
			if(ids!=null && !ids.isEmpty()){
				for (String id2 : ids) {
					//删除ftp文件
					Htenclosure htenclosure = selectById(id2);
					if(Objects.nonNull(htenclosure.getHtpath())){
						fileUtils.deleteFTPFile(htenclosure.getHtpath());
					}
				}
				return deleteBatchIds(ids);
			}
		}
		return false;
	}

	@Override
	public boolean update(Htenclosure htenclosure) {
		return updateById(htenclosure);
	}

	@Override
	public List<Htenclosure> queryList(Htenclosure htenclosure) {
		List<Htenclosure> list = selectList(new EntityWrapper<Htenclosure>(htenclosure));
		return list;
	}

	@Override
	public Htenclosure queryById(Integer id) {
		return selectById(id);
	}

}
