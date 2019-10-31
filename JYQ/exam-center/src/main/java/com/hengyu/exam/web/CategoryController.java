package com.hengyu.exam.web;

import static com.hengyu.common.constant.CommonConstants.ADD_FAILURE;
import static com.hengyu.common.constant.CommonConstants.ADD_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.DELETE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.DELETE_SUCCESS;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.common.util.JsonUtils;
import com.hengyu.exam.entity.Category;
import com.hengyu.exam.entity.Question;
import com.hengyu.exam.service.CategoryService;
import com.hengyu.exam.service.QuestionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-10-17
 */
@Api(value = "CategoryController", tags = "试题类型Controller")
@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private QuestionService questionService;

	@Autowired
	private JWTUtils jwtUtils;

	@ApiOperation(value = "新增试题类型", notes = "新增试题类型")
	@ApiImplicitParam(name = "categoryList", value = "试题类型实体", dataTypeClass = String.class)
	@PostMapping("/add")
	public RestResponse<String> add(@RequestHeader("token") String token, String categoryList) throws Exception {
		// 获取公司和个人信息
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		List<Category> categorys = JsonUtils.toArray(categoryList, Category.class);
		for (Category category : categorys) {
			category.setCompanyId(info.getCompanyId());
		}
		boolean flag = categoryService.insertOrUpdateBatch(categorys);

		RestResponse<String> response = new RestResponse<>();
		response.rel(flag);
		if (flag) {
			return response.data(ADD_SUCCESS);
		}
		return response.message(ADD_FAILURE);
	}

	@ApiOperation(value = "删除试题类型", notes = "删除试题类型")
	@ApiImplicitParam(name = "ids", value = "id集合,以','隔开", dataTypeClass = String.class)
	@DeleteMapping("/delete")
	public RestResponse<String> delete(@RequestHeader("token") String token, String ids) throws Exception {
		// 获取公司和个人信息
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		// 查询该公司总共有多少种题目类型
		Integer count = categoryService
				.selectCount(new EntityWrapper<Category>().eq("company_id", info.getCompanyId()));
		List<String> idList = Arrays.asList(ids.split(","));
		RestResponse<String> response = new RestResponse<>();
		for (String id : idList) {
			Integer num = questionService.selectCount(new EntityWrapper<Question>().eq("category", id));
			if(num>0){
				return response.rel(false).message("id是 "+num+" 的分类下存在题目，无法删除!");
			}
		}
		// 如果题目类型小于2 或者要删除的类型个数减去数据库中的类型个数小于2则返回删除失败重新删除
		if (count < 2 || (count - idList.size() < 2)) {
			return response.rel(false).message("删除失败！至少保留一种题目类型，请重新删除！");
		}
		boolean flag = categoryService.deleteBatchIds(idList);
		response.rel(flag);
		if (flag) {
			return response.data(DELETE_SUCCESS);
		}
		return response.message(DELETE_FAILURE);
	}

	@ApiOperation(value = "查询试题类型列表", notes = "查询试题类型列表")
	@GetMapping("getList")
	public RestResponse<List<Category>> getList(@RequestHeader("token") String token) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		List<Category> list = categoryService.selectList(new EntityWrapper<Category>().eq("company_id", info.getCompanyId()));
		return new RestResponse<List<Category>>().rel(true).data(list);
	}

}
