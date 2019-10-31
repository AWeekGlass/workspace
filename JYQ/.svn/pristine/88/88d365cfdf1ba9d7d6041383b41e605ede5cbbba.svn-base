package com.hengyu.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.common.entity.TemplateData;
import com.hengyu.common.entity.WechatTemplate;
import com.hengyu.common.jwt.JWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.EncryptionUtil;
import com.hengyu.common.util.HttpRequest;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.common.util.JsonUtils;
import com.hengyu.user.dao.AdminDAO;
import com.hengyu.user.dao.ChangePhoneDAO;
import com.hengyu.user.dao.SmsVerifyDAO;
import com.hengyu.user.entity.Admin;
import com.hengyu.user.entity.ChangePhone;
import com.hengyu.user.entity.SmsVerify;
import com.hengyu.user.po.ChangePhonePo;
import com.hengyu.user.service.AdminService;
import com.hengyu.user.vo.AdminFullVo;
import com.hengyu.user.vo.AdminMiddleVo;
import com.hengyu.user.vo.AdminShortVo;
import com.hengyu.user.vo.AdminSmallVo;
import com.hengyu.user.vo.AdminTreeVo;
import com.hengyu.user.vo.AdminVo;
import com.hengyu.user.vo.MyInfoVo;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 中介用户表 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
@Slf4j
@Service
public class AdminServiceImpl extends ServiceImpl<AdminDAO, Admin> implements AdminService {
	@Autowired
	private AdminDAO adminDAO;

	@Autowired
	private JWTUtils jwtUtil;

	@Autowired
	private SmsVerifyDAO smsVerifyDAO;

	@Autowired
	private ChangePhoneDAO changePhoneDAO;

	@Value("${wx.appid}")
	private String appId;

	@Value("${wx.change_ms_tem}")
	private String changeMsTem;

	@Value("${wx.url}")
	private String url;

	@Value("${wx.secret}")
	private String secret;

	private final String REQUES_TURL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

	@Override
	public Page<AdminMiddleVo> queryList(Page<AdminMiddleVo> page, Map<String, Object> parameterMap) {
		return page.setRecords(adminDAO.queryList(page, parameterMap));
	}

	@Override
	public String login(String phone, String password) throws Exception {
		AdminShortVo adminShortVo = adminDAO.login(phone, EncryptionUtil.getSHA256StrJava(password));
		if (Objects.nonNull(adminShortVo)) {
			JWTInfo jwtInfo = new JWTInfo();
			jwtInfo.setId(adminShortVo.getId());
			jwtInfo.setUserName(adminShortVo.getUserName());
			jwtInfo.setCompanyId(adminShortVo.getCompanyId());
			jwtInfo.setCompanyName(adminShortVo.getCompanyName());
			return jwtUtil.generateToken(jwtInfo);
		}
		return "";
	}

	@Override
	public AdminVo queryDetailById(Integer id) {
		return adminDAO.queryDetailById(id);
	}

	@Override
	public Integer queryCntByStoreId(Integer storeId) {
		return adminDAO.queryCntByStoreId(storeId);
	}

	@Override
	public boolean updateDelFlag(List<String> ids) {
		List<Admin> list = ids.stream().map(id -> {
			Admin admin = new Admin();
			admin.setId(Integer.parseInt(id));
			admin.setDelFlag(1);
			return admin;
		}).collect(Collectors.toList());
		return updateBatchById(list);
	}

	@Override
	public Boolean checkPwd(Integer adminId, String password) {
		return adminDAO.checkPwd(adminId, password);
	}

	@Override
	public AdminFullVo queryResumeById(Integer id) {
		return adminDAO.queryResumeById(id);
	}

	@Override
	public List<AdminSmallVo> queryByUserName(Integer companyId, String userName) {
		return adminDAO.queryByUserName(companyId, userName);
	}

	@Override
	public Integer checkPhone(String phone) {
		return adminDAO.checkPhone(phone);
	}

	@Override
	public RestResponse<String> registe(Admin admin) {
		RestResponse<String> response = new RestResponse<>();
		// 验证两次密码是否一直
		if (!Objects.equals(admin.getPassword(), admin.getRePassword())) {
			return response.rel(false).data("两次密码不一致!");
		}
		// 校验验证码
		SmsVerify smsVerify = new SmsVerify();
		smsVerify.setType(1);
		smsVerify.setPhone(admin.getTelephone());
		smsVerify.setValidLastTime(new Date());
		smsVerify = smsVerifyDAO.verifySmsCode(smsVerify);
		if (Objects.nonNull(smsVerify) && Objects.equals(smsVerify.getSmsCode(), admin.getCode())) {
			smsVerify.setIsValid(0);
			smsVerifyDAO.updateById(smsVerify);
			// 如果没有推荐人，则设置推荐为null
			if (Objects.equals("0", admin.getInviterPhone())) {
				admin.setInviterPhone(null);
			}
			// 密码加密
			admin.setPassword(EncryptionUtil.getSHA256StrJava(admin.getPassword()));
			boolean flag = insert(admin);
			if (flag) {
				return response.rel(true).data("注册成功!");
			} else {
				return response.rel(false).data("注册失败!");
			}

		} else {
			return response.rel(false).data("验证码不正确");
		}
	}

	@Override
	public String changePhone(ChangePhonePo changePhonePo) {
		// 查询手机验证码
		SmsVerify smsVerify = new SmsVerify();
		smsVerify.setType(1);
		smsVerify.setPhone(changePhonePo.getNewPhone());
		smsVerify.setValidLastTime(new Date());
		smsVerify = smsVerifyDAO.verifySmsCode(smsVerify);
		smsVerify.setIsValid(0);
		smsVerifyDAO.updateById(smsVerify);
		if (Objects.isNull(smsVerify) || !Objects.equals(smsVerify.getSmsCode(), changePhonePo.getCode())) {
			return "验证码不正确";
		}
		Admin admin = selectById(changePhonePo.getUserId());
		if (Objects.isNull(admin) || !Objects.equals(changePhonePo.getOldPhone(), admin.getTelephone())) {
			return "原手机号输入错误";
		}
		admin.setTelephone(changePhonePo.getNewPhone());
		boolean falg = updateById(admin);
		if (falg) {
			// 新增更换手机号记录
			ChangePhone changePhone = new ChangePhone();
			changePhone.setOldPhone(changePhonePo.getOldPhone());
			changePhone.setNewPhone(changePhonePo.getNewPhone());
			changePhone.setUserId(changePhonePo.getUserId());
			changePhone.setCompanyId(changePhonePo.getCompanyId());
			changePhoneDAO.insert(changePhone);
			// 查询该模块管理员
			List<Admin> list = queryUsers("申请加入公司审核", changePhonePo.getCompanyId());
			sendWXMessage(list, changePhone);
			return "提交成功";

		} else {
			return "更换失败";
		}
	}

	@Override
	public MyInfoVo queryMyInfo(Integer userId) {
		return adminDAO.queryMyInfo(userId);
	}

	public boolean sendWXMessage(List<Admin> list, ChangePhone changePhone) {
		// 获取基础支持的access_token
		String GET_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId
				+ "&secret=" + secret;
		String json = HttpRequest.sendGet(GET_ACCESS_TOKEN, null);
		JSONObject jsonObject = JSONObject.parseObject(json);
		String access_token = jsonObject.get("access_token").toString();

		// 发送模板消息
		String resultUrl2 = REQUES_TURL + access_token;
		// 封装基础数据
		WechatTemplate wechatTemplate = new WechatTemplate();
		// 模板id(申请加入企业)
		wechatTemplate.setTemplate_id("j7geXLK9FpQqTUlZ5Zv0UgsZ6cnQro5b78ubsda1njo");
		// 跳转详情URL
		wechatTemplate.setUrl(url);

		Map<String, TemplateData> mapdata = new HashMap<>();
		// 封装模板数据
		TemplateData first = new TemplateData();
		first.setValue("申请加入企业!");
		first.setColor("#173177");
		mapdata.put("first", first);

		TemplateData keyword1 = new TemplateData();
		keyword1.setValue(changePhone.getNewPhone());
		keyword1.setColor("#173177");
		mapdata.put("keyword1", keyword1);

		TemplateData keyword2 = new TemplateData();
		keyword2.setValue(selectById(changePhone.getUserId()).getUserName());
		keyword2.setColor("#173177");
		mapdata.put("keyword2", keyword2);

		TemplateData keyword3 = new TemplateData();
		keyword3.setValue("本次操作需要登录电脑完成，谢谢您的配合！");
		keyword3.setColor("#173177");
		mapdata.put("remark", keyword3);

		wechatTemplate.setData(mapdata);

		for (Admin admin : list) {
			// 发送用户openid
			wechatTemplate.setTouser(admin.getWxOpenId());
			String toString = JsonUtils.toJsonString(wechatTemplate);
			String json2 = HttpRequest.sendPost(resultUrl2, toString);
			JSONObject jsonObject2 = JSONObject.parseObject(json2);
			int result = 0;
			if (null != jsonObject2) {
				jsonObject2.getIntValue("errcode");
				if (0 != jsonObject2.getIntValue("errcode")) {
					result = jsonObject2.getIntValue("errcode");
					log.error("错误 errcode:{} errmsg:{}", jsonObject2.getInteger("errcode"),
							jsonObject2.get("errmsg").toString());
				}
			}
			log.info("模板消息发送结果：" + result);
		}

		return true;
	}

	@Override
	public List<Admin> queryUsers(String key, Integer companyId) {
		return adminDAO.queryUsers(key, companyId);
	}

	@Override
	public List<AdminTreeVo> queryTree(Integer companyId) {
		return adminDAO.queryRootTree(companyId);
	}

	@Override
	public String login(AdminShortVo adminShortVo) throws Exception {
		JWTInfo jwtInfo = new JWTInfo();
		jwtInfo.setId(adminShortVo.getId());
		jwtInfo.setUserName(adminShortVo.getUserName());
		jwtInfo.setCompanyId(adminShortVo.getCompanyId());
		jwtInfo.setCompanyName(adminShortVo.getCompanyName());
		return jwtUtil.generateToken(jwtInfo);
	}
	
	public static void main(String[] args) throws Exception {
		JWTUtils jwtUtil = new JWTUtils();
		JWTInfo jwtInfo = new JWTInfo();
		jwtInfo.setId(2);
		jwtInfo.setUserName("汪文建");
		jwtInfo.setCompanyId(53);
		jwtInfo.setCompanyName("南京链家地产经纪有限公司");
		System.out.println(jwtUtil.generateToken(jwtInfo));
	}

	@Override
	public String check(String phone) {
		return String.valueOf(selectCount(new EntityWrapper<Admin>().eq("telephone", phone).eq("del_flag", 0)));
	}

}
