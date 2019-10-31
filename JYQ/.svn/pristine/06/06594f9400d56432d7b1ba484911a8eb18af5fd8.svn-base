package com.hengyu.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.hengyu.system.entity.Apply;
import com.hengyu.system.po.ApplyPo;
import com.hengyu.system.vo.ApplyVo;

/**
 * <p>
 * 公司申请表 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-10-16
 */
public interface ApplyService extends IService<Apply> {
	
	Page<ApplyVo> queryList(Page<ApplyVo> page, Integer state, String searchKey,Integer companyId);
	
	String approvalApply(ApplyPo applyPo);
	
	/**
	 * 根据id查询详情
	 * @param id
	 * @return
	 */
	ApplyVo queryDetail(Integer id);

}
