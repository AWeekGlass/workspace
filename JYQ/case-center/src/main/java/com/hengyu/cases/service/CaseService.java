package com.hengyu.cases.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.hengyu.cases.entity.Case;
import com.hengyu.cases.entity.Question;
import com.hengyu.cases.vo.CaseInfoVo;

/**
 * 
 * -
 * <p>
 * - 中介案例表 服务类 -
 * </p>
 *
 * - @author allnas - @since 2018-08-28
 */
public interface CaseService extends IService<Case> {
	/**
	 * 根据ID查询案例信息
	 * 
	 * @param caseId
	 * @return
	 */
	CaseInfoVo queryCaseById(Integer caseId);

	/**
	 * 根据案例类型查看列表
	 * 
	 * @param companyId
	 * @param categoryId
	 * @return
	 */
	Page<CaseInfoVo> queryAllByCategoryId(Page<CaseInfoVo> page, Integer companyId, Integer categoryId, Integer userId);

	List<CaseInfoVo> queryDraft(Integer companyId);

	void updateTop(Integer userId, Integer id);

	void updateRef(Integer userId, Integer id);

	void cancelTop(Integer userId, Integer id);

	void cancelRef(Integer userId, Integer id);
	
	void sendWXMessage(Case c);
}
