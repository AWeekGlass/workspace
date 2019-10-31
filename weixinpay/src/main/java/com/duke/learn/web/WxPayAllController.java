package com.duke.learn.web;

import com.duke.learn.weixinpay.util.GsonUtils;
import com.duke.learn.weixinpay.util.HttpKit;
import com.duke.learn.weixinpay.util.IpKit;
import com.duke.learn.weixinpay.util.PaymentKit;
import com.duke.learn.weixinpay.util.ResponseUtil;
import com.duke.learn.weixinpay.util.StrKit;
import com.duke.learn.weixinpay.util.WxPayApi;
import com.duke.learn.weixinpay.util.WxPayApiConfig;
import com.duke.learn.weixinpay.util.WxPayApiConfigKit;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("/wx")
public class WxPayAllController {


	@Autowired
	//注入的方法
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private String notify_url = "http://www.xxxx.xom/项目名/wx/pay_notify.do";
	public static WxPayApiConfig getApiConfig() {
		return WxPayApiConfig.New()
				       .setAppId("appID")
				       .setMchId("mchID")
				       .setPaternerKey("partnerKey")
				       .setPayModel(WxPayApiConfig.PayModel.BUSINESSMODEL);
	}
	
	
	/**
	 * 微信H5 支付
	 * 注意：必须再web页面中发起支付且域名已添加到开发配置中
	 */
	@RequestMapping(value ="/h5pay")
	public void wapPay(HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info(">>>>>>>request:"+request+"<<<<<<<");
		String orderNo = request.getParameter("orderNo");//通过订单号查询需要支付多少钱
					System.out.println("--pay start--");
					String ip = IpKit.getRealIp(request);
					if (StrKit.isBlank(ip)) {
						ip = "127.0.0.1";
					}
					
					Map<String, String> h5_info=new HashMap<>();
					Map<String, Map> sceneInfo=new HashMap<>();
					h5_info.put("type", "Wap");
					h5_info.put("wap_url", "http://www.xxxxxx.com/demo");//发起支付的网址
					h5_info.put("wap_name", "销售xxxxx");
					sceneInfo.put("sceneInfo", h5_info);//必要参数
					Map<String, String> params=WxPayApiConfig.New()
							                           .setAppId("appid")
							                           .setMchId("mchID")
							                           .setBody("净水器预售-净水器预售支付")
							                           .setSpbillCreateIp(ip)
							                           .setTotalFee("钱")//以分为单位
							                           .setTradeType(WxPayApi.TradeType.MWEB)
							                           .setNotifyUrl(notify_url)
							                           .setPaternerKey("partnerKey")
							                           .setOutTradeNo("")//你的订单号 注意一个商品不能被多次支付
							                           .setSceneInfo(GsonUtils.object2json(sceneInfo))
							                           .setAttach("净水器预售")
							                           .build();
					log.info(">>>>>>params :"+params+"<<<<<<<<<");
					String xmlResult = WxPayApi.pushOrder(false,params);//false 不是沙箱
					
					Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
					log.info(">>>>>xmlResult:"+result+"<<<<<<<<<");
					String return_code = result.get("return_code");
					String return_msg = result.get("return_msg");
					if (!PaymentKit.codeIsOK(return_code)) {
						log.error("return_code>"+return_code+" return_msg>"+return_msg);
						throw new RuntimeException(return_msg);
					}
					String result_code = result.get("result_code");
					if (!PaymentKit.codeIsOK(result_code)) {
						log.error("result_code>"+result_code+" return_msg>"+return_msg);
						throw new RuntimeException(return_msg);
					}
					// 以下字段在return_code 和result_code都为SUCCESS的时候有返回
					
					String prepay_id = result.get("prepay_id");
					String mweb_url = result.get("mweb_url");
					
					log.info("prepay_id:"+prepay_id+" mweb_url:"+mweb_url);
					 String	urlString = mweb_url+"&redirect_url="+URLEncoder.encode("回调页面 不能带参数", "GBK");
					
					try {
							Map<String,String> map=new HashMap<String,String>();
							map.put("url", urlString);
		        			ResponseUtil.write(response, GsonUtils.object2json(map));
				} catch (Exception e) {
					e.printStackTrace();
					ResponseUtil.write(response, "error.jsp");//错误页面
					// TODO: handle exception
				}
					
			
		}
		 
		
	
	
	/**
	 * 公众号支付
	 */
	@RequestMapping(value ="/gzhpay")
	public ModelAndView webPay(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv=new ModelAndView();
		//String total_fee = request.getParameter("total_fee");
		String orderNo = request.getParameter("state");
		//获取用户的code
		String code = request.getParameter("code");
		log.info("code********************"+code);
		String openId=null;
		try {
			List<Object> list = accessToken(code);
			log.info("list********************"+list);
			openId=list.get(1).toString();
			log.info("openId********************"+openId);
		} catch (IOException e) {
			System.out.println("--openid获取错误");
		}
		if (StrKit.isBlank(openId)) {
			mv.setViewName("error");
			return mv;
		}
		if (StrKit.isBlank(orderNo)) {
			mv.setViewName("error");
			return mv;
		}
		String ip = IpKit.getRealIp(request);
		if (StrKit.isBlank(ip)) {
			ip = "127.0.0.1";
		}

				 WxPayApiConfigKit.putApiConfig(getApiConfig());
					Map<String, String> params = WxPayApiConfigKit.getWxPayApiConfig()
							                             .setAttach("净水器支付")
							                             .setBody("净水器支付")
							                             .setOpenId(openId)
							                             .setSpbillCreateIp(ip)
							                             .setTotalFee("money")//用户需要支付的钱 以分为单位
							                             .setTradeType(WxPayApi.TradeType.JSAPI)
							                             .setNotifyUrl(notify_url)
							                             .setOutTradeNo(orderNo+String.valueOf(Math.random()*900+100).substring(0, 3))
							                             .build();
					String xmlResult = WxPayApi.pushOrder(false,params);//false 代表不是沙盒测试
					log.info(xmlResult);
					Map<String, String> resultMap = PaymentKit.xmlToMap(xmlResult);
					
					String return_code = resultMap.get("return_code");
					String return_msg = resultMap.get("return_msg");
					if (!PaymentKit.codeIsOK(return_code)) {
						mv.setViewName("error");
						return mv;
					}
					String result_code = resultMap.get("result_code");
					if (!PaymentKit.codeIsOK(result_code)) {
						mv.setViewName("error");
						return mv;
					}
					// 以下字段在return_code 和result_code都为SUCCESS的时候有返回
					String prepay_id = resultMap.get("prepay_id");
					String timeStamp=String.valueOf(System.currentTimeMillis() / 1000);
					String nonce_str=String.valueOf(System.currentTimeMillis());
					String packages = "prepay_id="+prepay_id;
					Map<String, String> packageParams = new HashMap<String, String>();
					packageParams.put("appId", "appid");//自己的商户号
					packageParams.put("timeStamp", timeStamp);
					packageParams.put("nonceStr", nonce_str);
					packageParams.put("package", "prepay_id=" + prepay_id);
					packageParams.put("signType", "MD5");
					String packageSign = PaymentKit.createSign(packageParams, WxPayApiConfigKit.getWxPayApiConfig().getPaternerKey());
					packageParams.put("paySign", packageSign);
					//下面可以通过Map传入参数获取json字符串，其形式和上面一样，传到前台可以转为json形式再用，也是一个不错的方式，可以参考IJPay
					/*Map<String, String> packageParams = PaymentKit.prepayIdCreateSign(prepay_id);
					String jsonStr = JSON.toJSONString(packageParams);
					mv.addObject("jsonStr",jsonStr);*/
					mv.addObject("appid",  "appid");//自己设置
					mv.addObject("timeStamp", timeStamp);
					mv.addObject("nonceStr", nonce_str);
					mv.addObject("packageValue", packages);
					mv.addObject("paySign", packageSign);
					mv.addObject("success","ok");
					mv.setViewName("jsp/pay");
					return mv;
		
	}
	/**
	 * 通过微信用户的code换取网页授权access_token
	 * @return
	 * @throws IOException
	 * @throws
	 */
	public List<Object> accessToken(String code) throws IOException {
		List<Object> list = new ArrayList<Object>();
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				             +  "自己的APPID" + "&secret=" +"密钥" + "&code=" + code + "&grant_type=authorization_code";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		HttpResponse res = client.execute(post);
		if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity entity = res.getEntity();
			String str = EntityUtils.toString(entity, "utf-8");
			ObjectMapper mapper=new ObjectMapper();
			Map<String,Object> jsonOb=mapper.readValue(str, Map.class);
			list.add(jsonOb.get("access_token"));
			list.add(jsonOb.get("openid"));
		}
		return list;
	}
	
	
	@RequestMapping(value = "/pay_notify.do",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String pay_notify(HttpServletRequest request) {
		// 支付结果通用通知文档: https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7
		
		String xmlMsg = HttpKit.readData(request);
		System.out.println("pay notice---------"+xmlMsg);
		Map<String, String> params = PaymentKit.xmlToMap(xmlMsg);
//		String appid  = params.get("appid");
//		//商户号
//		String mch_id  = params.get("mch_id");
		String result_code  = params.get("result_code");
//		String openId      = params.get("openid");
//		//交易类型
//		String trade_type      = params.get("trade_type");
//		//付款银行
//		String bank_type      = params.get("bank_type");
//		// 总金额
	String total_fee     = params.get("total_fee");
//		//现金支付金额
	String cash_fee     = params.get("cash_fee");
//		// 微信支付订单号
		String transaction_id      = params.get("transaction_id");
//		// 商户订单号
		String out_trade_no      = params.get("out_trade_no");
//		// 支付完成时间，格式为yyyyMMddHHmmss
//		String time_end      = params.get("time_end");
		
		/////////////////////////////以下是附加参数///////////////////////////////////
		
		String attach      = params.get("attach");
//		String fee_type      = params.get("fee_type");
//		String is_subscribe      = params.get("is_subscribe");
//		String err_code      = params.get("err_code");
//		String err_code_des      = params.get("err_code_des");
		// 注意重复通知的情况，同一订单号可能收到多次通知，请注意一定先判断订单状态
		// 避免已经成功、关闭、退款的订单被再次更新
//		Order order = Order.dao.getOrderByTransactionId(transaction_id);
//		if (order==null) {
		WxPayApiConfigKit.setThreadLocalWxPayApiConfig(getApiConfig());
		if(PaymentKit.verifyNotify(params, WxPayApiConfigKit.getWxPayApiConfig().getPaternerKey())){
			if (("SUCCESS").equals(result_code)) {
				//此处更新订单状态
				
				//发送通知等
				Map<String, String> xml = new HashMap<String, String>();
				xml.put("return_code", "SUCCESS");
				xml.put("return_msg", "OK");
				return PaymentKit.toXml(xml);
			}
		}
		
		return null;
	}
	
	
	
}
