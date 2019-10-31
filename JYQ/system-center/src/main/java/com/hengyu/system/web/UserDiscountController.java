package com.hengyu.system.web;

import static com.hengyu.common.constant.CommonConstants.PAGE_SIZE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.system.entity.UserDiscount;
import com.hengyu.system.service.UserDiscountService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 用户折扣券表 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-12-11
 */
@Api(value = "UserDiscountController", tags = "用户优惠Controller")
@RestController
@RequestMapping("/userDiscount")
public class UserDiscountController {

	@Autowired
	private UserDiscountService discountService;

	@Autowired
	private JWTUtils jwtUtils;

	@ApiOperation(value = "查询我的优惠", notes = "查询我的优惠")
	@ApiImplicitParams({ @ApiImplicitParam(name = "current", value = "当前页", defaultValue = "1", dataType = "Integer"),
			@ApiImplicitParam(name = "size", value = "每页行数", defaultValue = PAGE_SIZE, dataType = "Integer"),
			@ApiImplicitParam(name = "userDiscount", value = "折扣信息", dataTypeClass = UserDiscount.class) })
	@GetMapping("userDiscount")
	public RestResponse<Page<UserDiscount>> queryList(@RequestHeader("token") String token,
			@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "1") Integer size,
			UserDiscount userDiscount) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		userDiscount.setUserId(info.getUserId());
		Page<UserDiscount> page = new Page<>(current, size);
		return new RestResponse<Page<UserDiscount>>().rel(true).data(discountService.queryList(page, userDiscount));
	}
	
	@ApiOperation(value = "领取优惠券", notes = "领取优惠券")
	@ApiImplicitParam(name = "userDiscount", value = "折扣信息", dataTypeClass = UserDiscount.class)
	@PostMapping("add")
	public RestResponse<String> add(@RequestHeader("token") String token,Integer discountId) throws Exception{
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		UserDiscount userDiscount = new UserDiscount();
		userDiscount.setUserId(info.getUserId());
		userDiscount.setDiscountId(discountId);
		boolean flag = discountService.add(userDiscount);
		RestResponse<String> response = new RestResponse<>();
		if(flag){
			return response.rel(true).data("领取成功");
		}
		return response.rel(false).data("领取失败");
	}

}
