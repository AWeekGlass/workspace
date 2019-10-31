package com.hengyu.user.web;

import static com.hengyu.common.constant.CommonConstants.TOKEN;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.user.service.ResourceCategoryService;
import com.hengyu.user.vo.ResourceCategoryVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
@Api(value = "ResourceCategoryController", tags = "权限菜单Controller")
@RestController
@RequestMapping("/resourceCategory")
public class ResourceCategoryController {
	@Autowired
	private ResourceCategoryService resourceCategoryService;
	
	@Autowired
	private JWTUtils jwtUtils;

	@ApiOperation(value = "获取菜单", notes = "获取菜单（请求头添加token）")
	@GetMapping("getAllResourceByCompanyId")
	public RestResponse<List<ResourceCategoryVo>> getAllResourceByCompanyId(@RequestHeader(TOKEN) String token)
			throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		return new RestResponse<List<ResourceCategoryVo>>().rel(true)
				.data(resourceCategoryService.getAllResourceByCompanyId(info.getCompanyId()));
	}
}
