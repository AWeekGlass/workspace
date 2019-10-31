package com.hengyu.system.service.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.common.entity.TemplateData;
import com.hengyu.common.entity.WechatTemplate;
import com.hengyu.common.util.EncryptionUtil;
import com.hengyu.common.util.HttpRequest;
import com.hengyu.common.util.JsonUtils;
import com.hengyu.service.AdminService;
import com.hengyu.system.dao.CompanyDAO;
import com.hengyu.system.entity.Admin;
import com.hengyu.system.entity.Apply;
import com.hengyu.system.entity.Company;
import com.hengyu.system.entity.JoinCompany;
import com.hengyu.system.po.CompanyPo;
import com.hengyu.system.service.ApplyService;
import com.hengyu.system.service.CompanyService;
import com.hengyu.system.service.JoinCompanyService;
import com.hengyu.system.vo.CompanyInfoVo;
import com.hengyu.system.vo.CompanyVo;
import com.hengyu.system.vo.UserVo;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 中介公司表 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
@Slf4j
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyDAO, Company> implements CompanyService {
	
	@Autowired
	private CompanyDAO companyDao;

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private JoinCompanyService joinCompanyService;
	
	@Autowired
	private ApplyService applyService;
	
	@Value("${wx.appid}")
	private String appId;
	
	@Value("${wx.url}")
	private String url;

	@Value("${wx.secret}")
	private String secret;

	private final String REQUES_TURL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

	@Override
	public CompanyInfoVo queryCompanyInfoById(Integer id) {
		return companyDao.queryCompanyInfoById(id);
	}

	@Override
	public boolean update(Company company) {
		return updateById(company);
	}

	@Override
	public Company queryCompanyDetailById(Integer id) {
		return selectOne(new EntityWrapper<Company>()
				.setSqlSelect("`name`,abb,province_id,city_id,area_id,address_detail,logo_url").eq("id", id));
	}

	@Override
	public boolean addCompany(CompanyPo companyPo) {
		Company company = new Company();
		company.setName(companyPo.getName());
		company.setProvinceId(companyPo.getProvinceId());
		company.setCityId(companyPo.getCityId());
		company.setAreaId(companyPo.getAreaId());
		company.setAddressDetail(companyPo.getAddressDetail());
		company.setCustomerStatus(companyPo.getCustomerStatus());
		company.setStatus(1);// 设置为待审核状态
		company.setCreateTime(new Date());
		company.setSignDate(new Date());
		// 获取七天之后的日期
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 7);
		company.setExpireDate(calendar.getTime());
		if (Objects.nonNull(companyPo.getBusinessUrl())) {
			company.setBusinessUrl(companyPo.getBusinessUrl());
		}
		// 新增公司
		boolean flag = insert(company);

		if (flag) {// 新增公司用户
			Admin admin = new Admin();
			admin.setUserName(companyPo.getUserName());
			admin.setPhone(companyPo.getPhone());
			admin.setInviterPhone(companyPo.getInviterPhone());
			String password = companyPo.getPassword();
			admin.setPassword(EncryptionUtil.getSHA256StrJava(password));
			admin.setCreateTime(new Date());
			admin.setCompanyId(company.getId());
			admin.setStatus(2);
			adminService.addCompany(null, admin);
			
			//新增审批记录
			Apply apply = new Apply();
			apply.setCompanyId(company.getId());
			apply.setNumber(getRandom());
			apply.setState(1);//设置为待审批状态
			apply.setUserId(admin.getId());
			applyService.insert(apply);
		}
		return flag;
	}
	
	@Override
	public boolean createCompany(Company company) {
		company.setCustomerStatus(1);//试用用户
		company.setStatus(1);// 设置为待审核状态
		// 新增公司
		boolean flag = insert(company);
		if (flag) {			
			//新增审批记录
			Apply apply = new Apply();
			apply.setCompanyId(company.getId());
			apply.setNumber(getRandom()); 
			apply.setState(1);//设置为待审批状态
			apply.setUserId(company.getAdminId());
			applyService.insert(apply);
		}
		return flag;
	}
	
	@Override
	public String joinCompany(Integer userId, String companyName,String userName) {
		//验证公司是否存在
		List<Company> list = selectList(new EntityWrapper<Company>().eq("name", companyName));
		if(Objects.isNull(list) || list.size()<1){
			return "公司不存在";
		}
		//获取公司信息
		Company company = list.get(0);
		//新增加入公司申请
		JoinCompany joinCompany = new JoinCompany();
		joinCompany.setCompanyId(company.getId());
		joinCompany.setUserId(userId);
		joinCompany.setUserName(userName);
		boolean flag = joinCompanyService.insert(joinCompany);
		List<UserVo> list2 = companyDao.queryUsers("",joinCompany.getCompanyId());
		sendWXMessage(list2,joinCompany);
		if(flag){
			return "申请成功";
		}
		return "申请失败";
	}

	@Override
	public CompanyVo queryDetailById(Integer id) {
		return companyDao.queryDetailById(id);
	}

	@Override
	public Page<Company> getList(Page<Company> page, Company company) {
		List<Company> list = companyDao.getList(page, company);
		for (Company cp : list) {//查询门店和公司人数
			Integer store = companyDao.countStore(cp.getId());
			Integer staff = companyDao.countStaff(cp.getId());
			cp.setStaffNum(staff);
			cp.setStoreNum(store);
		}
		return page.setRecords(list);
	}
	
	/**
	 * 生成审批记录编号
	 * 
	 * @return
	 */
	private String getRandom() {
		Integer num = applyService.selectCount(new EntityWrapper<Apply>().eq("create_time", new Date()));
		num++;
		DecimalFormat df = new DecimalFormat("000");
		String str2 = df.format(num);
		return FastDateFormat.getInstance("yyyyMMdd").format(new Date())+str2;
	}

	@Override
	public boolean deleteAdmin(String phone) {
		Integer count = companyDao.deleteAdmin(phone);
		return count== 1? true:false;
	}
	
	public boolean sendWXMessage(List<UserVo> list,JoinCompany joinCompany){
		// 获取基础支持的access_token
		String GET_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ appId +"&secret="+ secret;
	    String json = HttpRequest.sendGet(GET_ACCESS_TOKEN, null);
	    JSONObject jsonObject = JSONObject.parseObject(json);
	    String access_token = jsonObject.get("access_token").toString();
	    
	    // 发送模板消息
	    String resultUrl2 = REQUES_TURL+access_token;
	    // 封装基础数据
	    WechatTemplate wechatTemplate = new WechatTemplate();
	    //模板id(申请加入企业)
	    wechatTemplate.setTemplate_id("iPvVcfNkFv9qqWpaWZHvun3wkrgeMQRddkipGqp1Br4");
	    //跳转详情URL
	    wechatTemplate.setUrl(url);
	    
	    Map<String,TemplateData> mapdata = new HashMap<>();
	    // 封装模板数据
	    TemplateData first = new TemplateData();
	    first.setValue("您好，有新同事申请加入您的企业啦！");
	    first.setColor("#173177");
	    mapdata.put("first", first);
	    
	    TemplateData keyword1 = new TemplateData();
	    keyword1.setValue(joinCompany.getUserName());
	    keyword1.setColor("#173177");
	    mapdata.put("keyword1", keyword1);
	    
	    Date d = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    TemplateData keyword2 = new TemplateData();
	    keyword2.setValue(sdf.format(d));
	    keyword2.setColor("#173177");
	    mapdata.put("keyword2", keyword2);
	    
	    TemplateData keyword3 = new TemplateData();
	    keyword3.setValue("本次操作需要登录电脑完成，谢谢您的配合！");
	    keyword3.setColor("#173177");
	    mapdata.put("remark", keyword3);
	    
	    wechatTemplate.setData(mapdata);
	    
	    for (UserVo userVo : list) {
	    	//发送用户openid
		    wechatTemplate.setTouser(userVo.getWxOpenId());
		    String toString = JsonUtils.toJsonString(wechatTemplate);
		    String json2 = HttpRequest.sendPost(resultUrl2, toString);
		    JSONObject jsonObject2 = JSONObject.parseObject(json2);
		    int result = 0;
		    if (null != jsonObject2) {
		    	jsonObject2.getIntValue("errcode");
		        if (0 != jsonObject2.getIntValue("errcode")) {
		            result = jsonObject2.getIntValue("errcode");
		            log.error("错误 errcode:{} errmsg:{}", jsonObject2.getInteger("errcode"), jsonObject2.get("errmsg").toString());
		        }
		    }
		    log.info("模板消息发送结果："+result);
		}
		return true;
	}

}
