package com.hengyu.system.web;

import static com.hengyu.common.constant.CommonConstants.PAGE_SIZE;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.system.po.ApplyPo;
import com.hengyu.system.service.ApplyService;
import com.hengyu.system.vo.ApplyVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 公司申请表 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-10-16
 */
@Api(value = "ApplyController", tags = "新建企业Controller")
@RestController
@RequestMapping("/apply")
public class ApplyController {

	@Autowired
	private ApplyService applyService;

	@Autowired
	private JWTUtils jwtUtils;

	@ApiOperation(value = "查询创建企业申请列表", notes = "查询创建企业申请列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "current", value = "当前页", defaultValue = "1", dataType = "Integer"),
			@ApiImplicitParam(name = "size", value = "每页行数", defaultValue = PAGE_SIZE, dataType = "Integer"),
			@ApiImplicitParam(name = "state", value = "认证状态 1未认证 2通过 3未通过", dataTypeClass = Integer.class),
			@ApiImplicitParam(name = "searchKey", value = "高级搜索(人名/企业名)", dataTypeClass = String.class) })
	@GetMapping("queryList")
	public RestResponse<Page<ApplyVo>> queryList(@RequestHeader("token") String token,
			@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer size,
			Integer state, String searchKey) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		Page<ApplyVo> page = new Page<>(current, size);
		page = applyService.queryList(page, state, searchKey, info.getCompanyId());
		return new RestResponse<Page<ApplyVo>>().rel(true).data(page);
	}

	@ApiOperation(value = "审核创建企业", notes = "审核创建企业")
	@ApiImplicitParam(name = "applyPo", value = "审核条件", dataTypeClass = ApplyPo.class)
	@PostMapping("approvalApply")
	public RestResponse<String> approvalApply(@Valid @RequestBody ApplyPo applyPo) {
		return new RestResponse<String>().rel(true).data(applyService.approvalApply(applyPo));
	}
	
	@ApiOperation(value = "根据id查询详情", notes = "根据id查询详情")
	@ApiImplicitParam(name = "id", value = "id", dataTypeClass = Integer.class)
	@GetMapping("queryDetail")
	public RestResponse<ApplyVo> queryDetail(@RequestHeader("token") String token,Integer id) throws Exception {
		ApplyVo applyVo = applyService.queryDetail(id);
		return new RestResponse<ApplyVo>().rel(true).data(applyVo);
	}
}
