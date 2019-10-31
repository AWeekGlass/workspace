package com.hengyu.system.web;

import static com.hengyu.common.constant.CommonConstants.PAGE_SIZE;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.system.entity.Order;
import com.hengyu.system.service.OrderService;
import com.hengyu.system.vo.OrderVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-12-05
 */
@Api(value = "OrderController", tags = "订单Controller")
@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private JWTUtils jwtUtils;
	
	@ApiOperation(value = "创建订单", notes = "创建订单")
	@ApiImplicitParam(name = "order", value = "订单内容", dataTypeClass = Order.class)
	@PostMapping("createOrder")
	public RestResponse<Order> createOrder(@RequestHeader("token")String token,@RequestBody Order order) throws Exception{
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		order.setCompanyId(info.getCompanyId());
		order.setAdminId(info.getUserId());
		return orderService.createOrder(order);
	}
	
	@ApiOperation(value = "续费", notes = "续费")
	@ApiImplicitParam(name = "order", value = "订单内容", dataTypeClass = Order.class)
	@PostMapping("renew")
	public RestResponse<Order> renew(@RequestHeader("token")String token,@RequestBody Order order) throws Exception{
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		if(!Objects.equals(info.getCompanyId(), order.getCompanyId())){
			return new RestResponse<Order>().rel(false).message("非本公司订单，无法续费!");
		}
		if (!Objects.equals(info.getUserId(), order.getAdminId())) {
			return new RestResponse<Order>().rel(false).message("非本人订单，无法续费！");
		}
		return orderService.createOrder(order);
	}
	
	@ApiOperation(value = "查询订单详情", notes = "查询订单详情")
	@ApiImplicitParam(name = "order", value = "订单内容", dataTypeClass = Order.class)
	@GetMapping("queryDetail/{id}")
	public RestResponse<Order> queryDetail(@PathVariable Integer id){
		Order order = new Order();
		order.setId(id);
		return new RestResponse<Order>().rel(true).data(orderService.queryDetail(order));
	}
	
	@ApiOperation(value = "查询订单列表", notes = "查询订单列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "current", value = "当前页", defaultValue = "1", dataType = "Integer"),
		@ApiImplicitParam(name = "size", value = "每页行数", defaultValue = PAGE_SIZE, dataType = "Integer"),
		@ApiImplicitParam(name = "order", value = "订单内容", dataTypeClass = Order.class) })
	@PostMapping("queryList")
	public RestResponse<Page<OrderVo>> queryList(@RequestHeader("token") String token,
			@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer size)
			throws Exception{
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		Page<OrderVo> page = new Page<>(current,size);
		Order order = new Order();
		order.setCompanyId(info.getCompanyId());
		order.setAdminId(info.getUserId());
		page = orderService.queryList(page,order);
		return new RestResponse<Page<OrderVo>>().rel(true).data(page);
	}

}

