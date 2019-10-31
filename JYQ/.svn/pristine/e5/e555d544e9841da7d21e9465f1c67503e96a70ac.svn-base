package com.hengyu.contract.web;

import static com.hengyu.common.constant.CommonConstants.ADD_FAILURE;
import static com.hengyu.common.constant.CommonConstants.ADD_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.DELETE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.DELETE_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.TOKEN;
import static com.hengyu.common.constant.CommonConstants.UPDATE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.UPDATE_SUCCESS;

import java.util.Objects;

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

import com.baomidou.mybatisplus.plugins.Page;
import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.FileUtils;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.contract.entity.Partner;
import com.hengyu.contract.po.QueryPartnerPo;
import com.hengyu.contract.service.PartnerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 签约伴侣表 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-08-29
 */
@Api(value = "PartnerController", tags = "签约伴侣Controller")
@RestController
@RequestMapping("/partner")
public class PartnerController {
	
	@Value("${contract.upload-file-path}")
	private String uploadFilePath;
	
	@Autowired
	private PartnerService partnerService;
	
	@Autowired
	private FileUtils fileUtils; 
	
	@Autowired
	private JWTUtils jwtUtils;
	
	/**
	 * 新增签约附件
	 * @param partner
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation(value="新增签约附件",notes="新增签约附件")
	@ApiImplicitParam(name="partner",value="签约附件对象",dataTypeClass=Partner.class)
	@PostMapping("/save")
	public RestResponse<String> save(@RequestHeader(TOKEN) String token,@RequestParam(value = "logoFile", required = true) MultipartFile logoFile,Partner partner) throws Exception{
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		partner.setCompanyId(info.getCompanyId());
		//保存文件名称
		partner.setFileName(logoFile.getOriginalFilename());
		partner.setSize(fileUtils.getPrintSize(logoFile.getSize()));
		//上传文件
		String fileName = fileUtils.uploadFtpFile(logoFile,uploadFilePath);
		partner.setAttachmentAddress(fileName);
		partner.setCreateUser(info.getUserId());
		boolean flag = partnerService.save(partner);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(ADD_SUCCESS);
		} 
		return response.message(ADD_FAILURE);
	}
	
	/**
	 * 修改签约附件
	 * @param partner
	 * @return
	 */
	@ApiOperation(value="修改签约附件",notes="修改签约附件")
	@ApiImplicitParam(name="partner",value="签约附件对象",dataTypeClass=Partner.class)
	@PatchMapping("/update")
	public RestResponse<String> update(@RequestParam(value = "logoFile", required = false) MultipartFile logoFile,Partner partner){
		if(Objects.nonNull(logoFile)){
			String path = fileUtils.uploadFtpFile(logoFile, uploadFilePath);
			partner.setAttachmentAddress(path);
		}
		boolean flag = partnerService.update(partner);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(UPDATE_SUCCESS);
		}
		return response.message(UPDATE_FAILURE);
	}
	
	/**
	 * 查询签约附件详情
	 * @param id
	 * @return
	 */
	@ApiOperation(value="查询签约附件详情",notes="查询签约附件详情")
	@ApiImplicitParam(name="id",value="签约附件id",dataTypeClass=Integer.class)
	@GetMapping("/queryById/{id}")
	public RestResponse<Partner> queryById(@PathVariable Integer id){
		Partner partner = partnerService.queryById(id);
		return new RestResponse<Partner>().rel(true).data(partner);
	}
	
	/**
	 * 查询签约附件列表
	 * @param partner
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation(value="查询签约附件列表",notes="查询签约附件列表")
	@ApiImplicitParam(name="queryPartnerPo",value="插询签约附件参数",dataTypeClass=QueryPartnerPo.class)
	@GetMapping("/queryList")
	public RestResponse<Page<Partner>> queryList(@RequestHeader(TOKEN) String token,QueryPartnerPo po) throws Exception{
		Page<Partner> page = new Page<>(po.getCurrent(),po.getSize());
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		po.setCompanyId(info.getCompanyId().toString());
		page = partnerService.queryList(page, po);
		return new RestResponse<Page<Partner>>().rel(true).data(page);
	}
	
	/**
	 * 删除签约附件
	 * @param id
	 * @return
	 */
	@ApiOperation(value="删除签约附件",notes="删除签约附件")
	@ApiImplicitParam(name="id",value="签约附件id",dataTypeClass=Integer.class)
	@DeleteMapping("/delete/{id}")
	public RestResponse<String> delete(@PathVariable String id){
		boolean flag = partnerService.delete(id);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(DELETE_SUCCESS);
		} 
		return response.message(DELETE_FAILURE);
	}
	
}

