package com.hengyu.system.web;


import static com.hengyu.common.constant.CommonConstants.ADD_FAILURE;
import static com.hengyu.common.constant.CommonConstants.ADD_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.DELETE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.DELETE_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.UPDATE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.UPDATE_SUCCESS;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.system.entity.Industry;
import com.hengyu.system.service.IndustryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 行业表 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
@Api(value = "IndustryController", tags = "行业Controller")
@RestController
@RequestMapping("/industry")
public class IndustryController {
	
	@Autowired
	private IndustryService industryService;
	
	@Autowired
	private JWTUtils jwtUtils;
	
	/**
	 * 新增行业
	 * @param industry
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation(value="新增行业",notes="新增行业")
	@ApiImplicitParam(name="industry",value="行业对象",dataTypeClass=Industry.class)
	@PostMapping("/save")
	public RestResponse<String> save(@RequestHeader("token")String token,Industry industry) throws Exception{
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		industry.setCompanyId(info.getCompanyId());
		boolean flag = industryService.save(industry);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(ADD_SUCCESS);
		} 
		return response.message(ADD_FAILURE);
	}
	
	/**
	 * 更新行业
	 * @param industry
	 * @return
	 */
	@ApiOperation(value="更新行业",notes="更新行业")
	@ApiImplicitParam(name="industry",value="行业对象",dataTypeClass=Industry.class)
	@PostMapping("/update")
	public RestResponse<String> update(Industry industry){
		boolean flag = industryService.update(industry);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(UPDATE_SUCCESS);
		}
		return response.message(UPDATE_FAILURE);
	}
	
	/**
	 * 根据id删除行业
	 * @param id
	 * @return
	 */
	@ApiOperation(value="根据id删除行业",notes="根据id删除行业")
	@ApiImplicitParam(name="id",value="模板id", required = true,dataTypeClass=String.class)
	@DeleteMapping("/delete/{id}")
	public RestResponse<String> delete(@PathVariable String id){
		boolean flag = industryService.delete(id);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(DELETE_SUCCESS);
		} 
		return response.message(DELETE_FAILURE);
	}
	
	/**
	 * 根据id查询行业
	 * @param id
	 * @return
	 */
	@ApiOperation(value="根据id查询行业",notes="根据id查询行业")
	@ApiImplicitParam(name="id",value="行业id",required=true,dataTypeClass=Integer.class)
	@GetMapping("/queryById/{id}")
	public RestResponse<Industry> queryById(@PathVariable Integer id){
		Industry industry = industryService.queryById(id);
		return new RestResponse<Industry>().rel(true).data(industry);
	}
	
	/**
	 * 查询行业列表
	 * @param industry
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation(value="查询行业列表",notes="查询行业列表")
	@ApiImplicitParam(name="industry",value="行业对象",dataTypeClass=Industry.class)
	@GetMapping("/queryList")
	public RestResponse<List<Industry>> queryList(@RequestHeader("token")String token,Industry industry) throws Exception{
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		industry.setCompanyId(info.getCompanyId());
		List<Industry> list = industryService.queryList(industry);
		return new RestResponse<List<Industry>>().rel(true).data(list);
	}
}

