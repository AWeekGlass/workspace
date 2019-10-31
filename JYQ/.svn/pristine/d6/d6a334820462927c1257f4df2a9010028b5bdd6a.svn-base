package com.hengyu.contract.service.impl;

import com.hengyu.contract.entity.Seller;
import com.hengyu.contract.dao.SellerDAO;
import com.hengyu.contract.service.SellerService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 中介出卖人表（甲方） 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
@Service
public class SellerServiceImpl extends ServiceImpl<SellerDAO, Seller> implements SellerService {

	@Override
	public boolean save(Seller seller) {
		return insert(seller);
	}

	
}
