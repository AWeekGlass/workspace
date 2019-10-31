package com.hengyu.exam.web;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.exam.po.SubmitAnswerPo;
import com.hengyu.exam.service.TestAnswerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 考试答案 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-11-01
 */
@Api(value = "TestAnswerController", tags = "考生答案Controller")
@RestController
@RequestMapping("/testAnswer")
public class TestAnswerController {
	
	@Autowired
	private JWTUtils jwtUtils;
	
	@Autowired
	private TestAnswerService testAnswerService;

	@ApiOperation(value = "提交答案并验证选择题和填空题答案", notes = "提交答案并验证选择题和填空题答案")
	@ApiImplicitParam(name = "submitAnswerPo", value = "提交答案参数", dataTypeClass= SubmitAnswerPo.class)
	@PostMapping("submitAnswer")
	public RestResponse<String> submitAnswer(@RequestHeader("token")String token,@Valid @RequestBody SubmitAnswerPo po) throws Exception{
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		po.setUserId(info.getUserId());
		boolean flag = testAnswerService.submitAnswer(po);
		if(flag){
			return new RestResponse<String>().rel(flag).data("提交成功");
		}
		return new RestResponse<String>().rel(flag).message("提交失败");
	}
	
	@ApiOperation(value = "批阅试卷(填空题和简答题)", notes = "批阅试卷(填空题和简答题)")
	@ApiImplicitParam(name = "submitAnswerPo", value = "批阅答案参数", dataTypeClass= SubmitAnswerPo.class)
	@PostMapping("checkQuestion")
	public RestResponse<String> checkQuestion(@RequestHeader("token")String token,@Valid @RequestBody SubmitAnswerPo po) throws Exception{
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		boolean flag = testAnswerService.checkQuestion(po,info.getUserId());
		if(flag){
			return new RestResponse<String>().rel(flag).data("提交成功!");
		}
		return new RestResponse<String>().rel(flag).message("提交失败!");
	}
}

