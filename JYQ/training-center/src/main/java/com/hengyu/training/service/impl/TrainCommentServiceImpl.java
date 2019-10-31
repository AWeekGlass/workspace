package com.hengyu.training.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.common.entity.TemplateData;
import com.hengyu.common.entity.WechatTemplate;
import com.hengyu.common.util.HttpRequest;
import com.hengyu.common.util.JsonUtils;
import com.hengyu.training.dao.TrainCommentDAO;
import com.hengyu.training.dao.TrainingExperienceDAO;
import com.hengyu.training.entity.TrainComment;
import com.hengyu.training.entity.TrainingExperience;
import com.hengyu.training.service.TrainCommentService;
import com.hengyu.training.vo.UserVo;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 培训心得回复表 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-12-14
 */
@Slf4j
@Service
public class TrainCommentServiceImpl extends ServiceImpl<TrainCommentDAO, TrainComment> implements TrainCommentService {

	@Autowired
	private TrainCommentDAO trainComDAO;
	
	@Autowired
	private TrainingExperienceDAO texDao;
	
	@Value("${wx.appid}")
	private String appId;
	
	@Value("${wx.url}")
	private String url;

	@Value("${wx.secret}")
	private String secret;

	private final String REQUES_TURL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
	
	@Override
	public List<TrainComment> getCommentTree(Integer companyId, Integer id) {
		//查询父节点评论
		TrainComment trainComment = new TrainComment();
		trainComment.setCompanyId(companyId);
		trainComment.setTrainingExpId(id);
		trainComment.setType(1);
		List<TrainComment> list = trainComDAO.getTree(companyId, id, null);
		//查询子节点
		for (TrainComment trainComment_ : list) {
			List<TrainComment> trainComments = getChouBiList(companyId,trainComment_.getId());
			trainComment_.setComments(trainComments);
		}
		return list;
	}
	
	//获取子评论
	private List<TrainComment> getSonList(Integer companyId,Integer id){
		List<TrainComment> list = new ArrayList<TrainComment>();
		list = trainComDAO.getTree(companyId, null, id);
		if(Objects.nonNull(list)){
			for (TrainComment trainComment : list) {
				List<TrainComment> trainComments = getSonList(companyId, trainComment.getId());
				//嵌套
				trainComment.setComments(trainComments);
				//查询全部
				list.addAll(trainComments);
			}
		}
		return list;
	}
	
	private List<TrainComment> getChouBiList(Integer companyId,Integer id){
		List<TrainComment> list = trainComDAO.getChouBiList(companyId,id);
		return list;
	}

	@Override
	public void sendWXMessage(TrainComment t){
		TrainingExperience tex = texDao.selectById(t.getTrainingExpId());
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
	    first.setValue("有一篇新的培训心得发表了，欢迎阅读及评论!");
	    first.setColor("#173177");
	    mapdata.put("first", first);
	    
	    TemplateData keyword1 = new TemplateData();
	    keyword1.setValue(tex.getTitle());
	    keyword1.setColor("#173177");
	    mapdata.put("keyword1", keyword1);
	    
	    UserVo userVo = texDao.getUserName(t.getUserId());
	    TemplateData keyword2 = new TemplateData();
	    keyword2.setValue(userVo.getUserName());
	    keyword2.setColor("#173177");
	    mapdata.put("keyword2", keyword2);
	    
	    TemplateData keyword3 = new TemplateData();
	    keyword3.setValue("本次操作需要登录电脑完成，谢谢您的配合！");
	    keyword3.setColor("#173177");
	    mapdata.put("remark", keyword3);
	    
	    wechatTemplate.setData(mapdata);
	    
	    
	    UserVo userVo1 = texDao.getUserName(tex.getUserId());
	    
    	//发送用户openid
	    wechatTemplate.setTouser(userVo1.getWxOpenId());
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


}
