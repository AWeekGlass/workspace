package com.hengyu.cases.web;

import static com.hengyu.common.constant.CommonConstants.ADD_FAILURE;
import static com.hengyu.common.constant.CommonConstants.ADD_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.PAGE_SIZE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.hengyu.cases.entity.Question;
import com.hengyu.cases.service.QuestionService;
import com.hengyu.cases.vo.QuestionVo;
import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-12-14
 */
@Api(value = "QuestionController", tags = "提问Controller")
@RestController
@RequestMapping("/question")
public class QuestionController {
	@Autowired
	private JWTUtils jwtUtils;

	@Autowired
	private QuestionService questionService;

	@ApiOperation(value = "新增提问", notes = "新增提问")
	@ApiImplicitParam(name = "Question", value = "提问对象", dataTypeClass = Question.class)
	@PostMapping("add")
	public RestResponse<String> add(@RequestHeader("token") String token, @RequestBody Question question)
			throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		question.setAdminId(info.getUserId());
		question.setCompanyId(info.getCompanyId());
		boolean flag = questionService.insert(question);
		questionService.sendWXMessage(question);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		return flag ? response.data(ADD_SUCCESS) : response.message(ADD_FAILURE);
	}

	@ApiOperation(value = "查询提问列表", notes = "查询提问列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "current", value = "当前页", defaultValue = "1", dataType = "Integer"),
			@ApiImplicitParam(name = "size", value = "每页行数", defaultValue = PAGE_SIZE, dataType = "Integer"),
			@ApiImplicitParam(name = "type", value = "类型（1：所有 ；2：当前人）", defaultValue = "1", dataType = "Integer") })
	@GetMapping("queryAll")
	public RestResponse<Page<QuestionVo>> queryAll(@RequestHeader("token") String token,
			@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = PAGE_SIZE) Integer size,
			@RequestParam(defaultValue = "1") Integer type) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		Page<QuestionVo> page = null;
		if (1 == type) {
			page = questionService.queryAll(new Page<QuestionVo>(current, size), info.getCompanyId(), null);
		} else {
			Integer companyId = info.getCompanyId();
			Integer userId = info.getUserId();
			page = questionService.queryAll(new Page<QuestionVo>(current, size), companyId, userId);
		}
		return new RestResponse<Page<QuestionVo>>().rel(true).data(page);
	}

	@ApiOperation(value = "查询提问详情", notes = "查询提问详情")
	@ApiImplicitParam(name = "id", value = "提问ID", required = true, dataTypeClass = Integer.class)
	@GetMapping("detail/{id}")
	public RestResponse<QuestionVo> detail(@RequestHeader("token") String token, @PathVariable Integer id)
			throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		Integer userId = info.getUserId();
		return new RestResponse<QuestionVo>().rel(true).data(questionService.queryDetailById(userId, id));
	}
}
