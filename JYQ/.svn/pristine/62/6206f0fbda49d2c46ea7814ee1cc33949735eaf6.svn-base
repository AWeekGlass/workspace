package com.hengyu.system.web;

import static com.hengyu.common.constant.CommonConstants.ADD_FAILURE;
import static com.hengyu.common.constant.CommonConstants.ADD_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.PAGE_SIZE;
import static com.hengyu.common.constant.CommonConstants.TOKEN;
import static com.hengyu.common.constant.CommonConstants.UPDATE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.UPDATE_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.DELETE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.DELETE_SUCCESS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.system.entity.Feedback;
import com.hengyu.system.service.FeedbackService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 中介反馈表 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
@Api(value = "FeedbackController", tags = "意见反馈Controller")
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;

	@Autowired
	private JWTUtils jwtUtils;

	/**
	 * 新增意见反馈
	 * 
	 * @throws Exception
	 * 
	 */
	@ApiOperation(value = "新增意见反馈", notes = "新增意见反馈（请求头添加token）")
	@ApiImplicitParam(name = "feedback", value = "中介反馈", dataTypeClass = Feedback.class)
	@PostMapping("add")
	public RestResponse<String> add(@RequestHeader(TOKEN) String token, Feedback feedback) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		feedback.setCompanyId(info.getCompanyId());
		feedback.setAdminId(info.getUserId());
		feedback.setStatus(2);
		boolean flag = feedbackService.insert(feedback);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(ADD_SUCCESS);
		} else {
			return response.message(ADD_FAILURE);
		}
	}

	/**
	 * 查询列表
	 * @param token
	 * @param current
	 * @param size
	 * @param feedback
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "查询意见反馈列表", notes = "查询意见反馈列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "current", value = "当前页", defaultValue = "1", dataType = "Integer"),
			@ApiImplicitParam(name = "size", value = "每页行数", defaultValue = PAGE_SIZE, dataType = "Integer"),
			@ApiImplicitParam(name = "feedback", value = "查询条件", dataTypeClass = Feedback.class) })
	@GetMapping("getList")
	public RestResponse<Page<Feedback>> getList(@RequestHeader("token") String token,@RequestParam(defaultValue = "1") Integer current,
			@RequestParam(defaultValue = PAGE_SIZE) Integer size, Feedback feedback) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		feedback.setCompanyId(info.getCompanyId());
		Page<Feedback> page = new Page<>(current,size);
		page = feedbackService.getList(page, feedback);
		return new RestResponse<Page<Feedback>>().rel(true).data(page);
	}
	
	@ApiOperation(value = "查询反馈详情", notes = "查询反馈详情")
	@ApiImplicitParam(name = "id", value = "反馈id", dataTypeClass = Integer.class) 
	@GetMapping("queryById")
	public RestResponse<Feedback> queryById(Integer id){
		Feedback feedback = feedbackService.queryById(id);
		return new RestResponse<Feedback>().rel(true).data(feedback);
	}
	
	@ApiOperation(value = "修改反馈", notes = "修改反馈")
	@ApiImplicitParam(name = "feedback", value = "反馈对象", dataTypeClass = Feedback.class) 
	@GetMapping("update")
	public RestResponse<String> update(Feedback feedback){
		boolean flag = feedbackService.updateById(feedback);
		RestResponse<String> response = new RestResponse<>();
		response.rel(flag);
		if(flag){
			return response.data(UPDATE_SUCCESS);
		}
		return response.message(UPDATE_FAILURE);
	}
	
	@ApiOperation(value = "删除反馈", notes = "删除反馈")
	@ApiImplicitParam(name = "id", value = "反馈id", dataTypeClass = String.class) 
	@GetMapping("delete/{id}")
	public RestResponse<String> delete(@PathVariable String id){
		boolean flag = feedbackService.delete(id);
		RestResponse<String> response = new RestResponse<>();
		response.rel(flag);
		if(flag){
			return response.data(DELETE_SUCCESS);
		}
		return response.message(DELETE_FAILURE);
	}
}
