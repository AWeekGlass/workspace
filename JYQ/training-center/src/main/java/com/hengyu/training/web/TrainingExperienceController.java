package com.hengyu.training.web;

import static com.hengyu.common.constant.CommonConstants.ADD_FAILURE;
import static com.hengyu.common.constant.CommonConstants.ADD_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.DELETE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.DELETE_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.PAGE_SIZE;
import static com.hengyu.common.constant.CommonConstants.UPDATE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.UPDATE_SUCCESS;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.plugins.Page;
import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.FileUtils;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.training.entity.TrainingExperience;
import com.hengyu.training.entity.TrainingLike;
import com.hengyu.training.service.TrainingExperienceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 培训心得 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-12-13
 */
@RestController
@RequestMapping("/trainingExperience")
@Api(value = "TrainingExperienceController", tags = "培训心得Controller")
public class TrainingExperienceController {

	@Autowired
	private TrainingExperienceService traExpService;

	@Value("${training.upload-img-path}")
	private String uploadPath;

	@Autowired
	FileUtils fileUtils;

	@Autowired
	private JWTUtils jwtUtils;

	@ApiOperation(value = "根据类型查询列表", notes = "查看培训心得列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "caseCategory", value = "案例分类ID", required = true, dataTypeClass = Integer.class),
			@ApiImplicitParam(name = "current", value = "页码", defaultValue = "1", dataTypeClass = Integer.class),
			@ApiImplicitParam(name = "size", value = "每页条数", defaultValue = PAGE_SIZE, dataType = "Integer"),
			@ApiImplicitParam(name = "type", value = "查询类型 1根据条件查询列表 2查询我的发表", defaultValue = "1", dataType = "Integer") })
	@GetMapping("queryAll")
	public RestResponse<Page<TrainingExperience>> queryAll(@RequestHeader("token") String token,
			@NotNull Integer caseCategory, @RequestParam(defaultValue = "1") Integer current,
			@RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "1") Integer type)
			throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		TrainingExperience trainingExperience = new TrainingExperience();
		trainingExperience.setCaseCategory(caseCategory);
		trainingExperience.setCompanyId(info.getCompanyId());
		trainingExperience.setUserId(info.getUserId());
		trainingExperience.setType(type);
		Page<TrainingExperience> page = new Page<>(current, size);
		return new RestResponse<Page<TrainingExperience>>().rel(true)
				.data(traExpService.queryAllByCategoryId(page, trainingExperience));
	}

	@ApiOperation(value = "查询草稿", notes = "查询草稿")
	@GetMapping("queryDraft")
	public RestResponse<List<TrainingExperience>> queryDraft(@RequestHeader("token") String token) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		Integer companyId = info.getCompanyId();
		return new RestResponse<List<TrainingExperience>>().rel(true).data(traExpService.queryDraft(companyId));
	}

	@ApiOperation(value = "查询培训心得详情", notes = "查询培训心得详情")
	@ApiImplicitParam(name = "id", value = "案例ID", required = true, dataTypeClass = Integer.class)
	@GetMapping("detail/{id}")
	public RestResponse<TrainingExperience> detail(@RequestHeader("token") String token, @PathVariable Integer id)
			throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		return new RestResponse<TrainingExperience>().rel(true).data(traExpService.detail(id, info.getUserId()));
	}

	@ApiOperation(value = "新增培训心得", notes = "新增培训心得")
	@ApiImplicitParam(name = "TrainingExperience", value = "培训心得对象", dataTypeClass = TrainingExperience.class)
	@PostMapping("add")
	public RestResponse<String> add(@RequestHeader("token") String token, @RequestBody @Valid TrainingExperience t)
			throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		t.setUserId(info.getUserId());
		t.setCompanyId(info.getCompanyId());
		boolean flag = traExpService.insert(t);
		traExpService.sendWXMessage(t);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		return flag ? response.data(ADD_SUCCESS) : response.message(ADD_FAILURE);
	}

	@ApiOperation(value = "培训心得删除", notes = "培训心得删除")
	@ApiImplicitParam(name = "id", value = "培训心得ID", required = true, dataTypeClass = String.class)
	@DeleteMapping("delete/{id}")
	public RestResponse<String> delete(@PathVariable String id) throws Exception {
		boolean flag = traExpService.delete(id);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		return flag ? response.data(DELETE_SUCCESS) : response.message(DELETE_FAILURE);
	}

	@ApiOperation(value = "培训心得修改", notes = "培训心得编辑功能")
	@ApiImplicitParam(name = "TrainingExperience", value = "培训心得对象", required = true, dataTypeClass = TrainingExperience.class)
	@PostMapping("update")
	public RestResponse<String> update(@RequestBody TrainingExperience t) {
		boolean flag = traExpService.updateById(t);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		return flag ? response.data(UPDATE_SUCCESS) : response.message(UPDATE_FAILURE);
	}

	@ApiOperation(value = "培训心得置顶", notes = "培训心得置顶功能")
	@ApiImplicitParam(name = "id", value = "培训心得ID", required = true, dataTypeClass = Integer.class)
	@GetMapping("updateTop/{id}")
	public RestResponse<String> updateTop(@RequestHeader("token") String token, @PathVariable Integer id)
			throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		TrainingExperience trainingExp = traExpService.selectById(id);
		RestResponse<String> response = new RestResponse<String>();
		// 如果已经置顶则取消置顶
		if (Objects.nonNull(trainingExp.getTopId()) || Objects.nonNull(trainingExp.getTopTime())) {
			trainingExp.setTopId(null);
			trainingExp.setTopTime(null);
			boolean flag = traExpService.updateAllColumnById(trainingExp);
			return flag ? response.rel(flag).data("取消成功") : response.rel(flag).message("取消失败");
		}
		trainingExp.setId(id);
		trainingExp.setTopTime(new Date());
		trainingExp.setTopId(info.getUserId());
		boolean flag = traExpService.updateById(trainingExp);
		return flag ? response.rel(flag).data("置顶成功") : response.rel(flag).message("置顶失败");
	}

	@ApiOperation(value = "推荐培训心得", notes = "推荐培训心得")
	@ApiImplicitParam(name = "id", value = "培训心得ID", required = true, dataTypeClass = Integer.class)
	@GetMapping("updateRef/{id}")
	public RestResponse<String> updateRef(@RequestHeader("token") String token, @PathVariable Integer id)
			throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		TrainingExperience trainingExp = traExpService.selectById(id);
		RestResponse<String> response = new RestResponse<String>();
		// 如果已经推荐则取消推荐
		if (Objects.nonNull(trainingExp.getRefId()) || Objects.nonNull(trainingExp.getRecommend())) {
			trainingExp.setRefId(null);
			trainingExp.setRecommend(null);
			boolean flag = traExpService.updateAllColumnById(trainingExp);
			return flag ? response.rel(flag).data("取消成功") : response.rel(flag).message("取消失败");
		}
		trainingExp.setRefId(info.getUserId());
		trainingExp.setRecommend(1);
		trainingExp.setId(id);
		boolean flag = traExpService.updateById(trainingExp);

		return flag ? response.rel(flag).data("推荐成功") : response.rel(flag).message("推荐失败");
	}

	/**
	 * 上传图片
	 * 
	 * @param htenclosure
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "上传图片", notes = "上传图片")
	@ApiImplicitParam(name = "logoFile", value = "图片对象", dataTypeClass = MultipartFile.class)
	@PostMapping("/uploadIMG")
	public RestResponse<String> uploadIMG(MultipartFile logoFile) {
		return new RestResponse<String>().rel(true).data(fileUtils.uploadFtpImg(logoFile, uploadPath));
	}
	
	/**
	 * 获取点赞人数
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "获取点赞人数", notes = "获取点赞人数")
	@ApiImplicitParam(name = "id", value = "图片对象", dataTypeClass = Integer.class)
	@GetMapping("getLike/{id}")
	public RestResponse<List<TrainingLike>> getLike(@PathVariable Integer id) {
		return new RestResponse<List<TrainingLike>>().rel(true).data(traExpService.getLike(id));
	}

}
