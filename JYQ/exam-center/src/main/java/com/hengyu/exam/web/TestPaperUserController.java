package com.hengyu.exam.web;

import static com.hengyu.common.constant.CommonConstants.UPDATE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.UPDATE_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.TOKEN;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.exam.entity.TestPaperUser;
import com.hengyu.exam.service.TestPaperUserService;
import com.hengyu.exam.vo.UserVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-10-24
 */
@RestController
@RequestMapping("/testPaperUser")
@Api(value = "TestPaperUserController", tags = "考试人员Controller")
public class TestPaperUserController {

	@Autowired
	private JWTUtils jwtUtils;

	@Autowired
	private TestPaperUserService testPaperUserService;

	@ApiOperation(value = "获取考试人员", notes = "获取考试人员")
	@ApiImplicitParam(name = "paperId", value = "试卷id", dataTypeClass = Integer.class)
	@GetMapping("queryUsr/{paperId}")
	public RestResponse<List<UserVo>> queryUser(@PathVariable Integer paperId) {
		return new RestResponse<List<UserVo>>().rel(true).data(testPaperUserService.queryUser(paperId));
	}

	@ApiOperation(value = "开始考试", notes = "开始考试")
	@ApiImplicitParam(name = "paperId", value = "试卷id", dataTypeClass = Integer.class)
	@GetMapping("startTest/{paperId}")
	public RestResponse<TestPaperUser> startTest(@RequestHeader(TOKEN) String token, @PathVariable Integer paperId)
			throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		TestPaperUser paperUser = testPaperUserService.startTest(paperId, info.getUserId());
		return new RestResponse<TestPaperUser>().rel(true).data(paperUser);
	}

	@ApiOperation(value = "更新分数", notes = "更新分数")
	@ApiImplicitParam(name = "paperId", value = "试卷id", dataTypeClass = Integer.class)
	@PostMapping("updateScore/{paperId}")
	public RestResponse<String> updateScore(@RequestHeader(TOKEN) String token, @PathVariable Integer paperId)
			throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		TestPaperUser testPaperUser = new TestPaperUser();
		testPaperUser.setUserId(info.getUserId());
		testPaperUser.setPaperId(paperId);
		boolean flag = testPaperUserService.updateScore(testPaperUser);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(UPDATE_SUCCESS);
		} else {
			return response.message(UPDATE_FAILURE);
		}
	}
}
