package com.hengyu.training.web;

import static com.hengyu.common.constant.CommonConstants.DELETE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.DELETE_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.OPRATION_FAILURE;
import static com.hengyu.common.constant.CommonConstants.OPRATION_SUCCESS;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.training.entity.TrainCategory;
import com.hengyu.training.entity.TrainingExperience;
import com.hengyu.training.service.TrainCategoryService;
import com.hengyu.training.service.TrainingExperienceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 培训心得类别 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-12-26
 */
@RestController
@RequestMapping("/trainCategory")
@Api(value = "TrainCategoryController", tags = "培训心得分类Controller")
public class TrainCategoryController {

	@Autowired
	private TrainCategoryService categoryService;
	
	@Autowired
	private TrainingExperienceService traExpService;

	@Autowired
	private JWTUtils jwtUtils;

	@ApiOperation(value = "查询案例分类", notes = "查询案例分类")
	@GetMapping("queryAll")
	public RestResponse<List<TrainCategory>> queryAll(@RequestHeader("token") String token) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		List<TrainCategory> list = categoryService
				.selectList(new EntityWrapper<TrainCategory>().eq("company_id", info.getCompanyId()));
		return new RestResponse<List<TrainCategory>>().rel(true).data(list);
	}

	@ApiOperation(value = "新增或修改案例分类", notes = "新增或修改案例分类")
	@ApiImplicitParam(name = "CaseCategory", value = "案例分类对象", dataTypeClass = TrainCategory.class)
	@PostMapping("insertOrUpdate")
	public RestResponse<String> insertOrUpdate(@RequestHeader("token") String token,
			@RequestBody List<TrainCategory> caseCategorys) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		caseCategorys.forEach(caseCategory -> caseCategory.setCompanyId(info.getCompanyId()));
		boolean flag = categoryService.insertOrUpdateBatch(caseCategorys);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		return flag ? response.data(OPRATION_SUCCESS) : response.message(OPRATION_FAILURE);
	}

	@ApiOperation(value = "案例分类删除", notes = "案例分类删除")
	@ApiImplicitParam(name = "id", value = "案例分类ID", required = true, dataTypeClass = Integer.class)
	@DeleteMapping("delete/{id}")
	public RestResponse<String> delete(@PathVariable Integer id) throws Exception {
		int cnt = traExpService.selectCount(new EntityWrapper<TrainingExperience>().eq("case_category", id));
		RestResponse<String> response = new RestResponse<>();
		if (cnt > 0) {
			return response.rel(false).message("分类下有案例");
		}
		boolean flag = categoryService.deleteById(id);
		return flag ? response.rel(flag).data(DELETE_SUCCESS) : response.rel(flag).message(DELETE_FAILURE);
	}

}
