package com.hengyu.system.web;

import static com.hengyu.common.constant.CommonConstants.ADD_FAILURE;
import static com.hengyu.common.constant.CommonConstants.ADD_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.DELETE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.DELETE_SUCCESS;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.FileUtils;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.system.entity.Watermark;
import com.hengyu.system.service.WatermarkService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 水印表 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-10-09
 */
@Api(value = "WatermarkController", tags = "水印Controller")
@RestController
@RequestMapping("/watermark")
public class WatermarkController {

	@Autowired
	private WatermarkService watermarkService;
	
	@Value("${company.upload-img-path}")
	private String uploadFilePath;
	
	@Autowired
	private FileUtils fileUtils;
	
	@Autowired
	private JWTUtils jwtUtils;

	@ApiOperation(value = "查询公司水印信息", notes = "查询公司水印信息(请求头添加token)")
	@GetMapping("queryWatermark")
	public RestResponse<Watermark> queryWatermark(@RequestHeader("token")String token) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		Watermark watermark = watermarkService.queryWatermark(info.getCompanyId());
		return new RestResponse<Watermark>().rel(true).data(watermark);
	}
	
	@ApiOperation(value = "删除公司水印信息", notes = "删除公司水印信息(请求头添加token)")
	@GetMapping("deleteWatermark")
	public RestResponse<String> deleteWatermark(@RequestHeader("token")String token) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		RestResponse<String> response = new RestResponse<String>();
		boolean flag = watermarkService.delete(new EntityWrapper<Watermark>().eq("company_id", info.getCompanyId()));
		response.rel(flag);
		if(flag){
			return response.data(DELETE_SUCCESS);
		}
		return response.message(DELETE_FAILURE);
	}
	
	@ApiOperation(value = "新增公司水印信息", notes = "新增公司水印信息(请求头添加token)")
	@ApiImplicitParam(name = "watermark", value = "水印对象", dataTypeClass = Watermark.class)
	@PostMapping("addWatermark")
	public RestResponse<String> addWatermark(@RequestHeader("token") String token,@Valid Watermark watermark,
			@RequestParam(value = "abbFile", required = false) MultipartFile abbFile) throws Exception{
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		RestResponse<String> response = new RestResponse<String>();
		//如果已经存在水印记录则删除
		Integer count = watermarkService.selectCount(new EntityWrapper<Watermark>().eq("company_id", info.getCompanyId()));
		if(count>0){
			watermarkService.delete(new EntityWrapper<Watermark>().eq("company_id", info.getCompanyId()));
		}
		// 上传图片至ftp
		if (Objects.nonNull(abbFile)) {
			String path = fileUtils.uploadFtpImg(abbFile, uploadFilePath);
			watermark.setPicture(path);
		}
		watermark.setCompanyId(info.getCompanyId());
		boolean flag = watermarkService.insert(watermark);
		response.rel(flag);
		if(flag){
			return response.data(ADD_SUCCESS);
		}
		return response.message(ADD_FAILURE);
	}
}
