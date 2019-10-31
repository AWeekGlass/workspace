package com.hengyu.contract.service;

import com.hengyu.contract.entity.Buyer;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 中介买受人表（乙方） 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
public interface BuyerService extends IService<Buyer> {
	
	/**
	 * 新增买受人
	 * @param buyer
	 * @return
	 */
	boolean save(Buyer buyer);

}
