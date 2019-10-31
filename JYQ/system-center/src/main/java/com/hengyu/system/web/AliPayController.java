package com.hengyu.system.web;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alipay.api.AlipayApiException;
import com.hengyu.alipay.service.AlipayService;
import com.hengyu.system.entity.Order;
import com.hengyu.system.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/alipay")
public class AliPayController {
	@Autowired
	private AlipayService alipayService;

	@Autowired
	private OrderService orderService;

	/**
	 * 支付订单生成
	 * 
	 * @return
	 * @throws AlipayApiException
	 * @throws IOException
	 */
	@GetMapping("generateTradeOrder/{orderId}")
	public void generateTradeOrder(HttpServletResponse response, @PathVariable String orderId)
			throws AlipayApiException, IOException {

		Order order = orderService.selectById(orderId);
		if (Objects.nonNull(order)) {
			String amount = String.valueOf((double) order.getPayPrice() / 100);
			String orderCode = order.getOrderCode();
			String body = alipayService.generateTradeOrder(orderCode, amount, "支付金额", "消费支付：" + amount);
			log.info("body:{}", body);
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(body);// 直接将完整的表单html输出到页面
			response.getWriter().flush();
			response.getWriter().close();
		}

	}

	/**
	 * 支付宝支付订单回调
	 * 
	 * @param request
	 * @return
	 * @throws AlipayApiException
	 */
	@GetMapping("aliPayNotify")
	public String aliPayNotify(HttpServletRequest request) throws AlipayApiException {
		Map<String, String> params = alipayService.orderCallback(request);
		log.info("回调参数params:{}", params);
		if (Objects.nonNull(params)) {
			// 商户订单号
			String out_trade_no = params.get("out_trade_no");
			// 支付宝交易号
			String trade_no = params.get("trade_no");
			// 交易状态
			String trade_status = params.get("trade_status");
			// 交易金额
			String total_amount = params.get("total_amount");
			log.info("out_trade_no={};trade_no={};trade_status={};total_amount={}", out_trade_no, trade_no,
					trade_status, total_amount);

			Order order = new Order();
			order.setOrderCode(out_trade_no);
			order = orderService.queryDetail(order);
			order.setOuterCode(trade_no);
			order.setPayTime(new Date());

			if (Objects.equals(trade_status, "TRADE_SUCCESS")) {
				// 更新订单信息
				order.setStatus(1);
				//获取时间加一年或加一月或加一天
				Date date = new Date();
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);//设置起时间
				cal.add(Calendar.YEAR, 1);//增加一年
				order.setEndTime(cal.getTime());
				orderService.updateById(order);
				return "success";
			} else if (Objects.equals(trade_status, "TRADE_CLOSED")) {
				order.setStatus(2);
				orderService.updateById(order);
			}

		}
		return "failure";
	}
}
