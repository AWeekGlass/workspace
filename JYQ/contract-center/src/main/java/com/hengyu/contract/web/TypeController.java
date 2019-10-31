package com.hengyu.contract.web;


import static com.hengyu.common.constant.CommonConstants.ADD_FAILURE;
import static com.hengyu.common.constant.CommonConstants.ADD_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.DELETE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.DELETE_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.UPDATE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.UPDATE_SUCCESS;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.contract.entity.Type;
import com.hengyu.contract.service.TypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  类型前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-08-31
 */
@Api(value = "TypeController", tags = "类型Controller")
@RestController
@RequestMapping("/type")
public class TypeController {
	
	@Autowired
	private TypeService typeService;
	
	/**
	 * 新增类型
	 * @param type
	 * @return
	 */
	@ApiOperation(value="新增类型",notes="新增类型")
	@ApiImplicitParam(name="type",value="类型对象",dataTypeClass=Type.class)
	@PostMapping("/save")
	public RestResponse<String> save(Type type){
		boolean flag = typeService.insert(type);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(ADD_SUCCESS);
		} 
		return response.message(ADD_FAILURE);
	}
	
	/**
	 * 修改类型
	 * @param type
	 * @return
	 */
	@ApiOperation(value="修改类型",notes="修改类型")
	@ApiImplicitParam(name="type",value="类型对象",dataTypeClass=Type.class)
	@PostMapping("/update")
	public RestResponse<String> update(Type type){
		boolean flag = typeService.updateById(type);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(UPDATE_SUCCESS);
		}
		return response.message(UPDATE_FAILURE);
	}
	
	/**
	 * 删除类型
	 * @param type
	 * @return
	 */
	@ApiOperation(value="删除类型",notes="删除类型")
	@ApiImplicitParam(name="type",value="类型id",dataTypeClass=String.class)
	@DeleteMapping("/delete/{id}")
	public RestResponse<String> delete(@PathVariable String id){
		RestResponse<String> response = new RestResponse<String>();
		if(!ObjectUtils.isEmpty(id) && !id.isEmpty()){
			List<String> ids = Arrays.asList(id.split(","));
			if(ids!=null && !ids.isEmpty()){
				boolean flag = typeService.deleteBatchIds(ids);
				if (flag) {
					return response.data(DELETE_SUCCESS).rel(true);
				} 
				return response.message(DELETE_FAILURE).rel(false);
			}
		}
		return response.message(DELETE_FAILURE).rel(false);
	}
	
	/**
	 * 查询类型详情
	 * @param id
	 * @return
	 */
	@ApiOperation(value="查询类型详情",notes="查询类型详情")
	@ApiImplicitParam(name="type",value="类型对象",dataTypeClass=Integer.class)
	@GetMapping("/queryById/{id}")
	public RestResponse<Type> queryById(@PathVariable Integer id){
		Type type = typeService.selectById(id);
		return new RestResponse<Type>().rel(true).data(type);
	}
	
	/**
	 * 查询类型列表
	 * @param type
	 * @return
	 */
	@ApiOperation(value="查询类型列表",notes="查询类型列表")
	@ApiImplicitParam(name="type",value="类型对象",dataTypeClass=Type.class)
	@GetMapping("/queryList")
	public RestResponse<List<Type>> queryList(Type type){
		List<Type> list = typeService.selectList(new EntityWrapper<Type>(type));
		return new RestResponse<List<Type>>().rel(true).data(list);
	}
	
	/**
	 * 查询合同类型列表
	 * @return
	 */
	@ApiOperation(value="查询合同类型列表",notes="查询合同类型列表")
	@GetMapping("/queryContractType")
	public RestResponse<List<Type>> queryContractType(){
		List<Type> list = typeService.selectList(new EntityWrapper<Type>().in("id", "1,2,3"));
		return new RestResponse<List<Type>>().rel(true).data(list);
	}

}

