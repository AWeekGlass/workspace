package com.hengyu.system.web;

import static com.hengyu.common.constant.CommonConstants.PAGE_SIZE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.system.entity.Discount;
import com.hengyu.system.service.DiscountService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 折扣优惠表 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-12-07
 */
@Api(value = "DiscountController", tags = "优惠表Controller")
@RestController
@RequestMapping("/discount")
public class DiscountController {

	@Autowired
	private DiscountService discountService;
	
	@Autowired
	private JWTUtils jwtUtils;

	@ApiOperation(value = "查询优惠列表", notes = "查询优惠列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "current", value = "当前页", defaultValue = "1", dataType = "Integer"),
			@ApiImplicitParam(name = "size", value = "每页行数", defaultValue = PAGE_SIZE, dataType = "Integer"),
			@ApiImplicitParam(name = "discount", value = "优惠条件", dataTypeClass = Discount.class) })
	@GetMapping("queryList")
	public RestResponse<Page<Discount>> queryList(@RequestHeader("token") String token,
			@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer size,
			Discount discount) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		Page<Discount> page = new Page<>(current, size);
		discount.setCompanyId(info.getCompanyId());
		page = discountService.queryList(page, discount,info.getUserId());
		return new RestResponse<Page<Discount>>().rel(true).data(page);
	}

}
