package com.hengyu.system.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.system.dao.OrderDAO;
import com.hengyu.system.entity.Discount;
import com.hengyu.system.entity.Module;
import com.hengyu.system.entity.Order;
import com.hengyu.system.entity.UserDiscount;
import com.hengyu.system.service.DiscountService;
import com.hengyu.system.service.ModuleService;
import com.hengyu.system.service.OrderService;
import com.hengyu.system.service.UserDiscountService;
import com.hengyu.system.vo.OrderVo;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-12-05
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderDAO, Order> implements OrderService {

	@Autowired
	private OrderDAO orderDAO;

	@Autowired
	private ModuleService moduleService;

	@Autowired
	private UserDiscountService uDService;

	@Autowired
	private DiscountService discountService;

	@Override
	public RestResponse<Order> createOrder(Order order) {
		RestResponse<Order> response = new RestResponse<>();
		// 生成订单号
		order.setOrderCode(randomOrderNum(order.getCompanyId()));
		// 查询模块
		String product = order.getProductId();
		List<String> productids = Arrays.asList(product.split(","));
		Integer sum = 0;
		for (String id : productids) {
			Module module = moduleService.selectById(id);
			sum += module.getPrice() * order.getNumber();
		}
		order.setProductPrice(sum);
		// 查询折扣
		String discountId = order.getDiscountId();
		if (Objects.nonNull(discountId)) {
			List<String> discounts = Arrays.asList(discountId.split(","));
			for (String id : discounts) {
				Discount discount = discountService.selectById(id);
				// 折扣
				if (discount.getType() == 2) {
					Double sumDouble = new Double(((double) (discount.getDiscount() * sum)) / 100);
					sum = sumDouble.intValue();
				} else {// 满减
					sum = sum - discount.getDiscount();
				}
				//将优惠券设置为已经使用
				UserDiscount userDiscount = new UserDiscount();
				userDiscount.setState(1);
				uDService.update(userDiscount, new EntityWrapper<UserDiscount>().eq("user_id", order.getAdminId())
						.eq("discount_id", Integer.valueOf(id)));
			}
		}
		if (!Objects.equals(sum, order.getPayPrice())) {
			return response.rel(false).message("订单金额不正确");
		}
		// 查询优惠价格
		order.setDiscountPrice(order.getProductPrice() - sum);
		boolean flag = insert(order);
		if (flag) {
			return response.rel(true).data(order);
		}
		return response.rel(false).message("创建失败");
	}
	
	@Override
	public RestResponse<Order> renew(Order order) {
		//判断续费订单信息是否合法
		Order order_ = selectById(order.getId());
		RestResponse<Order> response = new RestResponse<>();
		if(!Objects.equals(order_.getProductId(), order.getProductId())){
			return response.rel(false).message("模块信息不一致");
		}
		if(!Objects.equals(order_.getDiscountId(), order.getDiscountId())){
			return response.rel(false).message("折扣信息不一致");
		}
		if(!Objects.equals(order_.getPayPrice(), order.getPayPrice())){
			return response.rel(false).message("支付金额不一致");
		}
		if(!Objects.equals(order_.getNumber(), order.getNumber())){
			return response.rel(false).message("人员数量不一致");
		}
		//创建新订单
		Order newOrder = new Order();
		newOrder.setNumber(order_.getNumber());
		newOrder.setProductId(order_.getProductId());
		if(Objects.nonNull(order_.getDiscountId())){
			newOrder.setDiscountId(order_.getDiscountId());
		}
		newOrder.setPayPrice(order_.getPayPrice());
		newOrder.setProductPrice(order_.getProductPrice());
		return createOrder(newOrder);
	}

	@Override
	public Order queryDetail(Order order) {
		List<Order> list = selectList(new EntityWrapper<Order>(order));
		if (Objects.nonNull(list) && Objects.nonNull(list.get(0))) {
			order =  list.get(0);
			String discount = order.getDiscountId();
			List<String> discounts = Arrays.asList(discount.split(","));
			List<Discount> discountList = new ArrayList<>();
			discountList = discountService.selectBatchIds(discounts);
			order.setDiscounts(discountList);
			return order;
			
		}
		return null;
	}

	@Override
	public Page<OrderVo> queryList(Page<OrderVo> page, Order order) {
		List<OrderVo> list = orderDAO.queryList(page, order);
		for (OrderVo orderVo : list) {
			// 查询产品信息
			String product = orderVo.getProductId();
			if (Objects.nonNull(product)) {
				List<String> products = Arrays.asList(product.split(","));
				List<Module> modules = moduleService.selectBatchIds(products);
				orderVo.setModules(modules);
			}
			// 查询折扣信息
			String discount = orderVo.getDiscountId();
			List<Discount> discountList = new ArrayList<>();
			if (Objects.nonNull(discount)) {
				List<String> discounts = Arrays.asList(discount.split(","));
				discountList = discountService.selectBatchIds(discounts);
				
			}
			orderVo.setDiscounts(discountList);
		}
		return page.setRecords(list);
	}

	/**
	 * 生成订单号
	 * 
	 * @return
	 */
	private String randomOrderNum(Integer companyId) {
		// 查询系统中最大编号
		Integer count = orderDAO.queryMaxNum(companyId);
		count++;
		DecimalFormat num = new DecimalFormat("000000");
		String code = num.format(count);
		return FastDateFormat.getInstance("yyyyMMddHHmmss").format(new Date()) + code;
	}


}
