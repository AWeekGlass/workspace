package com.hengyu.exam.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.hengyu.exam.entity.TestPaperUser;
import com.hengyu.exam.vo.UserVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-10-24
 */
public interface TestPaperUserService extends IService<TestPaperUser> {

	List<UserVo> queryUser(Integer paperId);

	TestPaperUser startTest(Integer paperId, Integer userId);

	boolean updateScore(TestPaperUser testPaperUser);
}
