package com.zj.alipay.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;

@Component
public class AlipayService {
	@Value("${alipay.serverUrl}")
	private String serverUrl;

	@Value("${alipay.appId}")
	private String appId;

	@Value("${alipay.publicKey}")
	private String publicKey;

	@Value("${alipay.privateKey}")
	private String privateKey;

	/**
	 * 支付订单生成
	 * 
	 * @param outTradeNo     商户订单号
	 * @param orderAmount    订单金额
	 * @param passbackParams 公共回传参数
	 * @return 预订单信息，可直接给客户端请求
	 * @throws AlipayApiException
	 */
	public String generateTradeOrder(String outTradeNo, String orderAmount,String subject,String body)
			throws AlipayApiException {
		// 获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey, "json", "UTF-8", publicKey,
				"RSA2");

		// 设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setNotifyUrl("http://localhost:8080/aliPayNotify");

		alipayRequest.setBizContent("{\"out_trade_no\":\"" + outTradeNo + "\"," + "\"total_amount\":\"" + orderAmount
				+ "\"," + "\"subject\":\"" + subject + "\"," + "\"body\":\"" + body + "\","
				+ "\"timeout_express\":\"10m\"," + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

		// 请求
		String result = alipayClient.pageExecute(alipayRequest).getBody();
		return result;
	}

	/**
	 * 支付宝支付订单回调
	 * 
	 * @param request
	 * @return
	 * @throws AlipayApiException
	 */
	public Map<String, String> orderCallback(HttpServletRequest request) throws AlipayApiException {
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String[]> map = request.getParameterMap();
		for (String key : map.keySet()) {
			String[] paramValues = request.getParameterValues(key);
			if (paramValues.length == 1) {
				String value = paramValues[0];
				params.put(key, value);
			}
		}
		boolean flag = AlipaySignature.rsaCheckV1(params, publicKey, "UTF-8", "RSA2");
		if (flag) {
			return params;
		}
		return null;
	}

	/**
	 * 支付交易订单查询
	 * 
	 * @param outTradeNo 商户订单号
	 * @param tradeNo    支付宝交易号
	 * @throws AlipayApiException
	 */
	public boolean tradeQuery(String outTradeNo, String tradeNo) throws AlipayApiException {
		AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey, "json", "UTF-8", publicKey,
				"RSA2");
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		Map<String, String> bizContent = new HashMap<>();
		if (StringUtils.isNotBlank(outTradeNo)) {
			bizContent.put("out_trade_no", outTradeNo);
		}
		if (StringUtils.isNotBlank(tradeNo)) {
			bizContent.put("trade_no", tradeNo);
		}
		request.setBizContent(JSON.toJSONString(bizContent));
		AlipayTradeQueryResponse response = alipayClient.execute(request);
		return response.isSuccess();
	}

	/**
	 * 交易关闭
	 * 
	 * @param outTradeNo 商户订单号
	 * @param tradeNo    支付宝交易号
	 * @return
	 * @throws AlipayApiException
	 */
	public boolean tradeClose(String outTradeNo, String tradeNo) throws AlipayApiException {
		AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey, "json", "UTF-8", publicKey,
				"RSA2");
		AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
		Map<String, String> bizContent = new HashMap<>();
		if (StringUtils.isNotBlank(outTradeNo)) {
			bizContent.put("out_trade_no", outTradeNo);
		}
		if (StringUtils.isNotBlank(tradeNo)) {
			bizContent.put("trade_no", tradeNo);
		}
		request.setBizContent(JSON.toJSONString(bizContent));
		AlipayTradeCloseResponse response = alipayClient.execute(request);
		return response.isSuccess();
	}
}
