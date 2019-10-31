package com.hengyu.report.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.hengyu.report.entity.Report;
import com.hengyu.report.vo.ReportVo;

/**
 * <p>
 * 中介报告表 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
public interface ReportService extends IService<Report> {
	ReportVo queryDetailById(Integer id);

	Page<ReportVo> queryListByType(Page<ReportVo> page, Integer companyId, Integer type);
}