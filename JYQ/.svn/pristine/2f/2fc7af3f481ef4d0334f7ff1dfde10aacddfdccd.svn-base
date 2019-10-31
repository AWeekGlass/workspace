package com.hengyu.report.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hengyu.report.entity.Report;
import com.hengyu.report.vo.ReportVo;

/**
 * <p>
 * 中介报告表 Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
public interface ReportDAO extends BaseMapper<Report> {
	ReportVo queryDetailById(Integer id);

	List<ReportVo> queryListByType(Pagination pagination, @Param("companyId")Integer companyId,@Param("type") Integer type);
}