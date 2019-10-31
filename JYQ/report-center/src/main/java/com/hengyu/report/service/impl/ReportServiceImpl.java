package com.hengyu.report.service.impl;

import com.hengyu.report.entity.Report;
import com.hengyu.report.dao.ReportDAO;
import com.hengyu.report.service.ReportService;
import com.hengyu.report.vo.ReportVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 中介报告表 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
@Service
public class ReportServiceImpl extends ServiceImpl<ReportDAO, Report> implements ReportService {
	@Autowired
	private ReportDAO reportDAO;

	@Override
	public ReportVo queryDetailById(Integer id) {
		return reportDAO.queryDetailById(id);
	}

	@Override
	public Page<ReportVo> queryListByType(Page<ReportVo> page, Integer companyId, Integer type) {
		return page.setRecords(reportDAO.queryListByType(page, companyId, type));
	}

}
