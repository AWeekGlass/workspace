package com.hengyu.system.web;

import static com.hengyu.common.constant.CommonConstants.ADD_FAILURE;
import static com.hengyu.common.constant.CommonConstants.ADD_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.PAGE_SIZE;
import static com.hengyu.common.constant.CommonConstants.TOKEN;
import static com.hengyu.common.constant.CommonConstants.UPDATE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.UPDATE_SUCCESS;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hengyu.common.enums.ExceptionEnum;
import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.FileUtils;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.service.AdminService;
import com.hengyu.system.entity.Apply;
import com.hengyu.system.entity.Company;
import com.hengyu.system.po.CompanyPo;
import com.hengyu.system.service.ApplyService;
import com.hengyu.system.service.CompanyService;
import com.hengyu.system.vo.CompanyInfoVo;
import com.hengyu.system.vo.CompanyVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 公司信息控制器
 * 
 * @author allnas
 * @since 2018-8-21
 *
 */
@Slf4j
@Api(value = "CompanyController", tags = "公司Controller")
@RestController
@RequestMapping("/company")
public class CompanyController {
	@Value("${company.upload-img-path}")
	private String uploadFilePath;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private FileUtils fileUtils;

	@Autowired
	private JWTUtils jwtUtils;

	@Autowired
	private ApplyService applyService;

	@Autowired
	private AdminService adminService;

	/**
	 * 注册或申请试用
	 * 
	 * @param token
	 * @param addCompanyVo
	 * @param file
	 * @return
	 */
	@ApiOperation(value = "注册公司或申请试用", notes = "注册公司或申请试用（请求头添加token）")
	@PostMapping("add")
	public RestResponse<String> addCompany(@RequestHeader(TOKEN) String token, @Valid CompanyPo companyPo,
			@RequestParam(value = "file", required = true) MultipartFile file) {
		RestResponse<String> response = new RestResponse<>();
		String phone = companyPo.getPhone();
		Integer check = checkPhone(phone);
		if (!check.equals(-1)) {
			return response.rel(false).message("该手机号码已注册！");
		}
		// 上传图片至ftp
		if (Objects.nonNull(file)) {
			String path = fileUtils.uploadFtpImg(file, uploadFilePath);
			companyPo.setBusinessUrl(path);
		}
		boolean flag = companyService.addCompany(companyPo);
		if (flag) {// 新增公司用户
			return response.rel(true).data(ADD_SUCCESS);
		} else {
			return response.rel(false).message(ADD_FAILURE);
		}
	}

	@ApiOperation(value = "创建认证企业", notes = "创建认证企业")
	@ApiImplicitParams({ @ApiImplicitParam(name = "company", value = "公司信息", dataTypeClass = Company.class),
			@ApiImplicitParam(name = "files", value = "公司营业执照,公司委托书", dataTypeClass = MultipartFile.class) })
	@PostMapping("createCompany")
	public RestResponse<String> createCompany(@RequestHeader(TOKEN) String token,
			@RequestParam(value = "files", required = true) MultipartFile[] files,@Valid Company company) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		RestResponse<String> response = new RestResponse<>();
		// 验证公司名称是否重复
		List<Company> list = companyService.selectList(new EntityWrapper<Company>().eq("name", company.getName()));
		if (Objects.nonNull(list) && list.size() > 0) {
			return response.rel(false).message("企业名称重复");
		}
		// 上传图片至ftp
		if (Objects.nonNull(files) && files.length > 0) {
			String path = fileUtils.uploadFtpImg(files[0], uploadFilePath);
			// 设置营业执照
			company.setBusinessUrl(path);
			if (files.length > 1) {
				path = fileUtils.uploadFtpImg(files[1], uploadFilePath);
				company.setProxy(path);
			}
		}else {
			return response.rel(true).data("未获到取数据");
		}
		
		company.setAdminId(info.getUserId());
		boolean flag = companyService.createCompany(company);
		if (flag) {// 新增公司用户
			return response.rel(true).data("提交成功");
		} else {
			return response.rel(false).message("提交失败");
		}
	}

	@ApiOperation(value = "申请加入企业", notes = "申请加入企业")
	@ApiImplicitParams({@ApiImplicitParam(name = "companyName", value = "公司名称", dataTypeClass = String.class),
		@ApiImplicitParam(name = "userName", value = "申请人姓名", dataTypeClass = String.class)
	})
	@PostMapping("joinCompany")
	public RestResponse<String> joinCompany(@RequestHeader(TOKEN) String token, String companyName,String userName) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		return new RestResponse<String>().rel(true).data(companyService.joinCompany(info.getUserId(), companyName,userName));
	}

	@ApiOperation(value = "查询当前公司信息", notes = "获取对应公司信息（请求头添加token）")
	@GetMapping("info")
	public RestResponse<CompanyInfoVo> info(@RequestHeader(TOKEN) String token) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		return new RestResponse<CompanyInfoVo>().rel(true)
				.data(companyService.queryCompanyInfoById(info.getCompanyId()));
	}

	@ApiOperation(value = "查询当前公司详情", notes = "获取公司详情（请求头添加token）")
	@GetMapping("detailInfo")
	public RestResponse<Company> detailInfo(@RequestHeader(TOKEN) String token) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		return new RestResponse<Company>().rel(true).data(companyService.queryCompanyDetailById(info.getCompanyId()));
	}

	@ApiOperation(value = "根据ID修改公司信息", notes = "修改公司信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "公司ID", dataType = "Integer"),
			@ApiImplicitParam(name = "abb", value = "公司简称", dataType = "String") })
	@PostMapping("update")
	public RestResponse<String> update(Integer id, String abb,
			@RequestParam(value = "logoFile", required = false) MultipartFile logoFile) {
		log.info("id:{},abb:{}", id, abb);
		RestResponse<String> response = new RestResponse<>();
		if (Objects.isNull(id)) {
			return response.rel(false).message(ExceptionEnum.CLIENT_FORBIDDEN_ERROR_CODE.getDescription());
		}

		Company company = new Company();
		company.setId(id);
		company.setAbb(abb);

		// 上传图片至ftp
		if (Objects.nonNull(logoFile)) {
			String path = fileUtils.uploadFtpImg(logoFile, uploadFilePath);
			company.setLogoUrl(path);
		}

		boolean flag = companyService.updateById(company);
		if (flag) {
			return response.rel(flag).data(UPDATE_SUCCESS);
		} else {
			return response.rel(flag).message(UPDATE_FAILURE);
		}
	}

	@ApiIgnore
	@GetMapping("checkPhone")
	public Integer checkPhone(String phone) {
		log.info("phone:{}", phone);
		return adminService.checkPhone(phone);
	}

	@ApiOperation(value = "获取公司详情", notes = "获取公司详情")
	@GetMapping("detail")
	public RestResponse<CompanyVo> detail(@RequestHeader(TOKEN) String token) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		return new RestResponse<CompanyVo>().rel(true).data(companyService.queryDetailById(info.getCompanyId()));
	}

	@ApiOperation(value = "更新公司详情", notes = "更新公司详情")
	@PostMapping("updateDetail")
	public RestResponse<String> updateDetail(@RequestBody Company company) {
		boolean flag = companyService.updateById(company);

		// 修改申请状态
		Apply apply = new Apply();
		apply.setState(company.getStatus());
		if (Objects.nonNull(company.getReason()) && company.getReason() != "") {
			apply.setReason(company.getReason());
		}
		applyService.update(apply, new EntityWrapper<Apply>().eq("company_id", company.getId()));

		// 如果审核不通过则修改管理员为删除状态
		if (company.getStatus() == 3) {
			companyService.deleteAdmin(companyService.selectById(company.getId()).getTelephone());
		}
		// 如果通过审核则查询该手机号下是否有其他未通过审核的公司公司
		if (company.getStatus() == 2) {
			List<Company> list = companyService.selectList(new EntityWrapper<Company>()
					.eq("telephone", companyService.selectById(company.getId()).getTelephone()).eq("status", 3));
			// 如果该手机号下注册的公司大于0个则进行验证
			if (list.size() > 0) {
				for (Company cpany : list) {
					// 如果公司名称相同则删除前一条记录
					if (cpany.getName().equals(companyService.selectById(company.getId()).getName())) {
						companyService.deleteById(cpany.getId());
					}
				}
			}
		}

		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(UPDATE_SUCCESS);
		} else {
			return response.message(UPDATE_FAILURE);
		}
	}

	@ApiOperation(value = "查询公司列表", notes = "查询公司列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "current", value = "当前页", defaultValue = "1", dataType = "Integer"),
			@ApiImplicitParam(name = "size", value = "每页行数", defaultValue = PAGE_SIZE, dataType = "Integer"),
			@ApiImplicitParam(name = "company", value = "查询条件", dataTypeClass = Company.class) })
	@GetMapping("getList")
	public RestResponse<Page<Company>> getList(@RequestParam(defaultValue = "1") Integer current,
			@RequestParam(defaultValue = PAGE_SIZE) Integer size, Company company) {
		Page<Company> page = new Page<>(current, size);
		page = companyService.getList(page, company);
		return new RestResponse<Page<Company>>().rel(true).data(page);
	}

	@ApiOperation(value = "设置短信喜好", notes = "设置短信喜好")
	@ApiImplicitParam(name = "company", value = "设置短信喜好参数", dataTypeClass = Company.class)
	@PostMapping("setMessage")
	public RestResponse<String> setMessage(@RequestHeader(TOKEN) String token, @RequestBody Company company)
			throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		company.setId(info.getCompanyId());
		boolean flag = companyService.updateById(company);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data("设置成功");
		} else {
			return response.message("设置成功");
		}
	}

}
