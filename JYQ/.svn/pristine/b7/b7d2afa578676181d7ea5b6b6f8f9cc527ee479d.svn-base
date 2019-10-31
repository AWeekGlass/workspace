package com.hengyu.exam.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.exam.dao.TestAnswerDAO;
import com.hengyu.exam.dao.TestPaperDAO;
import com.hengyu.exam.dao.TestPaperUserDAO;
import com.hengyu.exam.entity.TestPaper;
import com.hengyu.exam.entity.TestPaperUser;
import com.hengyu.exam.service.TestPaperService;
import com.hengyu.exam.service.TestPaperUserService;
import com.hengyu.exam.vo.UserVo;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-10-24
 */
@Service
public class TestPaperUserServiceImpl extends ServiceImpl<TestPaperUserDAO, TestPaperUser>
		implements TestPaperUserService {

	@Autowired
	private TestPaperDAO testPaperDAO;

	@Autowired
	private TestPaperService paperService;

	@Autowired
	private TestAnswerDAO testAnswerDAO;

	@Autowired
	private TestPaperUserDAO testPaperUserDAO;

	@Override
	public List<UserVo> queryUser(Integer paperId) {
		return testPaperDAO.getUsers(paperId);
	}

	@Override
	public TestPaperUser startTest(Integer paperId, Integer userId) {
		TestPaperUser testPaperUser = selectOne(
				new EntityWrapper<TestPaperUser>().eq("paper_id", paperId).eq("user_id", userId));
		TestPaper paper = paperService.selectById(paperId);
		// 如果开始考试时间为空，则开始考试
		if (Objects.isNull(testPaperUser.getStartTime())) {
			// 设置开始时间和结束时间
			Calendar c = Calendar.getInstance();
			c.setTime(new Date()); // 设置时间
			testPaperUser.setStartTime(c.getTime());
			c.add(Calendar.MINUTE, paper.getTimeLength()); // 日期分钟加1,Calendar.DATE(天),Calendar.HOUR(小时)
			Date date = c.getTime(); // 结果
			testPaperUser.setEndTime(date);
			updateById(testPaperUser);
			return testPaperUser;
		}
		testPaperUser.setStartTime(new Date());
		return testPaperUser;
	}

	@Override
	public boolean updateScore(TestPaperUser testPaperUser) {
		int paperId = testPaperUser.getPaperId();
		int sum = testAnswerDAO.querySumScore(paperId);
		
		testPaperUser.setId(testPaperUserDAO.selectOne(testPaperUser).getId());
		testPaperUser.setScore(sum);
		// 设置为已经批阅
		testPaperUser.setIsCheck(1);

		Integer score = testPaperDAO.score(paperId);
		TestPaper paper = paperService.selectById(paperId);
		if (Objects.nonNull(paper.getPassRate()) && Objects.nonNull(score)) {
			// 如果分数大约几个分数
			if (Integer.valueOf(paper.getPassRate() / 100 * score) > sum) {
				testPaperUser.setPass(1);
			} else {
				testPaperUser.setPass(0);
			}
		}
		return updateById(testPaperUser);
	}

}
