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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.contract.entity.ContractNum;
import com.hengyu.contract.service.ContractNumService;
import com.hengyu.contract.vo.SearchVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 合同编号配置表 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
@Api(value = "ContractNumController", tags = "合同编号配置Controller")
@RestController
@RequestMapping("/contractNum")
public class ContractNumController {

	@Autowired
	ContractNumService contractNumService;
	
	@Autowired
	private JWTUtils jwtUtils;

	/**
	 * 查询公司合同编号配置
	 * 
	 * @param contractNum
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation(value = "查询公司合同编号配置", notes = "查询公司合同编号配置")
	@ApiImplicitParam(name = "token", value = "token必填", required = true, dataTypeClass = String.class)
	@GetMapping("/queryByCompany")
	public RestResponse<ContractNum> queryByCompany(@RequestHeader(TOKEN) String token) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		Integer companyId = info.getCompanyId();
		return new RestResponse<ContractNum>().rel(true).data(contractNumService.queryByCompany(companyId));
	}

	/**
	 * 根据id删除合同编号配置
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据id删除合同编号配置", notes = "根据id删除合同编号配置(删除多个时id用','隔开)")
	@ApiImplicitParam(name = "id", value = "合同编号配置id", required = true, dataTypeClass = String.class)
	@DeleteMapping("/deleteById/{id}")
	public RestResponse<String> deleteById(@PathVariable String id) {
		boolean flag = contractNumService.deletebyId(id);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(DELETE_SUCCESS);
		}
		return response.message(DELETE_FAILURE);
	}

	/**
	 * 根据id更新合同编号配置
	 * 
	 * @param contractNum
	 * @return
	 */
	@ApiOperation(value = "根据id更新合同编号配置", notes = "根据id更新合同编号配置")
	@ApiImplicitParam(name = "contractNum", value = "合同编号配置对象", dataTypeClass = ContractNum.class)
	@PatchMapping("/update")
	public RestResponse<String> update(ContractNum contractNum) {
		boolean flag = contractNumService.update(contractNum);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(UPDATE_SUCCESS);
		}
		return response.message(UPDATE_FAILURE);
	}

	/**
	 * 新增合同编号配置
	 * 
	 * @param contractNum
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation(value = "新增合同编号配置", notes = "新增合同编号配置")
	@ApiImplicitParam(name = "contractNum", value = "合同编号配置对象", dataTypeClass = ContractNum.class)
	@PostMapping("/save")
	public RestResponse<String> save(@RequestHeader(TOKEN) String token,ContractNum contractNum) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		contractNum.setCompanyId(info.getCompanyId());
		boolean flag = contractNumService.save(contractNum);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(ADD_SUCCESS);
		}
		return response.message(ADD_FAILURE);
	}

	/**
	 * 根据id查询合同编号配置
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据id查询合同编号配置", notes = "根据id查询合同编号配置")
	@ApiImplicitParam(name = "id", value = "合同编号配置id", required = true, dataTypeClass = Integer.class)
	@GetMapping("/queryById{id}")
	public RestResponse<ContractNum> queryById(@PathVariable Integer id) {
		return new RestResponse<ContractNum>().rel(true).data(contractNumService.queryById(id));
	}

	/**
	 * 根据参数查合同参数配置列表(包含公司信息)
	 * 
	 * @param contractNum
	 * @return
	 */
	@ApiOperation(value = "根据参数查合同参数配置列表(包含公司信息)", notes = "根据参数查合同参数配置列表(包含公司信息)")
	@ApiImplicitParam(name = "contractNum", value = "合同编号配置对象", dataTypeClass = ContractNum.class)
	@PostMapping("/queryByParams")
	public RestResponse<List<SearchVo>> queryByParams(ContractNum contractNum) {
		List<SearchVo> list = contractNumService.queryByParams(contractNum);
		return new RestResponse<List<SearchVo>>().rel(true).data(list);
	}

	/**
	 * 根据参数查合同参数配置列表(包含公司信息)
	 * 
	 * @param contractNum
	 * @return
	 */
	@ApiOperation(value = "根据参数查合同参数配置列表(包含公司信息)", notes = "根据参数查合同参数配置列表(包含公司信息)")
	@ApiImplicitParam(name = "contractNum", value = "合同编号配置对象", dataTypeClass = ContractNum.class)
	@PostMapping("/queryList")
	public RestResponse<List<ContractNum>> queryList(ContractNum contractNum) {
		List<ContractNum> list = contractNumService.queryList(contractNum);
		return new RestResponse<List<ContractNum>>().rel(true).data(list);
	}
}
