package com.hengyu.system.service.impl;

import com.hengyu.system.entity.Notice;
import com.hengyu.system.dao.NoticeDAO;
import com.hengyu.system.service.NoticeService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-12-10
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeDAO, Notice> implements NoticeService {

	@Autowired
	private NoticeDAO noticeDAO;
	
	@Override
	public Page<Notice> queryList(Page<Notice> page, Integer companyId) {
		return page.setRecords(noticeDAO.queryList(page, companyId));
	}

}
