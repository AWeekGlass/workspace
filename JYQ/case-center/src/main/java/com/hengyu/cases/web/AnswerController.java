package com.hengyu.cases.web;

import static com.hengyu.common.constant.CommonConstants.ADD_FAILURE;
import static com.hengyu.common.constant.CommonConstants.ADD_SUCCESS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hengyu.cases.entity.Answer;
import com.hengyu.cases.entity.AnswerLike;
import com.hengyu.cases.service.AnswerLikeService;
import com.hengyu.cases.service.AnswerService;
import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 问答回答 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-12-17
 */
@Api(value = "AnswerController", tags = "回答Controller")
@RestController
@RequestMapping("/answer")
public class AnswerController {
	@Autowired
	private JWTUtils jwtUtils;

	@Autowired
	private AnswerService answerService;

	@Autowired
	private AnswerLikeService answerLikeService;
	
	@ApiOperation(value = "新增答案", notes = "新增答案")
	@ApiImplicitParam(name = "Answer", value = "答案对象", dataTypeClass = Answer.class)
	@PostMapping("add")
	public RestResponse<String> add(@RequestHeader("token") String token, @RequestBody Answer answer) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		answer.setAdminId(info.getUserId());
		answer.setCompanyId(info.getCompanyId());
		boolean flag = answerService.insert(answer);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		return flag ? response.data(ADD_SUCCESS) : response.message(ADD_FAILURE);
	}
	
	@ApiOperation(value = "答案点赞", notes = "答案点赞")
	@ApiImplicitParam(name = "id", value = "答案ID", required = true, dataTypeClass = Integer.class)
	@GetMapping("like/{id}")
	public RestResponse<String> like(@RequestHeader("token") String token, @PathVariable Integer id) throws Exception {
		answerService.updateLikeCnt(id);
		AnswerLike answerLike = new AnswerLike();
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		answerLike.setAdminId(info.getUserId());
		answerLike.setAnswerId(id);
		answerLikeService.insert(answerLike);
		return new RestResponse<String>().rel(true).data("点赞成功");
	}
}