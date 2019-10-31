package com.hengyu.contract.service;

import com.hengyu.contract.entity.Partner;
import com.hengyu.contract.po.QueryPartnerPo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 中介签约附件表 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-08-29
 */
public interface PartnerService extends IService<Partner> {

	/**
	 * 新增签约附件
	 * @param partner
	 * @return
	 */
	boolean save(Partner partner);
	
	/**
	 * 更新签约附件
	 * @param partner
	 * @return
	 */
	boolean update(Partner partner);
	
	/**
	 * 删除签约附件
	 * @param id
	 * @return
	 */
    boolean delete(String id);
    
    /**
     * 查询详情
     * @param id
     * @return
     */
    Partner queryById(Integer id);
    
    /**
     * 分页查询签约附件
     * @param page
     * @param partner
     * @return
     */
    Page<Partner> queryList(Page<Partner> page,QueryPartnerPo po);
}
