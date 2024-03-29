package com.hengyu.exam.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.common.entity.TemplateData;
import com.hengyu.common.entity.WechatTemplate;
import com.hengyu.common.util.HttpRequest;
import com.hengyu.common.util.JsonUtils;
import com.hengyu.exam.dao.TestAnswerDAO;
import com.hengyu.exam.dao.TestPaperDAO;
import com.hengyu.exam.dao.TestPaperUserDAO;
import com.hengyu.exam.entity.Question;
import com.hengyu.exam.entity.TestAnswer;
import com.hengyu.exam.entity.TestPaper;
import com.hengyu.exam.entity.TestPaperSubject;
import com.hengyu.exam.entity.TestPaperUser;
import com.hengyu.exam.po.AnswerPo;
import com.hengyu.exam.po.SubmitAnswerPo;
import com.hengyu.exam.service.QuestionService;
import com.hengyu.exam.service.TestAnswerService;
import com.hengyu.exam.service.TestPaperService;
import com.hengyu.exam.service.TestPaperSubjectService;
import com.hengyu.exam.service.TestPaperUserService;
import com.hengyu.exam.vo.TestUserVo;
import com.hengyu.exam.vo.UserVo;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 考试答案 服务实现类
 * </p>
 *
 * @author
 * @since 2018-11-01
 */
@Slf4j
@Service
public class TestAnswerServiceImpl extends ServiceImpl<TestAnswerDAO, TestAnswer> implements TestAnswerService {

	@Autowired
	private TestPaperUserService paperUserService;

	@Autowired
	private QuestionService questionService;

	/*
	 * @Autowired private TestPaperSubjectDAO subjectDAO;
	 */

	@Autowired
	private TestPaperSubjectService subjectService;

	@Autowired
	private TestPaperService paperService;

	@Autowired
	private TestAnswerDAO answerDAO;

	@Autowired
	private TestPaperUserDAO paperUserDAO;

	@Autowired
	private TestPaperDAO testPaperDAO;

	@Value("${wx.appid}")
	private String appId;

	@Value("${wx.url}")
	private String url;

	@Value("${wx.secret}")
	private String secret;

	private final String REQUES_TURL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

	@Override
	public boolean submitAnswer(SubmitAnswerPo po) {
		boolean flag = false;
		List<AnswerPo> list = JsonUtils.toArray(po.getAnswerJson(), AnswerPo.class);
		if (Objects.isNull(list)) {
			return flag;
		}
		Integer sum = 0;
		// 查询考试记录id
		List<TestPaperUser> testPaperUsers = paperUserService.selectList(
				new EntityWrapper<TestPaperUser>().eq("paper_id", po.getPaperId()).eq("user_id", po.getUserId()));
		// 新增答案记录
		TestPaperUser testPaperUser = testPaperUsers.get(0);
		for (AnswerPo answerPo : list) {
			// 查询题目
			Question question = questionService.selectById(answerPo.getQuestionId());
			if (testPaperUsers.size() > 0 && Objects.nonNull(question)) {
				TestAnswer testAnswer = new TestAnswer();
				testAnswer.setQuestionId(answerPo.getQuestionId());
				testAnswer.setUserAnswer(answerPo.getAnswerJson());
				testAnswer.setCreateTime(new Date());
				testAnswer.setPaperId(po.getPaperId());
				testAnswer.setTestUserId(testPaperUsers.get(0).getId());
				String answer = answerPo.getAnswerJson();
				boolean flag2 = false;
				TestPaperSubject subject = new TestPaperSubject();
				// 根据题型去处理答案
				switch (question.getType()) {
				// 单选题、判断题
				case 1:
				case 3:
					if (question.getType() == 1) {
						subject = subjectService
								.selectOne(new EntityWrapper<TestPaperSubject>().setSqlSelect("DISTINCT score as score")
										.eq("subject_id", 1).eq("paper_id", po.getPaperId()));
					} else {
						subject = subjectService
								.selectOne(new EntityWrapper<TestPaperSubject>().setSqlSelect("DISTINCT score as score")
										.eq("subject_id", 3).eq("paper_id", po.getPaperId()));
					}
					flag2 = Objects.equals(answer, question.getResult());
					if (flag2) {
						testAnswer.setRight(1);
						testAnswer.setScore(subject.getScore());
						sum = sum + subject.getScore();
					} else {
						testAnswer.setRight(0);
						testAnswer.setScore(0);
					}
					testAnswer.setUserAnswer(answer);
					break;
				// 多选题
				case 2:
					if (Objects.nonNull(answer)) {
						// 将考生答案排序重新组合
						List<String> answerList = Arrays.asList(answer.split(","));
						Collections.sort(answerList);
						answer = String.join(",", answerList);
						// 将标准答案排序重新组合
						answerList = Arrays.asList(question.getResult().split(","));
						Collections.sort(answerList);
						String result = String.join(",", answerList);
						flag2 = Objects.equals(answer, result);
					} else {
						flag2 = false;
					}
					subject = subjectService
							.selectOne(new EntityWrapper<TestPaperSubject>().setSqlSelect("DISTINCT score as score")
									.eq("subject_id", 3).eq("paper_id", po.getPaperId()));
					if (flag2) {
						testAnswer.setRight(1);
						testAnswer.setScore(subject.getScore());
						sum = sum + subject.getScore();
					} else {
						testAnswer.setRight(0);
						testAnswer.setScore(0);
					}
					testAnswer.setUserAnswer(answer);
					break;
				default:
					break;
				}
				flag = insert(testAnswer);
			}
		}
		// 如果没有简答题和填空题则设置为已经阅卷
		Integer count = answerDAO.countQuestion(po.getPaperId());
		if (count == 0) {
			testPaperUser.setIsCheck(1);
		}
		// 设置已考
		testPaperUser.setStatus(1);
		testPaperUser.setScore(sum);
		testPaperUser.setEndTime(new Date());
		paperUserService.updateById(testPaperUser);
		// 设置试卷状态为未批阅状态
		TestPaper testPaper = testPaperDAO.selectById(po.getPaperId());
		if (Objects.nonNull(testPaper.getState()) && testPaper.getState() == 0) {
			TestUserVo testUserVo = testPaperDAO.getTestedRate(po.getPaperId());
			// 如果参考率是100则设置试卷为已经阅卷
			if (count == 0 && testUserVo.getPassRate() == 100) {
				testPaper.setState(2);
			} else {
				testPaper.setState(3);
			}
		}
		testPaper.setIsExam(1);
		testPaperDAO.updateById(testPaper);
		return flag;
	}

	@Override
	public boolean checkQuestion(SubmitAnswerPo po, Integer userId) {
		boolean flag = false;
		List<AnswerPo> list = JsonUtils.toArray(po.getAnswerJson(), AnswerPo.class);
		if (Objects.isNull(list)) {
			return true;
		}
		// 查询考试记录id
		List<TestPaperUser> testPaperUsers = paperUserService.selectList(
				new EntityWrapper<TestPaperUser>().eq("paper_id", po.getPaperId()).eq("user_id", po.getUserId()));
		if (testPaperUsers.size() < 0) {
			return false;
		}
		TestPaperUser testPaperUser = testPaperUsers.get(0);
		Integer sum = 0;
		// 先统计非简答题分数
		sum = testPaperUser.getScore();
		// 设置阅卷人
		testPaperUser.setCheckUser(userId);
		for (AnswerPo answerPo : list) {
			// 查询考生答案记录
			TestAnswer testAnswer = selectOne(new EntityWrapper<TestAnswer>().eq("test_user_id", testPaperUser.getId())
					.eq("paper_id", po.getPaperId()).eq("question_id", answerPo.getQuestionId()));
			Question question = questionService.selectById(testAnswer.getQuestionId());
			// 如果是简答题则直接存储分数
			if (question.getType() == 5) {
				testAnswer.setScore(Integer.valueOf(answerPo.getAnswerJson()));
				sum = sum + Integer.valueOf(answerPo.getAnswerJson());
			} else {
				// 如果是填空题则判断是否正确 A正确 B错误
				// 查询题目分数
				TestPaperSubject subject = subjectService.selectOne(new EntityWrapper<TestPaperSubject>()
						.setSqlSelect("DISTINCT score as score").eq("subject_id", 4).eq("paper_id", po.getPaperId()));
				if (Objects.equals(answerPo.getAnswerJson(), "A")) {
					testAnswer.setRight(1);

					sum = sum + subject.getScore();
				} else {
					testAnswer.setRight(0);
				}

			}
			testAnswer.setMark(answerPo.getRemark());
			flag = updateById(testAnswer);

		}
		// 更新考试记录
		TestPaperUser paperUser = new TestPaperUser();
		paperUser.setUserId(po.getUserId());
		paperUser.setPaperId(po.getPaperId());
		paperUser = paperUserDAO.selectOne(paperUser);
		TestPaper paper = paperService.selectById(po.getPaperId());
		if (Objects.nonNull(paperUser)) {
			paperUser.setScore(sum);
			// 设置为已经批阅
			paperUser.setIsCheck(1);
			Integer score = testPaperDAO.score(po.getPaperId());
			if (Objects.nonNull(paper.getPassRate()) && Objects.nonNull(score)) {
				// 如果分数大于及格分数
				Integer score_ = Integer.valueOf(paper.getPassRate() * score / 100);
				//判断是否及格
				if (score_ <= sum) {
					paperUser.setPass(1);
				} else {
					paperUser.setPass(0);
				}
			}
			paperUserService.updateById(paperUser);
		}
		// 设置试卷状态为批阅中
		TestPaper testPaper = new TestPaper();
		testPaper.setId(po.getPaperId());
		// 查询批阅数据
		TestUserVo testUserVo = paperUserDAO.getCheckRate(po.getPaperId());
		// 如果批阅完成，则将试卷
		if (Objects.equals(testUserVo.getTested(), testUserVo.getTotal())) {
			// 设置试卷状态为已批阅
			testPaper.setState(2);
		} else {
			// 设置试卷状态为批阅中
			testPaper.setState(3);
		}
		testPaperDAO.updateById(testPaper);
		return flag;
	}

	public boolean sendWXMessage(SubmitAnswerPo po) {
		TestPaper testPaper = testPaperDAO.selectById(po.getPaperId());
		if (Objects.isNull(testPaper)) {
			log.info("未查询到本场考试信息！");
			return false;
		}
		// 获取基础支持的access_token
		String GET_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId
				+ "&secret=" + secret;
		String json = HttpRequest.sendGet(GET_ACCESS_TOKEN, null);
		JSONObject jsonObject = JSONObject.parseObject(json);
		String access_token = jsonObject.get("access_token").toString();
		log.info("access_token:{}", access_token);

		// 发送模板消息
		String resultUrl2 = REQUES_TURL + access_token;
		// 封装基础数据
		WechatTemplate wechatTemplate = new WechatTemplate();
		// 模板id(申请加入企业)
		wechatTemplate.setTemplate_id("f_Wp0GUHnvmvtcSou5ubCntPjRWivcs1rApab8t8akI");
		// 跳转详情URL
		wechatTemplate.setUrl(url);

		Map<String, TemplateData> mapdata = new HashMap<>();
		// 封装模板数据
		TemplateData first = new TemplateData();
		first.setValue(testPaper.getName());
		first.setColor("#173177");
		mapdata.put("first", first);

		TemplateData keyword1 = new TemplateData();
		keyword1.setValue("考试");
		keyword1.setColor("#173177");
		mapdata.put("keyword1", keyword1);

		TemplateData keyword2 = new TemplateData();
		keyword2.setValue(testPaperDAO.getUserName(testPaper.getDistributeId()).getUserName());
		keyword2.setColor("#173177");
		mapdata.put("keyword2", keyword2);

		TemplateData keyword3 = new TemplateData();
		keyword3.setValue(testPaper.getTimeLength() + "分钟");
		keyword3.setColor("#173177");
		mapdata.put("keyword3", keyword3);

		TemplateData keyword4 = new TemplateData();
		keyword4.setValue("本次操作需要登录电脑完成，谢谢您的配合！");
		keyword4.setColor("#173177");
		mapdata.put("remark", keyword4);

		wechatTemplate.setData(mapdata);

		UserVo userVo = testPaperDAO.getUserName(testPaper.getCheckId());
		if (Objects.isNull(userVo) || Objects.isNull(userVo.getWxOpenId())) {
			log.info("未查询到监考人信息！");
			return false;
		}
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

		return true;
	}
}
