package com.hengyu.training.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.common.entity.TemplateData;
import com.hengyu.common.entity.WechatTemplate;
import com.hengyu.common.util.HttpRequest;
import com.hengyu.common.util.JsonUtils;
import com.hengyu.training.dao.TrainingExperienceDAO;
import com.hengyu.training.entity.TrainComment;
import com.hengyu.training.entity.TrainingExperience;
import com.hengyu.training.entity.TrainingLike;
import com.hengyu.training.service.TrainCommentService;
import com.hengyu.training.service.TrainingExperienceService;
import com.hengyu.training.service.TrainingLikeService;
import com.hengyu.training.vo.UserVo;

import lombok.extern.slf4j.Slf4j;

/**
 * 培训心得 服务实现类
 *
 * @author allnas
 * @since 2018-12-13
 */
@Slf4j
@Service
public class TrainingExperienceServiceImpl extends ServiceImpl<TrainingExperienceDAO, TrainingExperience>
		implements TrainingExperienceService {

	@Autowired
	private TrainingExperienceDAO tranExpDAO;

	@Autowired
	private TrainingLikeService traLikeService;

	@Autowired
	private TrainCommentService commentService;

	@Value("${wx.appid}")
	private String appId;

	@Value("${wx.url}")
	private String url;

	@Value("${wx.secret}")
	private String secret;

	private final String REQUES_TURL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

	@Override
	public Page<TrainingExperience> queryAllByCategoryId(Page<TrainingExperience> page,
			TrainingExperience trainingExperience) {
		Integer userId = trainingExperience.getUserId();
		if (Objects.equals(trainingExperience.getType(), 1)) {
			trainingExperience.setUserId(null);
		}
		List<TrainingExperience> list = tranExpDAO.queryAllByCategoryId(page, trainingExperience);
		for (TrainingExperience trainingExp : list) {
			// 查询点赞人数
			Integer count = traLikeService
					.selectCount(new EntityWrapper<TrainingLike>().eq("training_exp_id", trainingExp.getId()));
			trainingExp.setLike(count);
			// 查询本人是否点赞
			Integer num = traLikeService.selectCount(
					new EntityWrapper<TrainingLike>().eq("training_exp_id", trainingExp.getId()).eq("user_id", userId));
			if (num == 0) {
				trainingExp.setIsLike(0);
			} else {
				trainingExp.setIsLike(1);
			}
			// 评论数量
			Integer comment = commentService
					.selectCount(new EntityWrapper<TrainComment>().eq("training_exp_id", trainingExp.getId()));
			trainingExp.setCommentNum(comment);
		}
		return page.setRecords(list);
	}

	@Override
	public List<TrainingExperience> queryDraft(Integer companyId) {
		return tranExpDAO.queryDraft(companyId);
	}

	@Override
	public TrainingExperience detail(Integer id, Integer userId) {

		TrainingExperience trainingExp = tranExpDAO.detail(id);

		Integer count = traLikeService
				.selectCount(new EntityWrapper<TrainingLike>().eq("training_exp_id", trainingExp.getId()));
		trainingExp.setLike(count);
		Integer num = traLikeService.selectCount(
				new EntityWrapper<TrainingLike>().eq("training_exp_id", trainingExp.getId()).eq("user_id", userId));
		if (num == 0) {
			trainingExp.setIsLike(0);
		} else {
			trainingExp.setIsLike(1);
		}
		/*
		 * if(Objects.nonNull(trainingExp.getStoreId())){ StoreVo storeVo =
		 * tranExpDAO.getStoreName(trainingExp.getStoreId()); }
		 */
		return trainingExp;
	}

	@Override
	public boolean delete(String id) {
		boolean flag = false;
		List<String> ids = Arrays.asList(id.split(","));
		for (String id_ : ids) {
			TrainingExperience trainingExp = new TrainingExperience();
			trainingExp.setId(Integer.valueOf(id_));
			trainingExp.setDelFlag(0);
			flag = updateById(trainingExp);
			if (!flag) {
				return flag;
			}
		}
		return flag;
	}

	@Override
	public List<TrainingLike> getLike(Integer id) {
		return tranExpDAO.getLike(id);
	}

	@Override
	public void sendWXMessage(TrainingExperience t) {
		List<UserVo> list = tranExpDAO.queryUsers("", t.getCompanyId());
		sendWXMessage(list, t);
	}

	public boolean sendWXMessage(List<UserVo> list, TrainingExperience t) {
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
		first.setValue("有一篇新的培训心得发表了，欢迎阅读及评论!");
		first.setColor("#173177");
		mapdata.put("first", first);

		TemplateData keyword1 = new TemplateData();
		keyword1.setValue(t.getTitle());
		keyword1.setColor("#173177");
		mapdata.put("keyword1", keyword1);

		TemplateData keyword2 = new TemplateData();
		keyword2.setValue(t.getContent());
		keyword2.setColor("#173177");
		mapdata.put("keyword2", keyword2);

		TemplateData keyword3 = new TemplateData();
		keyword3.setValue("本次操作需要登录电脑完成，谢谢您的配合！");
		keyword3.setColor("#173177");
		mapdata.put("remark", keyword3);

		wechatTemplate.setData(mapdata);

		for (UserVo userVo : list) {
			// 发送用户openid
			wechatTemplate.setTouser(userVo.getWxOpenId());
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

}
