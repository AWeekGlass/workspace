package com.hengyu.training.web;


import static com.hengyu.common.constant.CommonConstants.ADD_FAILURE;
import static com.hengyu.common.constant.CommonConstants.ADD_SUCCESS;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.training.entity.TrainComment;
import com.hengyu.training.service.TrainCommentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 培训心得回复表 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-12-14
 */
@RestController
@RequestMapping("/trainComment")
@Api(value = "TrainCommentController", tags = "培训心得评论Controller")
public class TrainCommentController {
	
	@Autowired
	private JWTUtils jwtUtils;
	
	@Autowired
	private TrainCommentService traComService;

	@ApiOperation(value = "新增回复", notes = "新增回复")
	@ApiImplicitParam(name = "trainComment", value = "回复实体", required = true, dataTypeClass = Integer.class)
	@PostMapping("add")
	public RestResponse<String> add(@RequestHeader("token") String token,@RequestBody TrainComment trainComment) throws Exception{
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		if(Objects.nonNull(trainComment.getParentId())){
			trainComment.setType(2);
		}else {
			trainComment.setType(1);
		}
		trainComment.setCompanyId(info.getCompanyId());
		trainComment.setUserId(info.getUserId());
		boolean flag = traComService.insert(trainComment);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		return flag ? response.data(ADD_SUCCESS) : response.message(ADD_FAILURE);
	}
	
	@ApiOperation(value = "查询回复树", notes = "查询回复树")
	@ApiImplicitParam(name = "trainComment", value = "回复实体", required = true, dataTypeClass = Integer.class)
	@GetMapping("getCommentTree")
	public RestResponse<List<TrainComment>> getCommentTree(@RequestHeader("token") String token,Integer id) throws Exception{
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		return new RestResponse<List<TrainComment>>().rel(true).data(traComService.getCommentTree(info.getCompanyId(), id));
	}
	
}

