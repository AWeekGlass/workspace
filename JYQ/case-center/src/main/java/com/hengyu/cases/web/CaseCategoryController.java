package com.hengyu.cases.web;

import static com.hengyu.common.constant.CommonConstants.DELETE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.DELETE_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.OPRATION_FAILURE;
import static com.hengyu.common.constant.CommonConstants.OPRATION_SUCCESS;

import java.util.List;
import java.util.Objects;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hengyu.cases.dao.CaseCategoryDAO;
import com.hengyu.cases.entity.Case;
import com.hengyu.cases.entity.CaseCategory;
import com.hengyu.cases.po.Resource;
import com.hengyu.cases.service.CaseCategoryService;
import com.hengyu.cases.service.CaseService;
import com.hengyu.cases.vo.CaseCategoryVo;
import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 
 * </p>
 * 
 * @author hongyuan
 * @since 2018年9月6日
 * .
 * .
 * 
 * 0.
 * @version
 *
 */
@RestController
@RequestMapping("/caseCategory")
@Api(value = "CaseCategoryController", tags = "案例分类Controller")
public class CaseCategoryController {
	@Autowired
	private CaseCategoryService caseCategoryService;
	
	@Autowired
	private CaseService caseService;
	
	@Autowired
	private CaseCategoryDAO categoryDAO;

	@Autowired
	private JWTUtils jwtUtils;

	@ApiOperation(value = "查询案例分类", notes = "查询案例分类")
	@GetMapping("queryAll")
	public RestResponse<List<CaseCategoryVo>> queryAll(@RequestHeader("token") String token) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		return new RestResponse<List<CaseCategoryVo>>().rel(true)
				.data(caseCategoryService.queryAllByCompanyId(info.getCompanyId()));
	}

	@ApiOperation(value = "新增或修改案例分类", notes = "新增或修改案例分类")
	@ApiImplicitParam(name = "CaseCategory", value = "案例分类对象", dataTypeClass = CaseCategory.class)
	@PostMapping("insertOrUpdate")
	public RestResponse<String> insertOrUpdate(@RequestHeader("token") String token,
			@RequestBody List<CaseCategory> caseCategorys) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		caseCategorys.forEach(caseCategory -> caseCategory.setCompanyId(info.getCompanyId()));
		boolean flag = true;
		for (CaseCategory caseCategory : caseCategorys) {
			//增加角色管理里面分类
			Resource resource = new Resource();
			//如果该分类有id则修改资源表中分类名称
			if(Objects.nonNull(caseCategory.getId())) {
				resource.setRootId(caseCategory.getId());
				resource.setName(caseCategory.getName());
				Integer count = categoryDAO.updateSource(resource);
				flag = caseCategoryService.updateById(caseCategory);
			}else {//新增资源分类
				flag = caseCategoryService.insert(caseCategory);
				//设置为案例下分类
				resource.setParentId(22);
				resource.setRootId(caseCategory.getId());
				resource.setName(caseCategory.getName());
				//设置该分类为公司论坛下分类
				resource.setCategoryId(5);
				categoryDAO.insertSource(resource);
				Resource resource1 = new Resource();
				resource1.setParentId(resource.getId());
				resource1.setName("查看");
				resource1.setRootId(caseCategory.getId());
				resource1.setCategoryId(5);
				
				Resource resource2 = new Resource();
				resource2.setParentId(resource.getId());
				resource2.setName("置顶");
				resource2.setRootId(caseCategory.getId());
				resource2.setCategoryId(5);
				
				Resource resource3 = new Resource();
				resource3.setParentId(resource.getId());
				resource3.setName("删除");
				resource3.setRootId(caseCategory.getId());
				resource3.setCategoryId(5);
				
				categoryDAO.insertSource(resource1);
				categoryDAO.insertSource(resource2);
				categoryDAO.insertSource(resource3);
				categoryDAO.insertOrganization(resource.getId());
			}
		}
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		return flag ? response.data(OPRATION_SUCCESS) : response.message(OPRATION_FAILURE);
	}
	
	@ApiOperation(value = "案例分类删除", notes = "案例分类删除")
	@ApiImplicitParam(name = "id", value = "案例分类ID", required = true, dataTypeClass = Integer.class)
	@DeleteMapping("delete/{id}")
	public RestResponse<String> delete(@PathVariable Integer id) throws Exception {
		int cnt = caseService.selectCount(new EntityWrapper<Case>().eq("category_id", id));
		RestResponse<String> response = new RestResponse<>();
		if(cnt > 0) {
			return response.rel(false).message("分类下有案例");
		}
		//删除资源库数据
		boolean flag = caseCategoryService.deleteById(id);
		List<Integer> ids = categoryDAO.getSourceId(id);
		//删除organization表下权限分类
		for (Integer id_ : ids) {
			categoryDAO.deleteOrganization(id_);
		}
		categoryDAO.deleteSource(id);
		return flag ? response.rel(flag).data(DELETE_SUCCESS) : response.rel(flag).message(DELETE_FAILURE);
	}
}
