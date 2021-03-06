package com.hengyu.cases.web;

import static com.hengyu.common.constant.CommonConstants.ADD_FAILURE;
import static com.hengyu.common.constant.CommonConstants.ADD_SUCCESS;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hengyu.cases.entity.Comment;
import com.hengyu.cases.service.CommentService;
import com.hengyu.cases.vo.CommentVo;
import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
@Api(value = "CommentController", tags = "回复Controller")
@RestController
@RequestMapping("/comment")
public class CommentController {
	@Autowired
	private JWTUtils jwtUtils;

	@Autowired
	private CommentService commentService;

	@ApiOperation(value = "查看回复信息", notes = "获取回复信息")
	@ApiImplicitParam(name = "caseId", value = "案例ID", required = true, dataTypeClass = Integer.class)
	@GetMapping("info/{caseId}")
	public RestResponse<List<CommentVo>> info(@PathVariable Integer caseId) throws Exception {
		return new RestResponse<List<CommentVo>>().rel(true).data(commentService.queryCommentByCaseId(caseId));
	}

	@ApiOperation(value = "新增评论", notes = "新增评论")
	@ApiImplicitParam(name = "Comment", value = "评论对象", dataTypeClass = Comment.class)
	@PostMapping("add")
	public RestResponse<String> add(@RequestHeader("token") String token,@RequestBody Comment comment) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		comment.setAdminId(info.getUserId());
		boolean flag = commentService.insert(comment);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		return flag ? response.data(ADD_SUCCESS) : response.message(ADD_FAILURE);
	}
}
