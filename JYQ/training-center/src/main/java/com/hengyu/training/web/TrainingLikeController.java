package com.hengyu.training.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.training.entity.TrainingLike;
import com.hengyu.training.service.TrainingLikeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 培训心得点赞表 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-12-13
 */
@RestController
@RequestMapping("/trainingLike")
@Api(value = "TrainingLikeController", tags = "培训心得点赞Controller")
public class TrainingLikeController {

	@Autowired
	private TrainingLikeService trainingLikeService;

	@Autowired
	private JWTUtils jwtUtils;

	@ApiOperation(value = "点赞", notes = "点赞")
	@ApiImplicitParam(name = "id", value = "培训心得ID", required = true, dataTypeClass = Integer.class)
	@GetMapping("like/{id}")
	public RestResponse<String> like(@RequestHeader("token") String token, @PathVariable Integer id) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		RestResponse<String> response = new RestResponse<String>();
		
		TrainingLike trainingLike = new TrainingLike();
		trainingLike.setUserId(info.getUserId());
		trainingLike.setTrainingExpId(id);
		trainingLike.setCompanyId(info.getCompanyId());
		//查询是否已经点赞
		Integer count = trainingLikeService.selectCount(
				new EntityWrapper<TrainingLike>().eq("user_id", info.getUserId()).eq("training_exp_id", id));
		if(count>0){//取消点赞
			boolean flag =  trainingLikeService.delete(new EntityWrapper<TrainingLike>(trainingLike));
			return flag ? response.rel(flag).data("取消成功") : response.rel(flag).message("取消失败");
		}
		//点赞
		trainingLike.setCreateTime(new Date());
		boolean flag = trainingLikeService.insert(trainingLike);
		return flag ? response.rel(flag).data("点赞成功") : response.rel(flag).message("点赞失败");
	}

}
