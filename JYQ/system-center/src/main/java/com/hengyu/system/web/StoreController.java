package com.hengyu.system.web;

import static com.hengyu.common.constant.CommonConstants.ADD_FAILURE;
import static com.hengyu.common.constant.CommonConstants.ADD_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.DELETE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.DELETE_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.TOKEN;
import static com.hengyu.common.constant.CommonConstants.UPDATE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.UPDATE_SUCCESS;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hengyu.common.enums.ExceptionEnum;
import com.hengyu.common.exception.ClientForbiddenException;
import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.service.AdminService;
import com.hengyu.system.entity.Store;
import com.hengyu.system.service.StoreService;
import com.hengyu.system.vo.StoreShortVo;
import com.hengyu.system.vo.StoreTreeVo;
import com.hengyu.system.vo.StoreVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 中介门店表 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
@Api(value = "StoreController", tags = "门店Controller")
@Slf4j
@RestController
@RequestMapping("/store")
public class StoreController {
	@Autowired
	private StoreService storeService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private JWTUtils jwtUtils;

	@ApiOperation(value = "获取门店树", notes = "获取门店树（请求头添加token）")
	@GetMapping("queryTree")
	public RestResponse<List<StoreTreeVo>> queryTree(@RequestHeader(TOKEN) String token) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		return new RestResponse<List<StoreTreeVo>>().rel(true).data(storeService.queryTree(info.getCompanyId()));
	}

	@ApiOperation(value = "获取门店详情", notes = "获取门店详情")
	@GetMapping("detail/{id}")
	public RestResponse<StoreVo> detail(@PathVariable Integer id) {
		return new RestResponse<StoreVo>().rel(true).data(storeService.queryDetailById(id));
	}

	@ApiOperation(value = "新增门店", notes = "新增门店（请求头添加token）")
	@ApiImplicitParam(name = "store", value = "门店对象", dataTypeClass = Store.class)
	@PostMapping("add")
	public RestResponse<String> add(@RequestHeader(TOKEN) String token, @RequestBody Store store) throws Exception {
		log.info("store:{}", store);

		RestResponse<String> response = new RestResponse<String>();
		if (Objects.isNull(store)) {
			return response.rel(false).message(ExceptionEnum.MISSING_REQUEST_PARAMETER.getDescription());
		}

		Integer parentId = store.getParentId();
		boolean flag = false;
		if (Objects.isNull(parentId) & (Objects.equals(store.getType(), 3))) {
			return response.message("请先创建省级机构");
		}
		if (Objects.isNull(parentId) & (Objects.equals(store.getType(), 4) || Objects.equals(store.getType(), 5)
				|| Objects.equals(store.getType(), 6))) {
			return response.message("需要先建立省级和市级单位");
		}

		Integer parentType = storeService.selectOne(new EntityWrapper<Store>().eq("id", parentId)).getType();
		log.info("parentType:{}", parentType);
		
		switch (parentType) {
		case 2:
			if (StringUtils.equalsAny(store.getType() + "", "2", "4", "5", "6")) {
				return response.message("省级只可创建市级或以下");
			}
			break;
		case 3:
			if (StringUtils.equalsAny(store.getType() + "", "2" , "3", "5", "6")) {
				return response.message("市级只可创建区级或以下");
			}
			break;
		case 4:
			if (StringUtils.equalsAny(store.getType() + "", "2", "4", "3", "6")) {
				return response.message("区域下只可创建门店和小组");
			}
			break;
		case 5:
			if (StringUtils.equalsAny(store.getType()+"", "2", "3" , "4", "5")) {
				return response.message("门店下只可创建小组");
			}
			break;
		case 6:
				return response.message("小组下不可创建部门"); 
		default:
			break;
		}

		Integer cnt = storeService.queryCntByName(store.getName(), parentId);
		if (cnt > 0) {
			return response.message("名称重复");
		}

		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		store.setCompanyId(info.getCompanyId());
		store.setCreateId(info.getUserId());
		flag = storeService.insert(store);
		if (flag) {
			return response.rel(flag).data(ADD_SUCCESS);
		} else {
			return response.rel(flag).message(ADD_FAILURE);
		}
	}

	@ApiOperation(value = "更新门店", notes = "更新门店")
	@ApiImplicitParam(name = "store", value = "门店对象", dataTypeClass = Store.class)
	@PostMapping("update")
	public RestResponse<String> update(@RequestHeader(TOKEN) String token, @RequestBody Store store) throws Exception {
		log.info("store:{}", store);

		RestResponse<String> response = new RestResponse<String>();
		Integer parentId = store.getParentId();
		Integer cnt = storeService.queryCntByName(store.getName(), parentId);
		if (cnt > 1) {
			return response.message("名称重复");
		}

		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		store.setUpdateId(info.getUserId());
		boolean flag = storeService.updateById(store);
		response.rel(flag);
		if (flag) {
			return response.data(UPDATE_SUCCESS);
		} else {
			return response.message(UPDATE_FAILURE);
		}
	}

	@ApiOperation(value = "删除门店", notes = "删除门店")
	@ApiImplicitParam(name = "id", value = "门店ID", required = true, dataTypeClass = Integer.class)
	@DeleteMapping("delete/{storeId}")
	public RestResponse<String> delete(@PathVariable Integer storeId) throws ClientForbiddenException {
		Integer cnt = adminService.queryCntByStoreId(storeId);
		RestResponse<String> response = new RestResponse<>();

		if (Objects.equals(cnt, -1)) {
			throw new ClientForbiddenException("");
		}
		if (cnt > 0) {
			return response.rel(false).message("删除失败，请确认部门是否有下级部门或者相关人员，并将其转移");
		}

		boolean flag = storeService.delete(storeId);

		response.rel(flag);
		if (flag) {
			return response.data(DELETE_SUCCESS);
		} else {
			return response.message(DELETE_FAILURE);
		}
	}

	@ApiOperation(value = "获取部门", notes = "获取部门（请求头添加token）")
	@GetMapping("queryDept")
	public RestResponse<List<StoreVo>> queryDept(@RequestHeader(TOKEN) String token) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		return new RestResponse<List<StoreVo>>().rel(true).data(storeService.queryDept(info.getCompanyId()));
	}

	@ApiOperation(value = "查询组织架构中省市", notes = "查询组织架构中省市")
	@ApiImplicitParam(name = "type", value = "查询类型：1 查询省级部门 2 查询市级部门", required = true, dataTypeClass = Integer.class)
	@GetMapping("queryCity")
	public RestResponse<List<Store>> queryCity(@RequestHeader(TOKEN) String token, Integer type) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		return new RestResponse<List<Store>>().rel(true)
				.data(storeService.queryCity(info.getUserId(), type, info.getCompanyId()));
	}

	@ApiOperation(value = "查询组织架构中省市", notes = "查询组织架构中省市")
	@ApiImplicitParam(name = "type", value = "查询类型：1 查询省级部门 2 查询市级部门", required = true, dataTypeClass = Integer.class)
	@GetMapping("queryAllCity")
	public RestResponse<List<Store>> queryAllCity(@RequestHeader(TOKEN) String token, Integer type) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		return new RestResponse<List<Store>>().rel(true).data(storeService.queryAllCity(type, info.getCompanyId()));
	}

	@ApiOperation(value = "根据省级id查询所属市级", notes = "根据省级id查询所属市级")
	@ApiImplicitParam(name = "provinceId", value = "省级id", required = true, dataTypeClass = Integer.class)
	@GetMapping("queryCityById")
	public RestResponse<List<Store>> queryCityById(@RequestHeader(TOKEN) String token, Integer provinceId)
			throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		return new RestResponse<List<Store>>().rel(true)
				.data(storeService.queryCityById(provinceId, info.getCompanyId()));
	}

	@ApiOperation(value = "获取当前城市列表", notes = "获取当前城市列表")
	@GetMapping("queryStoreById")
	public RestResponse<List<StoreShortVo>> queryStoreById(@RequestHeader(TOKEN) String token) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		return new RestResponse<List<StoreShortVo>>().rel(true)
				.data(storeService.queryStoreById(info.getUserId(), info.getCompanyId()));
	}
	
	
	@ApiOperation(value = "新增门店(v2.0)", notes = "新增门店V2.0（请求头添加token）")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "token", dataTypeClass = String.class),
		@ApiImplicitParam(name = "store", value = "门店对象", dataTypeClass = Store.class),
	})
	@PostMapping("addNew")
	public RestResponse<String> addStore(@RequestHeader(TOKEN) String token, @RequestBody Store store) throws Exception {
		log.info("org:{}", store);

		RestResponse<String> response = new RestResponse<String>();
		if (Objects.isNull(store)) {
			return response.rel(false).message(ExceptionEnum.MISSING_REQUEST_PARAMETER.getDescription());
		}

		Integer parentId = store.getParentId();
		String name = store.getName();
		boolean flag = false;
		if (Objects.isNull(parentId)) {
			return response.message("请先选择上级部门");
		}
		if (Objects.isNull(name)) {
			return response.message("请输入当前部门名称");
		}


		Integer cnt = storeService.queryCntByName(store.getName(), parentId);
		if (cnt > 0) {
			return response.message("名称重复");
		}

		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		store.setCompanyId(info.getCompanyId());
		store.setCreateId(info.getUserId());
		flag = storeService.insert(store);
		if (flag) {
			return response.rel(flag).data(ADD_SUCCESS);
		} else {
			return response.rel(flag).message(ADD_FAILURE);
		}
	}
	
	@ApiOperation(value = "获取当前门店树", notes = "获取当前门店树（请求头添加token）")
	@GetMapping("queryStoreTree")
	public RestResponse<List<StoreTreeVo>> queryStoreTree(Integer parentId) throws Exception {
		return new RestResponse<List<StoreTreeVo>>().rel(true).data(storeService.queryStoreTree(parentId));
	}

}
