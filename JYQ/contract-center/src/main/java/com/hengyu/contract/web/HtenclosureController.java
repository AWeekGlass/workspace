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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.FileUtils;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.contract.entity.Htenclosure;
import com.hengyu.contract.service.HtenclosureService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 合同附件表 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-08-31
 */
@Api(value = "HtenclosureController", tags = "合同附件Controller")
@RestController
@RequestMapping("/htenclosure")
public class HtenclosureController {

	@Value("${contract.upload-img-path}")
	private String uploadPath;

	@Autowired
	FileUtils fileUtils;

	@Autowired
	private JWTUtils jwtUtils;

	@Autowired
	private HtenclosureService htenclosureService;

	/**
	 * 新增合同附件
	 * 
	 * @param htenclosure
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "新增合同附件", notes = "新增合同附件")
	@ApiImplicitParam(name = "htenclosure", value = "合同附件对象", dataTypeClass = Htenclosure.class)
	@PostMapping("/save")
	public RestResponse<String> save(@RequestHeader(TOKEN) String token,
			@RequestParam(value = "logoFile", required = true) MultipartFile logoFile, Htenclosure htenclosure)
			throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		String fileName = fileUtils.uploadFtpImg(logoFile, uploadPath);
		htenclosure.setHtpath(fileName);
		htenclosure.setName(logoFile.getOriginalFilename());
		htenclosure.setSize(fileUtils.getPrintSize(logoFile.getSize()));
		htenclosure.setCreateUser(info.getUserId());
		boolean flag = htenclosureService.save(htenclosure);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(ADD_SUCCESS);
		}
		return response.message(ADD_FAILURE);
	}

	/**
	 * 修改合同附件
	 * 
	 * @param htenclosure
	 * @return
	 */
	@ApiOperation(value = "修改合同附件", notes = "修改合同附件")
	@ApiImplicitParam(name = "htenclosure", value = "合同附件对象", dataTypeClass = Htenclosure.class)
	@PatchMapping("/update")
	public RestResponse<String> update(@RequestParam(value = "logoFile", required = true) MultipartFile logoFile,
			Htenclosure htenclosure) {
		String fileName = fileUtils.uploadFtpImg(logoFile, uploadPath);
		htenclosure.setHtpath(fileName);
		boolean flag = htenclosureService.update(htenclosure);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(UPDATE_SUCCESS);
		}
		return response.message(UPDATE_FAILURE);
	}

	/**
	 * 查询签约附件详情
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "查询合同附件详情", notes = "查询合同附件详情")
	@ApiImplicitParam(name = "id", value = "合同附件id", dataTypeClass = Integer.class)
	@GetMapping("/queryById/{id}")
	public RestResponse<Htenclosure> queryById(@PathVariable Integer id) {
		Htenclosure htenclosure = htenclosureService.queryById(id);
		return new RestResponse<Htenclosure>().rel(true).data(htenclosure);
	}

	/**
	 * 查询合同附件列表
	 * 
	 * @param htenclosure
	 * @return
	 */
	@ApiOperation(value = "查询合同附件列表", notes = "查询合同附件列表")
	@ApiImplicitParam(name = "htenclosure", value = "合同上传附参数", dataTypeClass = Htenclosure.class)
	@GetMapping("/queryList")
	public RestResponse<List<Htenclosure>> queryList(Htenclosure htenclosure) {
		List<Htenclosure> list = htenclosureService.queryList(htenclosure);
		return new RestResponse<List<Htenclosure>>().rel(true).data(list);
	}

	/**
	 * 删除合同附件
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "删除合同附件", notes = "删除合同附件")
	@ApiImplicitParam(name = "id", value = "合同附件id", dataTypeClass = Integer.class)
	@DeleteMapping("/delete/{id}")
	public RestResponse<String> delete(@PathVariable String id) {
		boolean flag = htenclosureService.delete(id);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(DELETE_SUCCESS);
		}
		return response.message(DELETE_FAILURE);
	}

}
