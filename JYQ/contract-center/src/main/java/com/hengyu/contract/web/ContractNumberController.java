package com.hengyu.contract.web;

import static com.hengyu.common.constant.CommonConstants.ADD_FAILURE;
import static com.hengyu.common.constant.CommonConstants.ADD_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.DELETE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.DELETE_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.TOKEN;
import static com.hengyu.common.constant.CommonConstants.UPDATE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.UPDATE_SUCCESS;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.contract.entity.ContractNumber;
import com.hengyu.contract.service.ContractNumberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 合同编号表 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
@Api(value = "ContractNumberController", tags = "合同编号Controller")
@RestController
@RequestMapping("/contractNumber")
public class ContractNumberController {

	@Autowired
	private JWTUtils jwtUtils;
	
	@Autowired
	private ContractNumberService contractNumberService;

	@ApiOperation(value = "新增合同编号", notes = "新增合同编号")
	@ApiImplicitParam(name = "ContractNumber", value = "合同对象", dataTypeClass = ContractNumber.class)
	@PostMapping("/save")
	public RestResponse<String> save(@RequestHeader(TOKEN) String token,ContractNumber contractNumber) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		contractNumber.setCompanyId(info.getCompanyId());
		boolean flag = contractNumberService.save(contractNumber);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(ADD_SUCCESS);
		} else {
			return response.message(ADD_FAILURE);
		}
	}
	
	/**
	 * 修改合同编号
	 * @param contractNumber
	 * @return
	 */
	@ApiOperation(value = "修改合同编号", notes = "修改合同编号")
	@ApiImplicitParam(name = "ContractNumber", value = "合同对象", dataTypeClass = ContractNumber.class)
	@PatchMapping("/update")
	public RestResponse<String> update(@RequestBody ContractNumber contractNumber) {
		boolean flag = contractNumberService.update(contractNumber);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(UPDATE_SUCCESS);
		}
		return response.message(UPDATE_FAILURE);
	}
	
	/**
	 * 删除合同编号
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "删除合同编号", notes = "删除合同编号")
	@ApiImplicitParam(name = "id", value = "合同编号ID", required = true, dataTypeClass = String.class)
	@DeleteMapping("delete/{id}")
	public RestResponse<String> delete(@PathVariable String id){
		boolean flag = contractNumberService.delete(id);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(DELETE_SUCCESS);
		} else {
			return response.message(DELETE_FAILURE);
		}
	}
	
	/**
	 * 查询合同编号详情
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "查询合同编号详情", notes = "查询合同编号详情")
	@ApiImplicitParam(name = "id", value = "合同编号ID", required = true, dataTypeClass = String.class)
	@GetMapping("queryById/{id}")
	public RestResponse<ContractNumber> queryById(@PathVariable String id){
		ContractNumber contractNumber = contractNumberService.queryById(id);
		return new RestResponse<ContractNumber>().rel(true).data(contractNumber);
	}
	
	/**
	 * 查询全部合同编号
	 * @param contractNumber
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation(value = "查询全部合同编号", notes = "查询全部合同编号")
	@ApiImplicitParam(name = "contractNumber", value = "合同编号对象", dataTypeClass = ContractNumber.class)
	@GetMapping("/queryList")
	public RestResponse<List<ContractNumber>> queryList(@RequestHeader(TOKEN) String token,ContractNumber contractNumber) throws Exception{
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		contractNumber.setCompanyId(info.getCompanyId());
		List<ContractNumber> list = contractNumberService.queryList(contractNumber);
		return new RestResponse<List<ContractNumber>>().rel(true).data(list);
	}
	
	/**
	 * 根据合同类型查询最大编号
	 * @param contractNumber
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation(value = "根据条件查询最新信息", notes = "根据条件查询最新信息")
	@ApiImplicitParam(name = "type", value = "合同类型", dataTypeClass = Integer.class)
	@GetMapping("/getHtNumberByMax/{type}")
	public RestResponse<String> getHtNumberByMax(@RequestHeader(TOKEN) String token,@PathVariable Integer type) throws Exception{
		ContractNumber contractNumber = new ContractNumber();
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		contractNumber.setCompanyId(info.getCompanyId());
		contractNumber.setType(type);
		String number = contractNumberService.getHtNumberByMax(contractNumber);
		return new RestResponse<String>().rel(true).data(number);
	}
	
	@ApiOperation(value = "修改合同编号使用状态", notes = "修改合同编号使用状态")
	@ApiImplicitParam(name = "ContractNumber", value = "合同对象", dataTypeClass = ContractNumber.class)
	@PatchMapping("/updateStatus")
	public RestResponse<String> updateStatus(ContractNumber contractNumber){
		boolean flag = contractNumberService.updateStatus(contractNumber);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(UPDATE_SUCCESS);
		}
		return response.message(UPDATE_FAILURE);
	}
	
}
