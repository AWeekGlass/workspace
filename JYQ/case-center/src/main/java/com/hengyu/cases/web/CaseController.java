package com.hengyu.cases.web;

import static com.hengyu.common.constant.CommonConstants.ADD_FAILURE;
import static com.hengyu.common.constant.CommonConstants.ADD_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.DELETE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.DELETE_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.PAGE_SIZE;
import static com.hengyu.common.constant.CommonConstants.UPDATE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.UPDATE_SUCCESS;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.hengyu.cases.entity.Case;
import com.hengyu.cases.service.CaseService;
import com.hengyu.cases.vo.CaseInfoVo;
import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * -
 * <p>
 * - 中介案例表 前端控制器 -
 * </p>
 *
 * - @author allnas
 */
@RestController
@RequestMapping("/case")
@Api(value = "CaseController", tags = "案例中心Controller")
public class CaseController {
	@Autowired
	private CaseService caseService;

	@Autowired
	private JWTUtils jwtUtils;

	@ApiOperation(value = "根据案例类型查询列表", notes = "查看案例列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "current", value = "当前页", defaultValue = "1", dataType = "Integer"),
			@ApiImplicitParam(name = "size", value = "每页行数", defaultValue = PAGE_SIZE, dataType = "Integer"),
			@ApiImplicitParam(name = "categoryId", value = "案例分类ID", required = true, dataTypeClass = Integer.class) })
	@GetMapping("queryAll")
	public RestResponse<Page<CaseInfoVo>> queryAll(@RequestHeader("token") String token,
			@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = PAGE_SIZE) Integer size,
			@RequestParam(value = "categoryId", defaultValue = "", required = true) Integer categoryId)
			throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		Integer companyId = info.getCompanyId();
		Integer userId = info.getUserId();
		return new RestResponse<Page<CaseInfoVo>>().rel(true).data(
				caseService.queryAllByCategoryId(new Page<CaseInfoVo>(current, size), companyId, categoryId, userId));
	}

	@ApiOperation(value = "查询草稿", notes = "查询草稿")
	@GetMapping("queryDraft")
	public RestResponse<List<CaseInfoVo>> queryDraft(@RequestHeader("token") String token) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		Integer companyId = info.getCompanyId();
		return new RestResponse<List<CaseInfoVo>>().rel(true).data(caseService.queryDraft(companyId));
	}

	@ApiOperation(value = "查询当前案例详情", notes = "查询案例信息")
	@ApiImplicitParam(name = "id", value = "案例ID", required = true, dataTypeClass = Integer.class)
	@GetMapping("detail/{id}")
	public RestResponse<CaseInfoVo> detail(@PathVariable Integer id) throws Exception {
		return new RestResponse<CaseInfoVo>().rel(true).data(caseService.queryCaseById(id));
	}

	@ApiOperation(value = "新增案例", notes = "新增案例")
	@ApiImplicitParam(name = "Case", value = "案例对象", dataTypeClass = Case.class)
	@PostMapping("add")
	public RestResponse<String> add(@RequestHeader("token") String token, Case c) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		c.setAdminId(info.getUserId());
		c.setCompanyId(info.getCompanyId());
		boolean flag = caseService.insert(c);
		caseService.sendWXMessage(c);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		return flag ? response.data(ADD_SUCCESS) : response.message(ADD_FAILURE);
	}

	@ApiOperation(value = "案例删除", notes = "案例删除")
	@ApiImplicitParam(name = "id", value = "案例ID", required = true, dataTypeClass = String.class)
	@DeleteMapping("delete/{id}")
	public RestResponse<String> delete(@PathVariable String id) throws Exception {
		boolean flag = caseService.deleteBatchIds(Arrays.asList(id.split(",")));
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		return flag ? response.data(DELETE_SUCCESS) : response.message(DELETE_FAILURE);
	}

	@ApiOperation(value = "案例修改", notes = "案例编辑功能")
	@ApiImplicitParam(name = "Case", value = "案例对象", required = true, dataTypeClass = Case.class)
	@PostMapping("update")
	public RestResponse<String> update(Case c) {
		boolean flag = caseService.updateById(c);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		return flag ? response.data(UPDATE_SUCCESS) : response.message(UPDATE_FAILURE);
	}

	@ApiOperation(value = "案例置顶", notes = "案例置顶功能")
	@ApiImplicitParam(name = "id", value = "案例ID", required = true, dataTypeClass = Integer.class)
	@GetMapping("updateTop/{id}")
	public RestResponse<String> updateTop(@RequestHeader("token") String token, @PathVariable Integer id)
			throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		caseService.updateTop(info.getUserId(), id);
		return new RestResponse<String>().rel(true).data("置顶成功");
	}
	
	@ApiOperation(value = "取消案例置顶", notes = "取消案例置顶功能")
	@ApiImplicitParam(name = "id", value = "案例ID", required = true, dataTypeClass = Integer.class)
	@GetMapping("cancelTop/{id}")
	public RestResponse<String> cancelTop(@RequestHeader("token") String token, @PathVariable Integer id)
			throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		caseService.cancelTop(info.getUserId(), id);
		return new RestResponse<String>().rel(true).data("取消置顶成功");
	}

	@ApiOperation(value = "案例推荐", notes = "案例推荐功能")
	@ApiImplicitParam(name = "id", value = "案例ID", required = true, dataTypeClass = Integer.class)
	@GetMapping("updateRef/{id}")
	public RestResponse<String> updateRef(@RequestHeader("token") String token, @PathVariable Integer id)
			throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		caseService.updateRef(info.getUserId(), id);
		return new RestResponse<String>().rel(true).data("推荐成功");
	}

	@ApiOperation(value = "取消案例推荐", notes = "取消案例推荐功能")
	@ApiImplicitParam(name = "id", value = "案例ID", required = true, dataTypeClass = Integer.class)
	@GetMapping("cancelRef/{id}")
	public RestResponse<String> cancelRef(@RequestHeader("token") String token, @PathVariable Integer id)
			throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		caseService.cancelRef(info.getUserId(), id);
		return new RestResponse<String>().rel(true).data("推荐成功");
	}
}