package com.hengyu.contract.web;

import static com.hengyu.common.constant.CommonConstants.ADD_FAILURE;
import static com.hengyu.common.constant.CommonConstants.ADD_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.DELETE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.DELETE_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.PREFIX_CONTRACT_CONTRACT;
import static com.hengyu.common.constant.CommonConstants.UPDATE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.UPDATE_SUCCESS;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Objects;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.FileUtils;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.common.util.RedisUtils;
import com.hengyu.common.web.BaseController;
import com.hengyu.contract.entity.Contract;
import com.hengyu.contract.po.AddcontractPo;
import com.hengyu.contract.po.SearchPo;
import com.hengyu.contract.service.ContractService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 中介合同表 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
@Api(value = "ContractController", tags = "合同Controller")
@RestController
@RequestMapping("/contract")
public class ContractController extends BaseController {

	@Value("${contract.upload-file-path}")
	private String uploadFilePath;

	@Autowired
	ContractService contractService;

	@Autowired
	private JWTUtils jwtUtils;

	@Autowired
	private FileUtils fileUtils;
	
	@Autowired
	private RedisUtils<String> redisUtils;

	/**
	 * 新增合同
	 * 
	 * @param addcontractPo
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "新增合同", notes = "新增合同")
	@ApiImplicitParam(name = "addcontractPo", value = "新增和通参数", dataTypeClass = AddcontractPo.class)
	@PostMapping("/save")
	public RestResponse<String> save(@RequestHeader("token") String token,
			@RequestBody @Valid AddcontractPo addcontractPo) throws Exception {
		Contract contract = addcontractPo.getContract();
		// 获取公司和个人信息
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		contract.setCompanyId(info.getCompanyId());
		contract.setRecordDate(new Date());
		contract.setMainUserId(info.getUserId());
		// 在线生成
		contract.setEntryMode(4);
		String path = "" ;
		// 将网页信息上传ftp
		if(Objects.nonNull(contract.getPath())){
			path = contract.getPath();
			InputStream inputStream = new ByteArrayInputStream(path.getBytes());
			String filePath = fileUtils.uploadFtpFile(inputStream, uploadFilePath, "****.html");
			// 将文件路径保存
			if (StringUtils.isNoneEmpty(filePath)) {
				contract.setPath(filePath);
			}
		}
		addcontractPo.setContract(contract);
		boolean flag = contractService.save(addcontractPo);
		//如果上传成功则将网页内容放入redis
		if(flag){
			redisUtils.setValue(PREFIX_CONTRACT_CONTRACT+contract.getId().toString(), path);
		}
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(ADD_SUCCESS);
		}
		return response.message(ADD_FAILURE);
	}

	/**
	 * 根据条件查询最新合同
	 * 
	 * @param contract
	 * @return
	 */
	@ApiOperation(value = "根据条件查询最新合同", notes = "根据条件查询最新合同")
	@ApiImplicitParam(name = "contract", value = "合同对象", dataTypeClass = Contract.class)
	@PostMapping("/getContractByMax")
	public RestResponse<Contract> getContractByMax(Contract contract) {
		Contract contract1 = contractService.getContractByMax(contract);
		return new RestResponse<Contract>().rel(true).data(contract1);
	}

	/**
	 * 更新合同
	 * 
	 * @param contract
	 * @return
	 */
	@ApiOperation(value = "更新合同", notes = "更新合同")
	@ApiImplicitParam(name = "contract", value = "合同对象", dataTypeClass = Contract.class)
	@PatchMapping("/updateContract")
	public RestResponse<String> updateContract(Contract contract) {
		boolean flag = contractService.updateContract(contract);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(UPDATE_SUCCESS);
		}
		return response.message(UPDATE_FAILURE);

	}

	/**
	 * 分页查询合同列表
	 * 
	 * @param searchPo
	 * @return
	 */
	@ApiOperation(value = "分页查询合同列表", notes = "分页查询合同列表")
	@ApiImplicitParam(name = "searchPo", value = "查询列表参数", dataTypeClass = SearchPo.class)
	@GetMapping("/selectContractList")
	public RestResponse<Page<Contract>> selectContractList(@RequestHeader("token") String token, SearchPo searchPo) {
		Page<Contract> page = new Page<>(searchPo.getCurrent(), searchPo.getSize());
		try {
			// 获取公司和个人信息
			IJWTInfo info = jwtUtils.getInfoFromToken(token);
			page = contractService.selectContractList(page, searchPo);
		} catch (Exception e) {
			e.printStackTrace();
			return new RestResponse<Page<Contract>>().rel(false).message("读取文件内容失败");
		}
		return new RestResponse<Page<Contract>>().rel(true).data(page);
	}

	/**
	 * 根据id查询合同
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据id查询合同", notes = "根据id查询合同")
	@ApiImplicitParam(name = "id", value = "合同id")
	@GetMapping("/queryById/{id}")
	public RestResponse<Contract> queryById(@PathVariable Integer id) {
		Contract contract;
		try {
			contract = contractService.queryById(id);
		} catch (IOException e) {
			e.printStackTrace();
			return new RestResponse<Contract>().rel(false).message("读取文件内容失败");
		}
		return new RestResponse<Contract>().rel(true).data(contract);
	}

	/**
	 * 根据id删除合同
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据id删除合同", notes = "根据id删除合同")
	@ApiImplicitParam(name = "id", value = "合同id", required = true, dataTypeClass = String.class)
	@DeleteMapping("/deleteById/{id}")
	public RestResponse<String> deleteById(@PathVariable String id) {
		boolean flag = contractService.deleteByID(id);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(DELETE_SUCCESS);
		}
		return response.message(DELETE_FAILURE);
	}
}
