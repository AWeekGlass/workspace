package com.hengyu.contract.service.impl;

import com.hengyu.contract.entity.Buyer;
import com.hengyu.contract.dao.BuyerDAO;
import com.hengyu.contract.service.BuyerService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 中介买受人表（乙方） 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
@Service
public class BuyerServiceImpl extends ServiceImpl<BuyerDAO, Buyer> implements BuyerService {

	@Override
	public boolean save(Buyer buyer) {
		return insert(buyer);
	}

}
