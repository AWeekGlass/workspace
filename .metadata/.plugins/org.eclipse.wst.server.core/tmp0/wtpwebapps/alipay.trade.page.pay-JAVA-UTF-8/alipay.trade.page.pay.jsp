<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>付款</title>
</head>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.alipay.config.*"%>
<%@ page import="com.alipay.api.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.alibaba.fastjson.JSON"%>
<%@ page import="com.alipay.api.request.*"%>
<%@ page import="com.alipay.api.domain.AlipayTradePagePayModel"%>

<%
	//获得初始化的AlipayClient
	AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", "2018072460787253", "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCL0OlzxnWnGtck1JF7a407tkoRomk1G2zRkCXwZag7CfVci45TPDWMRAEKqL8tUKzGAepPu6uBYqYCHnKPAV1QPNXUvv3o07B4EAWKNWMWW9XQ0WRLtU4F4z7HQ9KZ3pvz1+k4pEVvScmrIBSZCqbz/4xNF/l7o2p6AQFffETrbdxCdOcRH1k+OOXbM6mRL3id3uo+4IptWkCKStHDeUHxedEy5ytJHaz5YHzuUR8VXSK/tBLCpKvk6y5A5mkGAxdpEeOmow3jpv+4xh+2aE7E99UK6mSpFW7O7RCS+4DP8rAMB/T4Wj/IMgoumK5RcnmZUpwafeIG3A6o8kubO4NVAgMBAAECggEATJFB56c9cx/dxkuqyD4kABlaAsJAf1bfhvw76qShNoPDnMTIUojcgk4Ti/TRjU1q8JzH5pZN2/NcGjj2X55Nrc1DBNj7T+/t6jJL9l46P1pXg53IITXO3qaOEK3xvVYNbcJF9EDxzXpJOK++1/jfaSv/d5CzjaP2gY3fZJ03WP3mQ8kdRbzAMa9Ytq7n8PJ2ASl1XV9MZIMruzykWQ6qEGtR/F+/NOeUsWdNkNudPHBtasB1zADOeNpIslBILOQ4IELKsojYy3bqkMvju0afJLmiA8fStlBu2Tx1b186bnxyAcDZaqw8OiqA4VyW8lDniwWj5yV6mkucp36jYHd2wQKBgQDU2BPzZU02Qvei/CfL90AyQ27POM51i7EL+RFCEK3f0GoBDM/Pd5UH1Yl4C1biAFYE/cGQc7E3ZMEb1djmBxzbJF51W2J+rgtqfqyn9/XYJFU7XsJ2VoGsGNmSlOFGytoPaZK8KCtnbEr2gcCjqUvD0iDgz5dh9IH1cNYVt6oYLQKBgQCoKjrTd5fyZzxzZ0JQU/jlv3U8zJpr7s2J42EGBW3ZQVXendN8I+J60hF83GgkSavJnAPwG+r6UP8FXFZ2rQoQWtIUy4ibRqv1uROD4DY3Q25ahZxkvfnwNQ+itshtiSmvlXD9q6o87huU8/LQ/OtzbadRb0SzAJNBPbxoy7WoyQKBgQCchBz/sjG6QjqaVvTo73P8wku9BJgGJmhWVpyTSXKjED6zcPlsJhKohSWm6ifE39xhpTzu/bSG+Ids8a+jT/2vRKpVgJMIdvIcKPTMO/JENEuc8gzsOqb2p/tu6B5VEZyTEt59kMiCMEdbyIShlUAKyv20c9NtjAD5PTWhGMNzLQKBgB3tPgDKJLlX0/M5qhx/hSjl1KhYRRb2LY/fbjHTH4RtUynF7pAGFtUCs2gEFWh3z+GWKUE+xqd7aJb8osqF6ueUMhTPT+VKQ2n8YNO0QBG/p1DmvDlVcbhkyP6NBsgvg8KilrQeK7KiMThl3Y2BpSkql38vjQtKC+qtYO1Ja2+5AoGAK0uqx38tVs395fPxvcCVlTHWzCaTHWBkA1QAOu3gVEOpPk0Jm2eU1RurqBaU/edzz2/FgyJv7Zn5SowYK+z1mZZWEVlN11eKXND8N7LdaKsu+H5drlT6ZfoS0n5bv9dAKmWI+lhm+aR1ilAdQdhzFnYjImv6k25bd/Qs++d6fdc=", "json", "UTF-8","MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAi9Dpc8Z1pxrXJNSRe2uNO7ZKEaJpNRts0ZAl8GWoOwn1XIuOUzw1jEQBCqi/LVCsxgHqT7urgWKmAh5yjwFdUDzV1L796NOweBAFijVjFlvV0NFkS7VOBeM+x0PSmd6b89fpOKRFb0nJqyAUmQqm8/+MTRf5e6NqegEBX3xE623cQnTnER9ZPjjl2zOpkS94nd7qPuCKbVpAikrRw3lB8XnRMucrSR2s+WB87lEfFV0iv7QSwqSr5OsuQOZpBgMXaRHjpqMN46b/uMYftmhOxPfVCupkqRVuzu0QkvuAz/KwDAf0+Fo/yDIKLpiuUXJ5mVKcGn3iBtwOqPJLmzuDVQIDAQAB","RSA2");
	
	//设置请求参数
	AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
	alipayRequest.setNotifyUrl("http://localhost:8080/aliPayNotify");
	
	//商户订单号，商户网站订单系统中唯一订单号，必填
	String out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
	//付款金额，必填
	String total_amount = new String(request.getParameter("WIDtotal_amount").getBytes("ISO-8859-1"),"UTF-8");
	//订单名称，必填
	String subject = new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"),"UTF-8");
	//商品描述，可空
	String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");
	
	alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
			+ "\"total_amount\":\""+ total_amount +"\"," 
			+ "\"subject\":\""+ subject +"\"," 
			+ "\"body\":\""+ body +"\"," 
			+ "\"timeout_express\":\"10m\"," 
			+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
	
	//请求
	String result = alipayClient.pageExecute(alipayRequest).getBody();
	
	//输出
	out.println(result);
%>
<body>
</body>
</html>