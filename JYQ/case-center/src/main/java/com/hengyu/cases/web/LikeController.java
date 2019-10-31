package com.hengyu.cases.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hengyu.cases.entity.Like;
import com.hengyu.cases.service.LikeService;
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
@RestController
@RequestMapping("/like")
@Api(value = "LikeController", tags = "点赞Controller")
public class LikeController {

	@Autowired
	LikeService likeService;

	@Autowired
	private JWTUtils jwtUtils;

	@ApiOperation(value = "查看点赞详情", notes = "查看案例点赞信息")
	@ApiImplicitParam(name = "caseId", value = "案例ID", required = true, dataTypeClass = Integer.class)
	@GetMapping("info/{caseId}")
	public RestResponse<List<String>> info(@PathVariable Integer caseId) {
		return new RestResponse<List<String>>().rel(true).data(likeService.queryLikeInfoByCaseId(caseId));
	}

	@ApiOperation(value = "用户点赞", notes = "用户点赞")
	@ApiImplicitParam(name = "caseId", value = "案例ID", required = true, dataTypeClass = Integer.class)
	@PostMapping("add/{caseId}")
	public RestResponse<String> add(@RequestHeader("token") String token, @PathVariable Integer caseId)
			throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		Like like = new Like();
		like.setAdminId(info.getUserId());
		like.setCaseId(caseId);
		boolean flag = likeService.insert(like);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		return flag ? response.data("点赞成功") : response.message("点赞失败");
	}

	@ApiOperation(value = "取消点赞", notes = "取消点赞")
	@ApiImplicitParam(name = "caseId", value = "案例ID", required = true, dataTypeClass = Integer.class)
	@DeleteMapping("delete/{caseId}")
	public RestResponse<String> delete(@RequestHeader("token") String token, @PathVariable Integer caseId)
			throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		likeService.deleteByCaseId(info.getUserId(), caseId);
		return new RestResponse<String>().rel(true).data("取消点赞成功");
	}
}