package com.hengyu.user.web;


import static com.hengyu.common.constant.CommonConstants.ADD_FAILURE;
import static com.hengyu.common.constant.CommonConstants.ADD_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.DELETE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.DELETE_SUCCESS;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.user.entity.Transfer;
import com.hengyu.user.service.TransferService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 人员调动 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-09-29
 */
@Api(value = "TransferController", tags = "调动Controller")
@RestController
@RequestMapping("/transfer")
public class TransferController {
	
	@Autowired
	private TransferService transferService;
	
	@Autowired
	private JWTUtils jwtUtils;
	
	@ApiOperation(value = "新增人员调动", notes = "新增人员调动")
	@ApiImplicitParam(name = "transfer", value = "人员调动对象", dataTypeClass = Transfer.class)
	@PostMapping("add")
	public RestResponse<String> add(@RequestHeader("token") String token,@RequestBody @Valid Transfer transfer) throws Exception{
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		transfer.setOperator(info.getUserId());
		RestResponse<String> response = new RestResponse<>();
		boolean flag = transferService.add(transfer);
		response.rel(flag);
		if(flag){
			return response.data(ADD_SUCCESS);
		}
		return response.message(ADD_FAILURE);
	}
	
	@ApiOperation(value = "删除调动记录", notes = "删除调动记录")
	@ApiImplicitParam(name = "transfer", value = "人员调动对象", dataTypeClass = String.class)
	@DeleteMapping("delete/{id}")
	public RestResponse<String> delete(@PathVariable String id){
		RestResponse<String> response = new RestResponse<>();
		boolean flag = transferService.delete(id);
		response.rel(flag);
		if(flag){
			return response.data(DELETE_SUCCESS);
		}
		return response.message(DELETE_FAILURE);
	}
	
	@ApiOperation(value = "查询列表", notes = "查询列表")
	@ApiImplicitParam(name = "userId", value = "人员调动对象", dataTypeClass = Integer.class)
	@GetMapping("getList")
	public RestResponse<List<Transfer>> getList(Integer userId){
		return new RestResponse<List<Transfer>>().data(transferService.getList(userId)).rel(true);
	}
}

