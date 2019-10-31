package com.hengyu.contract.web;

import static com.hengyu.common.constant.CommonConstants.ADD_FAILURE;
import static com.hengyu.common.constant.CommonConstants.ADD_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.DELETE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.DELETE_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.TOKEN;
import static com.hengyu.common.constant.CommonConstants.UPDATE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.UPDATE_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.PREFIX_CONTRACT_TEMPLATE;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import javax.validation.Valid;

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
import com.hengyu.contract.entity.Template;
import com.hengyu.contract.po.QueryTemplatePo;
import com.hengyu.contract.service.TemplateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 中介模板表 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-08-29
 */
@Api(value = "TemplateController", tags = "模板Controller")
@RestController
@RequestMapping("/template")
public class TemplateController {

	@Value("${contract.upload-file-path}")
	private String uploadFilePath;

	@Autowired
	private TemplateService templateService;

	@Autowired
	private JWTUtils jwtUtils;

	@Autowired
	private FileUtils fileUtils;
	
	@Autowired
	private RedisUtils<String> redisUtils;

	/**
	 * 新增模板
	 * 
	 * @param template
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "新增模板", notes = "新增模板")
	@ApiImplicitParam(name = "template", value = "模板对象", dataTypeClass = Template.class)
	@PostMapping("/save")
	public RestResponse<String> save(@RequestHeader(TOKEN) String token, @RequestBody @Valid Template template)
			throws Exception {
		// 获取用户公司id
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		template.setCompanyId(info.getCompanyId());
		String address = template.getAddress();
		// 将网页信息上传ftp
		InputStream inputStream = new ByteArrayInputStream(template.getAddress().getBytes());
		String filePath = fileUtils.uploadFtpFile(inputStream, uploadFilePath, "****.html");
		template.setAddress(filePath);
		// 保存模板信息
		boolean flag = templateService.save(template);
		//如果上传成功则将网页内容放入redis
		if(flag){
			redisUtils.setValue(PREFIX_CONTRACT_TEMPLATE+template.getId().toString(), address);
		}
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(ADD_SUCCESS);
		}
		return response.message(ADD_FAILURE);
	}

	/**
	 * 更新模板
	 * 
	 * @param template
	 * @return
	 */
	@ApiOperation(value = "更新模板", notes = "更新模板")
	@ApiImplicitParam(name = "template", value = "模板对象", dataTypeClass = Template.class)
	@PatchMapping("/update")
	public RestResponse<String> update(Template template) {
		Template temp = templateService.selectById(template.getId());
		fileUtils.deleteFTPFile(temp.getAddress());
		// 将网页信息上传ftp
		InputStream inputStream = new ByteArrayInputStream(template.getAddress().getBytes());
		String filePath = fileUtils.uploadFtpFile(inputStream, uploadFilePath, "****.html");
		template.setAddress(filePath);
		boolean flag = templateService.update(template);
		//如果修改成功则将网页内容放入redis
		if(flag){
			redisUtils.setValue(PREFIX_CONTRACT_TEMPLATE+template.getId().toString(), template.getAddress());
		}
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(UPDATE_SUCCESS);
		}
		return response.message(UPDATE_FAILURE);

	}

	/**
	 * 分页查询模板列表
	 * 
	 * @param searchPo
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "分页查询模板列表", notes = "分页查询模板列表")
	@ApiImplicitParam(name = "queryTemplatePo", value = "查询列表参数", dataTypeClass = QueryTemplatePo.class)
	@GetMapping("/queryList")
	public RestResponse<Page<Template>> queryList(@RequestHeader(TOKEN) String token,@Valid QueryTemplatePo po)
			throws Exception {
		// 获取公司id
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		Integer companyId = info.getCompanyId();
		po.setCompanyId(companyId);
		Page<Template> page = new Page<>(po.getCurrent(), Integer.valueOf(po.getSize()));
		page = templateService.queryList(page, po);
		return new RestResponse<Page<Template>>().rel(true).data(page);
	}

	/**
	 * 根据id查询模板
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据id查询模板", notes = "根据id查询模板")
	@ApiImplicitParam(name = "id", value = "模板id", required = true, dataTypeClass = Integer.class)
	@GetMapping("/queryById/{id}")
	public RestResponse<Template> queryById(@PathVariable Integer id) {
		Template template;
		try {
			template = templateService.queryById(id);
			return new RestResponse<Template>().rel(true).data(template);
		} catch (IOException e) {
			e.printStackTrace();
			return new RestResponse<Template>().rel(false).message("读取文件失败");
		}
		
	}

	/**
	 * 根据id删除模板
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据id删除模板", notes = "根据id删除模板")
	@ApiImplicitParam(name = "id", value = "模板id", required = true, dataTypeClass = String.class)
	@DeleteMapping("/delete/{id}")
	public RestResponse<String> delete(@PathVariable String id) {
		boolean flag = templateService.delete(id);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(DELETE_SUCCESS);
		}
		return response.message(DELETE_FAILURE);
	}
	
	/**
	 * 根据类型查询系统模板
	 * 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation(value = "根据类型查询系统模板", notes = "根据类型查询系统模板")
	@ApiImplicitParam(name = "type", value = "模板类型", required = true, dataTypeClass = Integer.class)
	@GetMapping("/querySysTempate/{type}")
	public RestResponse<String> querySysTempate(@PathVariable Integer type,@RequestHeader(TOKEN) String token) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		Integer companyId = info.getCompanyId();
		String template = templateService.querySysTempate(type,companyId);
		if(Objects.isNull(template)){
			return new RestResponse<String>().rel(false).data("未查询到模板").message("未查询到模板");
		}
		return new RestResponse<String>().rel(true).data(template);
	}
	
}
