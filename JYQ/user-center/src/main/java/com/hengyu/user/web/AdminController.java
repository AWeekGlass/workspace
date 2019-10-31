package com.hengyu.user.web;

import static com.hengyu.common.constant.CommonConstants.ADD_FAILURE;
import static com.hengyu.common.constant.CommonConstants.ADD_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.DELETE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.DELETE_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.PAGE_SIZE;
import static com.hengyu.common.constant.CommonConstants.TOKEN;
import static com.hengyu.common.constant.CommonConstants.UPDATE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.UPDATE_SUCCESS;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.hengyu.common.enums.ExceptionEnum;
import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.EncryptionUtil;
import com.hengyu.common.util.FileUtils;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.common.util.JsonUtils;
import com.hengyu.common.util.UrlUtils;
import com.hengyu.user.entity.Admin;
import com.hengyu.user.po.ApprChaPhonePo;
import com.hengyu.user.po.ChangePhonePo;
import com.hengyu.user.service.AdminService;
import com.hengyu.user.service.ChangePhoneService;
import com.hengyu.user.vo.AdminFullVo;
import com.hengyu.user.vo.AdminMiddleVo;
import com.hengyu.user.vo.AdminSmallVo;
import com.hengyu.user.vo.AdminTreeVo;
import com.hengyu.user.vo.AdminVo;
import com.hengyu.user.vo.MyInfoVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

/**
 * <p>
 * 中介用户表 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
@Api(value = "用户中心", tags = "用户Controller")
@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {
	@Value("${wx.appid}")
	private String appid;

	@Value("${wx.secret}")
	private String secret;

	@Value("${admin.upload-img-path}")
	private String uploadPath;

	@Autowired
	private AdminService adminService;

	@Autowired
	private JWTUtils jwtUtils;

	@Autowired
	private ChangePhoneService changePhoneService;

	@Autowired
	private FileUtils fileUtils;

	@ApiOperation(value = "获取对应用户", notes = "获取对应用户（请求头添加token）")
	@ApiImplicitParams({ @ApiImplicitParam(name = "current", value = "当前页", defaultValue = "1", dataType = "Integer"),
			@ApiImplicitParam(name = "size", value = "每页行数", defaultValue = PAGE_SIZE, dataType = "Integer"),
			@ApiImplicitParam(name = "status", value = "状态", dataType = "status"),
			@ApiImplicitParam(name = "storeId", value = "部门ID", dataType = "Integer"),
			@ApiImplicitParam(name = "roleId", value = "职务ID", dataType = "roleId"),
			@ApiImplicitParam(name = "userName", value = "员工姓名", dataType = "String"),
			@ApiImplicitParam(name = "minEntryDate", value = "开始时间", dataType = "String"),
			@ApiImplicitParam(name = "maxEntryDate", value = "结束时间", dataType = "String") })
	@GetMapping("queryList")
	public RestResponse<Page<AdminMiddleVo>> queryList(@RequestHeader(TOKEN) String token,
			@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = PAGE_SIZE) Integer size,
			Integer status, Integer storeId, Integer roleId, String userName, String minEntryDate, String maxEntryDate)
			throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		Map<String, Object> parameterMap = new HashMap<>();
		parameterMap.put("companyId", info.getCompanyId());
		parameterMap.put("storeId", storeId);
		parameterMap.put("roleId", roleId);
		parameterMap.put("status", status);
		parameterMap.put("userName", userName);
		parameterMap.put("minEntryDate", minEntryDate);
		parameterMap.put("maxEntryDate", maxEntryDate);
		log.info("parameterMap:{}", parameterMap);

		return new RestResponse<Page<AdminMiddleVo>>().rel(true)
				.data(adminService.queryList(new Page<AdminMiddleVo>(current, size), parameterMap));
	}

	@ApiIgnore
	@GetMapping("login")
	public String login(String phone, String password) throws Exception {
		if (Objects.isNull(phone) || Objects.isNull(password)) {
			return "";
		}
		return adminService.login(phone, password);
	}

	@ApiOperation(value = "获取员工详情", notes = "员工详情")
	@ApiImplicitParam(name = "id", value = "用户ID", required = true, dataTypeClass = Integer.class)
	@GetMapping("detail/{id}")
	public RestResponse<AdminVo> detail(@PathVariable Integer id) {
		return new RestResponse<AdminVo>().rel(true).data(adminService.queryDetailById(id));
	}

	@ApiOperation(value = "获取当前员工概要", notes = "当前员工概要")
	@GetMapping("brief")
	public RestResponse<AdminVo> brief(@RequestHeader(TOKEN) String token) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		return new RestResponse<AdminVo>().rel(true).data(adminService.queryDetailById(info.getUserId()));
	}

	@ApiOperation(value = "获取当前员工简历", notes = "当前员工简历")
	@GetMapping("resume")
	public RestResponse<AdminFullVo> resume(@RequestHeader(TOKEN) String token) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		return new RestResponse<AdminFullVo>().rel(true).data(adminService.queryResumeById(info.getUserId()));
	}

	@ApiOperation(value = "根据ID获取简历", notes = "根据ID获取简历")
	@GetMapping("resumeById")
	public RestResponse<AdminFullVo> resumeById(Integer id) throws Exception {
		return new RestResponse<AdminFullVo>().rel(true).data(adminService.queryResumeById(id));
	}

	@ApiOperation(value = "新增员工", notes = "新增员工（请求头添加token）")
	@ApiImplicitParam(name = "admin", value = "员工对象", dataTypeClass = String.class)
	@PostMapping("add")
	public RestResponse<String> add(@RequestHeader(TOKEN) String token,
			@RequestParam(value = "photo", required = false) MultipartFile photo,
			@RequestParam(value = "admin") String json) throws Exception {
		Admin admin = JsonUtils.getObject4JsonString(json, Admin.class);
		RestResponse<String> response = new RestResponse<String>();
		if (StringUtils.isEmpty(admin.getUserName()) || Objects.isNull(admin.getStoreId())
				|| Objects.isNull(admin.getRoleId())) {
			return response.rel(false).message(ExceptionEnum.MISSING_REQUEST_PARAMETER.getDescription());
		}

		if (Objects.nonNull(photo)) {
			String headPortrait = fileUtils.uploadFtpImg(photo, uploadPath);
			log.info("headPortrait:{}", headPortrait);
			admin.setHeadPortrait(headPortrait);
		}
		Integer check = checkPhone(admin.getTelephone());
		if (!check.equals(-1)) {
			return response.rel(false).message("该手机号码已注册！");
		}

		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		admin.setCompanyId(info.getCompanyId());
		admin.setPassword(EncryptionUtil.getSHA256StrJava("123456"));
		boolean flag = adminService.insert(admin);
		if (flag) {
			return response.rel(flag).data(ADD_SUCCESS);
		} else {
			return response.rel(flag).message(ADD_FAILURE);
		}
	}

	@ApiOperation(value = "更换头像", notes = "更换头像(请求头添加token)")
	@ApiImplicitParam(name = "photo", value = "头像", dataTypeClass = MultipartFile.class)
	@PostMapping("changeHeadIMG")
	public RestResponse<String> changeHeadIMG(@RequestHeader(TOKEN) String token,
			@RequestParam(value = "file", required = true) MultipartFile file) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		Admin admin = new Admin();
		admin.setId(info.getUserId());
		RestResponse<String> response = new RestResponse<String>();
		if (Objects.nonNull(file)) {
			String headPortrait = fileUtils.uploadFtpImg(file, uploadPath);
			log.info("headPortrait:{}", headPortrait);
			admin.setHeadPortrait(headPortrait);
		}else {
			return response.rel(false).message("请上传头像");
		}
		boolean flag = adminService.updateById(admin);
		if (flag) {
			return response.rel(flag).data(admin.getHeadPortrait());
		} else {
			return response.rel(flag).message("更换失败");
		}
	}

	@ApiOperation(value = "设置员工密码", notes = "设置员工密码")
	@ApiImplicitParam(name = "admin", value = "员工对象", dataTypeClass = Admin.class)
	@PostMapping("updatePwd")
	public RestResponse<String> updatePwd(@RequestHeader(TOKEN) String token, @RequestBody Admin admin)
			throws Exception {
		RestResponse<String> response = new RestResponse<>();
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		Integer adminId = info.getUserId();
		String oldPassword = admin.getOldPassword();
		String password = admin.getPassword();
		String rePassword = admin.getRePassword();
		if (StringUtils.isAnyEmpty(oldPassword, password, rePassword) || !Objects.equals(password, rePassword)) {
			return response.rel(false).message(ExceptionEnum.MISSING_REQUEST_PARAMETER.getDescription());
		}
		Boolean flag = adminService.checkPwd(adminId, EncryptionUtil.getSHA256StrJava(oldPassword));
		if (flag) {
			return response.rel(false).message("原密码不正确");
		}

		admin.setPassword(EncryptionUtil.getSHA256StrJava(password));
		admin.setId(adminId);
		flag = adminService.updateById(admin);
		response.setRel(flag);
		if (flag) {
			return response.data(UPDATE_SUCCESS);
		} else {
			return response.message(UPDATE_FAILURE);
		}
	}

	@ApiOperation(value = "重置员工密码", notes = "重置员工密码")
	@ApiImplicitParam(name = "admin", value = "员工对象", dataTypeClass = Admin.class)
	@PostMapping("resetPwd/{adminId}")
	public RestResponse<String> resetPwd(@PathVariable Integer adminId) {
		Admin admin = new Admin();
		admin.setId(adminId);
		admin.setPassword(EncryptionUtil.getSHA256StrJava("123456"));
		boolean flag = adminService.updateById(admin);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(UPDATE_SUCCESS);
		} else {
			return response.message(UPDATE_FAILURE);
		}
	}

	@ApiOperation(value = "更换手机号", notes = "更换手机号")
	@ApiImplicitParam(name = "changePhonePo", value = "更换手机号", dataTypeClass = ChangePhonePo.class)
	@PostMapping("changePhone")
	public RestResponse<String> changePhone(@RequestHeader(TOKEN) String token,
			@Valid @RequestBody ChangePhonePo changePhonePo) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		changePhonePo.setUserId(info.getUserId());
		changePhonePo.setCompanyId(info.getCompanyId());
		RestResponse<String> response = new RestResponse<String>().rel(true);
		return response.data(adminService.changePhone(changePhonePo));
	}

	@ApiOperation(value = "审批更换手机号", notes = "审批更换手机号")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "更换手机号记录id", dataTypeClass = Integer.class),
			@ApiImplicitParam(name = "state", value = "更改状态", dataTypeClass = Integer.class) })
	@PostMapping("approveChangePhone")
	public RestResponse<String> approveChangePhone(@RequestHeader(TOKEN) String token,
			@RequestBody ApprChaPhonePo apprChaPhonePo) throws Exception {
		RestResponse<String> response = new RestResponse<String>().rel(true);
		return response.data(changePhoneService.approveChangePhone(apprChaPhonePo));
	}

	@ApiOperation(value = "更新员工", notes = "更新员工")
	@ApiImplicitParam(name = "admin", value = "员工对象", dataTypeClass = Admin.class)
	@PostMapping("update")
	public RestResponse<String> update(@RequestParam(value = "photo", required = false) MultipartFile photo,
			@RequestParam(value = "admin") String json) {
		Admin admin = JsonUtils.getObject4JsonString(json, Admin.class);

		RestResponse<String> response = new RestResponse<String>();
		if (StringUtils.isEmpty(admin.getUserName()) || Objects.isNull(admin.getStoreId())
				|| Objects.isNull(admin.getRoleId())) {
			return response.rel(false).message("调动状态或者调动部门为空，请重新选择");
		}
		if (Objects.nonNull(photo)) {
			String headPortrait = fileUtils.uploadFtpImg(photo, uploadPath);
			log.info("headPortrait:{}", headPortrait);
			admin.setHeadPortrait(headPortrait);
		}
		boolean flag = adminService.updateById(admin);
		response.rel(flag);
		if (flag) {
			return response.data(UPDATE_SUCCESS);
		} else {
			return response.message(UPDATE_FAILURE);
		}
	}

	@ApiOperation(value = "删除员工", notes = "删除员工")
	@ApiImplicitParam(name = "id", value = "用户ID", required = true, dataTypeClass = Integer.class)
	@DeleteMapping("delete/{ids}")
	public RestResponse<String> delete(@PathVariable String ids) {
		log.info("ids:{}", ids);
		boolean flag = adminService.updateDelFlag(Arrays.asList(ids.split(",")));
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(DELETE_SUCCESS);
		} else {
			return response.message(DELETE_FAILURE);
		}
	}

	@GetMapping("queryCntByStoreId")
	public Integer queryCntByStoreId(Integer storeId) {
		return adminService.queryCntByStoreId(storeId);
	}

	@ApiOperation(value = "根据名字模糊查询", notes = "根据名字模糊查询")
	@ApiImplicitParam(name = "userName", value = "员工姓名", dataType = "String")
	@GetMapping("queryByUserName")
	public RestResponse<List<AdminSmallVo>> queryByUserName(@RequestHeader(TOKEN) String token, String userName)
			throws Exception {
		log.info("userName:{}", userName);
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		return new RestResponse<List<AdminSmallVo>>().rel(true)
				.data(adminService.queryByUserName(info.getCompanyId(), userName));
	}

	@ApiIgnore
	@GetMapping("checkPhone")
	public Integer checkPhone(String phone) {
		return adminService.checkPhone(phone);
	}
	
	@ApiIgnore
	@GetMapping("check")
	public String check(String phone) {
		return adminService.check(phone);
	}

	@ApiOperation(value = "注册", notes = "注册用户")
	@ApiImplicitParam(name = "Admin", value = "注册信息", dataTypeClass = Admin.class)
	@PostMapping("registe")
	public RestResponse<String> registe(@RequestBody Admin admin) {
		return adminService.registe(admin);
	}

	@ApiOperation(value = "查询我的信息", notes = "查询我的信息")
	@ApiImplicitParam(name = "token", value = "token", dataTypeClass = String.class)
	@GetMapping("queryMyInfo")
	public RestResponse<MyInfoVo> queryMyInfo(@RequestHeader(TOKEN) String token) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		return new RestResponse<MyInfoVo>().rel(true).data(adminService.queryMyInfo(info.getUserId()));
	}

	@ApiOperation(value = "人员树", notes = "人员树")
	@GetMapping("queryTree")
	public RestResponse<List<AdminTreeVo>> queryTree(@RequestHeader(TOKEN) String token) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		return new RestResponse<List<AdminTreeVo>>().rel(true).data(adminService.queryTree(info.getCompanyId()));
	}

}
