package com.hengyu.exam.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
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
import com.hengyu.common.util.SmsUtils;
import com.hengyu.exam.dao.QuestionDAO;
import com.hengyu.exam.dao.TestPaperDAO;
import com.hengyu.exam.dao.TestPaperQuestionDAO;
import com.hengyu.exam.dao.TestPaperSubjectDAO;
import com.hengyu.exam.dao.TestPaperUserDAO;
import com.hengyu.exam.entity.TestPaper;
import com.hengyu.exam.entity.TestPaperQuestion;
import com.hengyu.exam.entity.TestPaperSubject;
import com.hengyu.exam.entity.TestPaperUser;
import com.hengyu.exam.po.QuerPaperDetailPo;
import com.hengyu.exam.po.QueryPastPo;
import com.hengyu.exam.po.TestPaperSubordinatePo;
import com.hengyu.exam.service.DictionaryService;
import com.hengyu.exam.service.TestPaperService;
import com.hengyu.exam.vo.AdminVo;
import com.hengyu.exam.vo.QuestionsVo;
import com.hengyu.exam.vo.SendMessageVo;
import com.hengyu.exam.vo.StateVo;
import com.hengyu.exam.vo.TestPaperFullVo;
import com.hengyu.exam.vo.TestPaperSubordinateVo;
import com.hengyu.exam.vo.TestPaperVo;
import com.hengyu.exam.vo.TestUserVo;
import com.hengyu.exam.vo.UserVo;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-09-04
 */
@Slf4j
@Service
public class TestPaperServiceImpl extends ServiceImpl<TestPaperDAO, TestPaper> implements TestPaperService {
	@Autowired
	private TestPaperDAO testPaperDAO;

/*	@Autowired
	private TestAnswerDAO testAnswerDAO;*/

	@Autowired
	private QuestionDAO questionDAO;

	@Autowired
	private TestPaperUserDAO testPaperUserDAO;

	@Autowired
	private TestPaperSubjectDAO testPaperSubjectDAO;

	@Autowired
	private DictionaryService dictionaryService;

	@Autowired
	private TestPaperQuestionDAO testPaperQuestionDAO;

	@Value("${paper.send_message_tpl_id}")
	private String messageTplId;

	@Value("${paper.send_message}")
	private boolean sendMessage;

	@Autowired
	private SmsUtils smsUtils;
	
	@Value("${wx.appid}")
	private String appId;
	
	@Value("${wx.url}")
	private String url;

	@Value("${wx.secret}")
	private String secret;

	private final String REQUES_TURL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

	@Override
	public boolean updateDelFlag(List<String> ids) {
		List<TestPaper> list = ids.stream().map(id -> {
			TestPaper testPaper = new TestPaper();
			testPaper.setId(Integer.parseInt(id));
			testPaper.setDelFlag(1);
			return testPaper;
		}).collect(Collectors.toList());
		return updateBatchById(list);
	}

	@Override
	public TestPaperFullVo queryById(Integer id) {
		Integer score = testPaperDAO.score(id);
		TestPaperFullVo vo = testPaperDAO.queryById(id);
		if (Objects.nonNull(vo)) {
			vo.setScore(score);
		}
		return vo;
	}

	@Override
	public Page<TestPaperVo> queryList(Page<TestPaperVo> page, TestPaper testPaper) {
		List<TestPaperVo> list = testPaperDAO.queryList(page, testPaper);
		// 查询参考率和及格率
		if (testPaper.getIsExam() == 1) {
			for (TestPaperVo testPaperVo : list) {
				// 查询参考率
				TestUserVo testUserVo = testPaperDAO.getTestedRate(testPaperVo.getId());
				testPaperVo.setTestedRate(testUserVo.getPassRate());
				// 查询已经批阅完成试卷的及格率
				if (Objects.equals(testPaperVo.getCheckState(), 2)) {
					TestUserVo testUser = testPaperUserDAO.getPassRate(testPaperVo.getId());
					testPaperVo.setPassRate(testUser.getPassRate());
				}
			}
		}

		return page.setRecords(list);
	}

	@Override
	public boolean save(TestPaper testPaper) {
		boolean flag = insert(testPaper);
		if (!flag) {
			return flag;
		}
		List<Integer> userIds = testPaper.getUserIds();
		Integer paperId = testPaper.getId();

		//新增考试人员记录
		if (!(Objects.isNull(userIds) || userIds.isEmpty())) {
			userIds.forEach(userId -> {
				TestPaperUser testPaperUser = new TestPaperUser();
				testPaperUser.setPaperId(paperId);
				testPaperUser.setUserId(userId);
				testPaperUserDAO.insert(testPaperUser);
			});
		}
		sendWXMessage(testPaper);
		switch (testPaper.getType()) {
		case 1:
			List<String[]> subjectList = testPaper.getSubjectList();
			String[] titles = subjectList.get(0);
			int length = titles.length;
			List<Integer> subjectIds = dictionaryService.querySubject();
			TestPaperSubject testPaperSubject = null;
			for (int i = 0, temp = subjectIds.size(); i < temp; i++) {
				String[] contents = subjectList.get(i + 1);
				for (int j = 0, temp2 = contents.length - 1; j < temp2; j++) {
					if (StringUtils.isNoneEmpty(contents[j])) {
						Integer category = Integer.parseInt(titles[j]);
						Integer score = Integer.parseInt(contents[length]);
						Integer subjectId = subjectIds.get(i);
						Integer num = Integer.parseInt(contents[j]);
						testPaperSubject = new TestPaperSubject();
						testPaperSubject.setCategoryId(category);
						testPaperSubject.setSubjectId(subjectId);
						testPaperSubject.setNum(num);
						testPaperSubject.setScore(score);
						testPaperSubject.setPaperId(paperId);
						testPaperSubjectDAO.insert(testPaperSubject);

						List<Integer> questionIds = questionDAO.queryQuestions(subjectId, category, num);
						if (!(Objects.isNull(questionIds) || questionIds.isEmpty())) {
							questionIds.stream().forEach(questionId -> {
								TestPaperQuestion testPaperQuestion = new TestPaperQuestion();
								testPaperQuestion.setQuestionId(questionId);
								testPaperQuestion.setRecord(score);
								testPaperQuestion.setPaperId(paperId);
								testPaperQuestionDAO.insert(testPaperQuestion);
							});
						}
					}
				}
			}
			break;
		case 2:
			List<TestPaperQuestion> testPaperQuestions = testPaper.getQuestionIds();

			if (!(Objects.isNull(testPaperQuestions) || testPaperQuestions.isEmpty())) {
				List<TestPaperSubject> testPaperSubjects = questionDAO.queryQuestionInfo(
						testPaperQuestions.stream().map(TestPaperQuestion::getQuestionId).collect(Collectors.toList()));
				for (TestPaperSubject entity : testPaperSubjects) {
					entity.setPaperId(paperId);
					entity.setScore(testPaperQuestions.stream()
							.filter(temp -> Objects.equals(temp.getQuestionId(), entity.getId())).findFirst().get()
							.getRecord());
					testPaperSubjectDAO.insert(entity);
				}

				testPaperQuestions.stream().forEach(testPaperQuestion -> {
					testPaperQuestion.setPaperId(paperId);
					testPaperQuestionDAO.insert(testPaperQuestion);
				});
			}
		default:
			break;
		}

		return flag;
	}

	@Override
	public Page<TestPaperVo> queryMyList(Page<TestPaperVo> page, Integer userId, Integer state) {
		page.setRecords(testPaperDAO.queryMyList(page, userId, state));
		return page;
	}

	@Override
	public boolean update(TestPaper testPaper) {
		boolean flag = updateById(testPaper);
		if (!flag) {
			return flag;
		}
		List<Integer> userIds = testPaper.getUserIds();
		Integer paperId = testPaper.getId();

		testPaperUserDAO.delete(new EntityWrapper<TestPaperUser>().eq("paper_id", paperId));
		if (!(Objects.isNull(userIds) || userIds.isEmpty())) {
			userIds.forEach(userId -> {
				TestPaperUser testPaperUser = new TestPaperUser();
				testPaperUser.setPaperId(paperId);
				testPaperUser.setUserId(userId);
				testPaperUserDAO.insert(testPaperUser);
			});
		}
		switch (testPaper.getType()) {
		case 1:
			testPaperSubjectDAO.delete(new EntityWrapper<TestPaperSubject>().eq("paper_id", paperId));
			testPaperQuestionDAO.delete(new EntityWrapper<TestPaperQuestion>().eq("paper_id", paperId));
			List<String[]> subjectList = testPaper.getSubjectList();
			String[] titles = subjectList.get(0);
			int length = titles.length;
			List<Integer> subjectIds = dictionaryService.querySubject();
			TestPaperSubject testPaperSubject = null;
			for (int i = 0, temp = subjectIds.size(); i < temp; i++) {
				String[] contents = subjectList.get(i + 1);
				for (int j = 0, temp2 = contents.length - 1; j < temp2; j++) {
					if (StringUtils.isNoneEmpty(contents[j])) {
						Integer category = Integer.parseInt(titles[j]);
						Integer score = Integer.parseInt(contents[length]);
						Integer subjectId = subjectIds.get(i);
						Integer num = Integer.parseInt(contents[j]);
						testPaperSubject = new TestPaperSubject();
						testPaperSubject.setCategoryId(category);
						testPaperSubject.setSubjectId(subjectId);
						testPaperSubject.setNum(num);
						testPaperSubject.setScore(score);
						testPaperSubject.setPaperId(paperId);
						testPaperSubjectDAO.insert(testPaperSubject);

						List<Integer> questionIds = questionDAO.queryQuestions(subjectId, category, num);
						if (!(Objects.isNull(questionIds) || questionIds.isEmpty())) {
							questionIds.stream().forEach(questionId -> {
								TestPaperQuestion testPaperQuestion = new TestPaperQuestion();
								testPaperQuestion.setQuestionId(questionId);
								testPaperQuestion.setRecord(score);
								testPaperQuestion.setPaperId(paperId);
								testPaperQuestionDAO.insert(testPaperQuestion);
							});
						}
					}
				}
			}
			break;
		case 2:
			testPaperQuestionDAO.delete(new EntityWrapper<TestPaperQuestion>().eq("paper_id", paperId));
			List<TestPaperQuestion> testPaperQuestions = testPaper.getQuestionIds();
			if (!(Objects.isNull(testPaperQuestions) || testPaperQuestions.isEmpty())) {
				testPaperQuestions.stream().forEach(testPaperQuestion -> {
					testPaperQuestion.setPaperId(paperId);
					testPaperQuestionDAO.insert(testPaperQuestion);
				});
			}
		default:
			break;
		}
		return flag;
	}

	@Override
	public boolean sendMessage(Integer paperId, String userId) {
		// 发送短信
		boolean flag = true;
		// 更新试卷状态
		TestPaper testPaper = new TestPaper();
		testPaper.setId(paperId);
		testPaper.setIsSmsNotice(1);
		testPaperDAO.updateById(testPaper);
		// 查询系统是否发送短信
		// 发送短信
		if (sendMessage && Objects.nonNull(userId) && userId != "") {
			// 查询公司是否发送短信
			SendMessageVo sendMessageVo = testPaperDAO.sendMessage(Integer.valueOf(userId));
			if (!Objects.equals(sendMessageVo.getShortMessage(), "0")
					&& !Objects.equals(sendMessageVo.getTestMessage(), "0")) {
				try {
					List<String> userList = Arrays.asList(userId.split(","));
					for (String user : userList) {
						String phone = testPaperDAO.getPhone(user);
						flag = smsUtils.sendSms(phone, messageTplId, null);
					}
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}

		}
		return flag;
	}

	@Override
	public String getPhone(String userId) {
		return testPaperDAO.getPhone(userId);
	}

	@Override
	public TestPaperFullVo queyDetailByUser(QuerPaperDetailPo po) {
		po.setId(po.getId());
		// 查询试卷信息
		TestPaperFullVo vo = testPaperDAO.queyDetailByUser(po);
		if (Objects.isNull(vo)) {
			return vo;
		}
		// 根据试卷和用户id查询用户答案和题目
		List<QuestionsVo> questionsVo = questionDAO.queryTypeChecked(po.getPaperId(), po.getUserId());
		vo.setQuestionTypes(questionsVo);
		// 查询总分和及格率和参考率
		TestPaperUser paperUser = new TestPaperUser();
		paperUser.setUserId(po.getUserId());
		paperUser.setPaperId(po.getPaperId());
		paperUser = testPaperUserDAO.queryTestPaperUser(paperUser);
		if (Objects.nonNull(paperUser)) {
			vo.setSum(paperUser.getScore());
		}
		vo.setCheckUserName(paperUser.getCheckName());
		TestUserVo testUserVo = testPaperUserDAO.getPassRate(po.getPaperId());
		if (Objects.nonNull(testUserVo.getPassRate())) {
			vo.setPassRateTest(testUserVo.getPassRate());
		}
		TestUserVo UserVo = testPaperDAO.getTestedRate(po.getPaperId());
		if (Objects.nonNull(UserVo.getPassRate())) {
			vo.setTested(UserVo.getPassRate());
		}
		return vo;
	}

	@Override
	public Page<TestPaperVo> queryCheckList(Page<TestPaperVo> page, Integer userId, Integer type) {
		List<TestPaperVo> list = testPaperDAO.queryCheckList(page, userId, type);
		// 查询参考人数
		for (TestPaperVo testPaperVo : list) {
			// 清空及格率
			testPaperVo.setPassRate(null);
			// 查询已经批阅完成试卷的及格率
			if (testPaperVo.getState() != null && testPaperVo.getState() == 2) {
				TestUserVo testUserVo = testPaperUserDAO.getPassRate(testPaperVo.getId());
				testPaperVo.setPassRate(testUserVo.getPassRate());
			}
			// 查询参考率
			TestUserVo testUserVo = testPaperDAO.getTestedRate(testPaperVo.getId());
			testPaperVo.setTestedRate(testUserVo.getPassRate());
		}
		return page.setRecords(list);
	}

	@Override
	public Page<TestPaperSubordinateVo> querySubordinate(Page<TestPaperSubordinateVo> page,
			TestPaperSubordinatePo testPaperSubordinatePo) {
		return page.setRecords(testPaperDAO.querySubordinate(page, testPaperSubordinatePo));
	}

	@Override
	public Page<TestPaperVo> queryPastList(Page<TestPaperVo> page, QueryPastPo queryPastPo) {
		List<Integer> userList = new ArrayList<>();
		if (Objects.nonNull(queryPastPo.getDptId())) {
			userList = testPaperUserDAO.getUserList(queryPastPo.getCompanyId(), queryPastPo.getDptId());
		}
		if (Objects.nonNull(queryPastPo.getUserId())) {
			userList.add(queryPastPo.getUserId());
		}
		if (Objects.nonNull(userList) && userList.size() > 0) {
			queryPastPo.setIds(testPaperUserDAO.getPaperId(userList));
		}
		List<TestPaperVo> list = testPaperDAO.queryPastList(page, queryPastPo);
		for (TestPaperVo testPaperVo : list) {
			// 先将及格率清空
			testPaperVo.setPassRate(null);
			// 查询已经批阅完成试卷的及格率
			TestUserVo testUserVo = testPaperUserDAO.getPassRate(testPaperVo.getId());
			testPaperVo.setPassRate(testUserVo.getPassRate());
			// 参与率
			TestUserVo testUserVo_ = testPaperDAO.getTestedRate(testPaperVo.getId());
			testPaperVo.setTestedRate(testUserVo_.getPassRate());
		}
		return page.setRecords(list);
	}

	@Override
	public Page<TestPaperVo> queryMyExamList(Page<TestPaperVo> page, TestPaper testPaper) {
		return page.setRecords(testPaperDAO.queryMyExamList(page, testPaper));
	}

	@Override
	public List<StateVo> getTestState(Integer paperId, Integer type) {
		List<StateVo> list = new ArrayList<>();
		if (type == 1) {
			list = testPaperDAO.getTestUser(paperId);
		} else {
			list = testPaperDAO.getPassUser(paperId);
		}
		return list;
	}

	@Override
	public List<AdminVo> queryPaperUser(Integer companyId) {
		return testPaperDAO.queryPaperUser(companyId);
	}

	@Override
	public Page<TestPaperVo> queryTodo(Page<TestPaperVo> page, Integer userId) {
		return page.setRecords(testPaperDAO.queryTodo(page,userId));
	}

	@Override
	public Integer countUserInfo(Integer userId) {
		
		return testPaperDAO.countUserInfo(userId);
	}
	
	public boolean sendWXMessage(TestPaper testPaper){
	    List<Integer> list = testPaper.getUserIds();
	    log.info("userList:{}",list);
	    if(Objects.isNull(list)){
	    	return false;
	    }
		// 获取基础支持的access_token
		String GET_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ appId +"&secret="+ secret;
	    
		String json = HttpRequest.sendGet(GET_ACCESS_TOKEN, null);
	    JSONObject jsonObject = JSONObject.parseObject(json);
	    String access_token = jsonObject.get("access_token").toString();
	    log.info("access_token:{}",access_token);
	    
	    // 发送模板消息
	    String resultUrl2 = REQUES_TURL+access_token;
	    // 封装基础数据
	    WechatTemplate wechatTemplate = new WechatTemplate();
	    //模板id(申请加入企业)
	    wechatTemplate.setTemplate_id("iKzuV9kjL0zAxRAENp2E9sxkN5m-0elaINIcSzwob9M");
	    log.info("模板id:{}","iKzuV9kjL0zAxRAENp2E9sxkN5m-0elaINIcSzwob9M");
	    //跳转详情URL
	    wechatTemplate.setUrl(url);
	    
	    Map<String,TemplateData> mapdata = new HashMap<>();
	    // 封装模板数据
	    TemplateData first = new TemplateData();
	    first.setValue("考试通知提醒");
	    first.setColor("#173177");
	    mapdata.put("first", first);
	    
	    TemplateData keyword1 = new TemplateData();
	    keyword1.setValue("内部考试");
	    keyword1.setColor("#173177");
	    mapdata.put("keyword1", keyword1);
	    
	    TemplateData keyword2 = new TemplateData();
	    keyword2.setValue(testPaper.getName());
	    keyword2.setColor("#173177");
	    mapdata.put("keyword2", keyword2);
	    
	    Date d = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    TemplateData keyword3 = new TemplateData();
	    keyword3.setValue(sdf.format(d));
	    keyword3.setColor("#173177");
	    mapdata.put("keyword3", keyword3);
	    
	    TemplateData keyword4 = new TemplateData();
	    keyword4.setValue("本次操作需要登录电脑完成，谢谢您的配合！");
	    keyword4.setColor("#173177");
	    mapdata.put("remark", keyword4);
	    
	    wechatTemplate.setData(mapdata);
	    
	    for (Integer id : list) {
	    	UserVo userVo = testPaperDAO.getUserName(id);
	    	if(Objects.isNull(userVo) || Objects.isNull(userVo.getWxOpenId())){
	    		  continue;
	    	}
	    	//发送用户openid
	    	log.info("用户open_id:{}",userVo.getWxOpenId());
		    wechatTemplate.setTouser(userVo.getWxOpenId());
		    String toString = JsonUtils.toJsonString(wechatTemplate);
		    String json2 = HttpRequest.sendPost(resultUrl2, toString);
		    JSONObject jsonObject2 = JSONObject.parseObject(json2);
		    log.info("jsonObject2:{}",jsonObject2);
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