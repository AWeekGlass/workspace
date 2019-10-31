package com.hengyu.cases.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.cases.dao.CaseDAO;
import com.hengyu.cases.dao.QuestionDAO;
import com.hengyu.cases.entity.Case;
import com.hengyu.cases.entity.Question;
import com.hengyu.cases.service.CaseService;
import com.hengyu.cases.vo.CaseInfoVo;
import com.hengyu.cases.vo.UserVo;
import com.hengyu.common.entity.TemplateData;
import com.hengyu.common.entity.WechatTemplate;
import com.hengyu.common.util.HttpRequest;
import com.hengyu.common.util.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 中介案例表 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
@Slf4j
@Service
public class CaseServiceImpl extends ServiceImpl<CaseDAO, Case> implements CaseService {
	
	@Autowired
	private CaseDAO caseDao;
	
	@Value("${wx.appid}")
	private String appId;
	
	@Value("${wx.url}")
	private String url;

	@Value("${wx.secret}")
	private String secret;

	private final String REQUES_TURL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
	
	@Autowired
	private QuestionDAO questionDAO;
	

	@Override
	public CaseInfoVo queryCaseById(Integer id) {
		return caseDao.queryCaseById(id);
	}

	@Override
	public Page<CaseInfoVo> queryAllByCategoryId(Page<CaseInfoVo> page, Integer companyId, Integer categoryId,
			Integer userId) {
		return page.setRecords(caseDao.queryAllByCategoryId(page, companyId, categoryId, userId));
	}

	@Override
	public List<CaseInfoVo> queryDraft(Integer companyId) {
		return caseDao.queryDraft(companyId);
	}

	@Override
	public void updateTop(Integer userId, Integer id) {
		caseDao.updateTop(userId, id);
	}

	@Override
	public void updateRef(Integer userId, Integer id) {
		caseDao.updateRef(userId, id);
	}

	@Override
	public void cancelTop(Integer userId, Integer id) {
		caseDao.cancelTop(userId, id);
	}

	@Override
	public void cancelRef(Integer userId, Integer id) {
		caseDao.cancelRef(userId, id);
	}

	@Override
	public void sendWXMessage(Case c) {
		List<UserVo> list = caseDao.queryUsers("案例审核",c.getCompanyId());
		
	}
	
	public boolean sendWXMessage(List<UserVo> list,Case c){
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
	    wechatTemplate.setTemplate_id("j7geXLK9FpQqTUlZ5Zv0UgsZ6cnQro5b78ubsda1njo");
	    //跳转详情URL
	    wechatTemplate.setUrl(url);
	    
	    Map<String,TemplateData> mapdata = new HashMap<>();
	    // 封装模板数据
	    TemplateData first = new TemplateData();
	    first.setValue("有一篇新的问答发表了，欢迎阅读及评论!");
	    first.setColor("#173177");
	    mapdata.put("first", first);
	    
	    TemplateData keyword1 = new TemplateData();
	    keyword1.setValue(c.getTitle());
	    keyword1.setColor("#173177");
	    mapdata.put("keyword1", keyword1);
	    
	    TemplateData keyword2 = new TemplateData();
	    keyword2.setValue(c.getContent());
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
