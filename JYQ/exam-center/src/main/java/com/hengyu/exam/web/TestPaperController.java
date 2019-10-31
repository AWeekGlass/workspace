package com.hengyu.exam.web;

import static com.hengyu.common.constant.CommonConstants.ADD_FAILURE;
import static com.hengyu.common.constant.CommonConstants.ADD_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.DELETE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.DELETE_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.PAGE_SIZE;
import static com.hengyu.common.constant.CommonConstants.TOKEN;
import static com.hengyu.common.constant.CommonConstants.UPDATE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.UPDATE_SUCCESS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.hengyu.exam.entity.TestPaper;
import com.hengyu.exam.po.QuerPaperDetailPo;
import com.hengyu.exam.po.QueryPastPo;
import com.hengyu.exam.po.TestPaperSubordinatePo;
import com.hengyu.exam.service.TestPaperService;
import com.hengyu.exam.vo.AdminVo;
import com.hengyu.exam.vo.StateVo;
import com.hengyu.exam.vo.TestPaperFullVo;
import com.hengyu.exam.vo.TestPaperSubordinateVo;
import com.hengyu.exam.vo.TestPaperVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 试卷库前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-09-04
 */
@Api(value = "TestPaperController", tags = "试卷库Controller")
@Slf4j
@RestController
@RequestMapping("/testPaper")
public class TestPaperController {
	@Autowired
	private TestPaperService testPaperService;

	@Autowired
	private JWTUtils jwtUtils;

	@ApiOperation(value = "获取试卷编号", notes = "获取试卷编号")
	@GetMapping("getCode")
	public RestResponse<String> getCode() {
		Random random = new Random();
		int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
		return new RestResponse<String>().rel(true)
				.data(FastDateFormat.getInstance("yyyyMMddHHmmss").format(new Date()) + rannum);
	}

	@ApiOperation(value = "查询我的下属成绩列表", notes = "查询我的下属成绩列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "current", value = "当前页", defaultValue = "1", dataType = "Integer"),
			@ApiImplicitParam(name = "size", value = "每页行数", defaultValue = PAGE_SIZE, dataType = "Integer"),
			@ApiImplicitParam(name = "testPaperSubordinatePo", value = "下属成绩", dataTypeClass = TestPaperSubordinatePo.class) })
	@GetMapping("querySubordinate")
	public RestResponse<Page<TestPaperSubordinateVo>> querySubordinate(@RequestHeader(TOKEN) String token,
			@RequestParam(value = "current", defaultValue = "1", required = false) Integer current,
			@RequestParam(value = "size", defaultValue = PAGE_SIZE, required = false) Integer size,
			TestPaperSubordinatePo testPaperSubordinatePo) throws Exception {
		// 获取用户信息
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		testPaperSubordinatePo.setUserId(info.getUserId());
		Page<TestPaperSubordinateVo> page = new Page<>(current, size);
		return new RestResponse<Page<TestPaperSubordinateVo>>().rel(true)
				.data(testPaperService.querySubordinate(page, testPaperSubordinatePo));
	}

	/**
	 * 新增试卷
	 * 
	 * @param po
	 * @return
	 */
	@ApiOperation(value = "新增试卷", notes = "新增试卷")
	@ApiImplicitParam(name = "testPaper", value = "试卷对象", dataTypeClass = TestPaper.class)
	@PostMapping("save")
	public RestResponse<String> save(@RequestHeader(TOKEN) String token, @RequestBody TestPaper testPaper)
			throws Exception {
		// 获取用户信息
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		testPaper.setCompanyId(info.getCompanyId());
		testPaper.setDistributeId(info.getUserId());

		if (Objects.equals(testPaper.getTestType(), 1)) {
			List<Integer> userIds = new ArrayList<>();
			userIds.add(info.getUserId());
			testPaper.setUserIds(userIds);
		}
		boolean flag = testPaperService.save(testPaper);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(ADD_SUCCESS);
		} else {
			return response.message(ADD_FAILURE);
		}
	}

	/**
	 * 修改试卷
	 * 
	 * @param token
	 * @param po
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "修改试卷", notes = "修改试卷")
	@ApiImplicitParam(name = "testPaper", value = "试卷对象", dataTypeClass = TestPaper.class)
	@PostMapping("update")
	public RestResponse<String> update(@RequestBody TestPaper testPaper) throws Exception {
		boolean flag = testPaperService.update(testPaper);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(UPDATE_SUCCESS);
		} else {
			return response.message(UPDATE_FAILURE);
		}
	}

	/**
	 * 删除试卷
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "删除试卷", notes = "删除试卷")
	@ApiImplicitParam(name = "id", value = "试卷id", dataTypeClass = String.class)
	@DeleteMapping("delete/{ids}")
	public RestResponse<String> delete(@PathVariable String ids) {
		log.info("ids:{}", ids);
		boolean flag = testPaperService.updateDelFlag(Arrays.asList(ids.split(",")));
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(DELETE_SUCCESS);
		} else {
			return response.message(DELETE_FAILURE);
		}
	}

	/**
	 * 查询详情
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "查询详情", notes = "查询详情")
	@ApiImplicitParam(name = "id", value = "试卷id", dataTypeClass = Integer.class)
	@GetMapping("queyById/{id}")
	public RestResponse<TestPaperFullVo> queyById(@PathVariable Integer id) {
		log.info("id:{}", id);
		return new RestResponse<TestPaperFullVo>().rel(true).data(testPaperService.queryById(id));
	}

	/**
	 * 查询考生试卷详情
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "查询考生试卷详情", notes = "查询考生试卷详情")
	@ApiImplicitParam(name = "querPaperDetailPo", value = "查询条件", dataTypeClass = QuerPaperDetailPo.class)
	@GetMapping("queyDetailByUser")
	public RestResponse<TestPaperFullVo> queyDetailByUser(@RequestHeader("token") String token, QuerPaperDetailPo po) {
		return new RestResponse<TestPaperFullVo>().rel(true).data(testPaperService.queyDetailByUser(po));
	}

	/**
	 * 查询列表
	 * 
	 * @param testPaper
	 * @return
	 */
	@ApiOperation(value = "查询列表", notes = "查询列表")
	@ApiImplicitParam(name = "testPaper", value = "试卷对象", dataTypeClass = TestPaper.class)
	@PostMapping("queryList")
	public RestResponse<Page<TestPaperVo>> queryList(@RequestBody TestPaper testPaper) {
		return new RestResponse<Page<TestPaperVo>>().rel(true)
				.data(testPaperService.queryList(new Page<>(testPaper.getCurrent(), testPaper.getSize()), testPaper));
	}

	@ApiOperation(value = "查询自测列表", notes = "查询自测列表")
	@ApiImplicitParam(name = "testPaper", value = "试卷对象", dataTypeClass = TestPaper.class)
	@PostMapping("queryMyExamList")
	public RestResponse<Page<TestPaperVo>> queryMyExamList(@RequestBody TestPaper testPaper) {
		return new RestResponse<Page<TestPaperVo>>().rel(true).data(
				testPaperService.queryMyExamList(new Page<>(testPaper.getCurrent(), testPaper.getSize()), testPaper));
	}

	/**
	 * 查询我的考试列表(有问题待修改，修改为从考试记录里面查询)
	 * 
	 * @param testPaper
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "查询我的考试列表", notes = "查询我的考试列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "current", value = "当前页", defaultValue = "1", dataType = "Integer"),
			@ApiImplicitParam(name = "size", value = "每页行数", defaultValue = PAGE_SIZE, dataType = "Integer") })
	@GetMapping("queryMyList")
	public RestResponse<Page<TestPaperVo>> queryMyList(@RequestHeader(TOKEN) String token,
			@RequestParam(value = "current", defaultValue = "1", required = false) Integer current,
			@RequestParam(value = "size", defaultValue = PAGE_SIZE, required = false) Integer size,
			@RequestParam(value = "state", required = false) Integer state) throws Exception {
		// 获取用户信息
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		Page<TestPaperVo> page = new Page<>(current, size);
		page = testPaperService.queryMyList(page, info.getUserId(), state);
		return new RestResponse<Page<TestPaperVo>>().rel(true).data(page);
	}

	/**
	 * 发送短信
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "发送短信", notes = "发送短信")
	@ApiImplicitParams({ @ApiImplicitParam(name = "paperId", value = "试卷id", required = true, dataType = "Integer"),
			@ApiImplicitParam(name = "userId", value = "参考人id", required = true, dataType = "Integer") })
	@GetMapping("sendMessage")
	public RestResponse<String> sendMessage(Integer paperId, String userId) {
		boolean flag = testPaperService.sendMessage(paperId, userId);
		if (flag) {
			// 更改试卷状态
			startExam(paperId);
			return new RestResponse<String>().rel(flag).data("发送成功!");
		}
		return new RestResponse<String>().rel(flag).message("发送失败!");
	}

	/**
	 * 查询列表
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "获取审批列表", notes = "获取审批列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "current", value = "当前页", defaultValue = "1", dataType = "Integer"),
			@ApiImplicitParam(name = "size", value = "每页行数", defaultValue = PAGE_SIZE, dataType = "Integer"),
			@ApiImplicitParam(name = "type", value = "查询类型 1 未批阅 2 已批阅 3批阅中", dataTypeClass = Integer.class), })
	@GetMapping("queryCheckList")
	public RestResponse<Page<TestPaperVo>> queryCheckList(@RequestHeader(TOKEN) String token,
			@RequestParam(value = "current", defaultValue = "1", required = false) Integer current,
			@RequestParam(value = "size", defaultValue = PAGE_SIZE, required = false) Integer size,
			@RequestParam(value = "type", required = false) Integer type) throws Exception {
		// 获取用户信息
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		Page<TestPaperVo> page = new Page<>(current, size);
		page = testPaperService.queryCheckList(page, info.getUserId(), type);
		return new RestResponse<Page<TestPaperVo>>().rel(true).data(page);
	}

	/**
	 * 查询列表
	 * 
	 * @param testPaper
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "查询往期列表", notes = "查询往期列表")
	@ApiImplicitParams(@ApiImplicitParam(name = "queryPastPo", value = "查询参数", dataTypeClass = QueryPastPo.class))
	@PostMapping("queryPastList")
	public RestResponse<Page<TestPaperVo>> queryPastList(@RequestHeader("token") String token,
			@RequestBody QueryPastPo queryPastPo) throws Exception {
		// 获取用户信息
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		queryPastPo.setCompanyId(info.getCompanyId());
		Page<TestPaperVo> page = new Page<>(queryPastPo.getCurrent(), queryPastPo.getSize());
		return new RestResponse<Page<TestPaperVo>>().rel(true).data(testPaperService.queryPastList(page, queryPastPo));
	}

	@ApiOperation(value = "查询参考率和及格率数据", notes = "查询参考率和及格率数据")
	@ApiImplicitParams({ @ApiImplicitParam(name = "paperId", value = "试卷id", dataType = "Integer"),
			@ApiImplicitParam(name = "type", value = "查询类型 1参考率 2 及格率", dataTypeClass = Integer.class) })
	@GetMapping("getTestState")
	public RestResponse<List<StateVo>> getTestState(Integer paperId, Integer type) {
		return new RestResponse<List<StateVo>>().rel(true).data(testPaperService.getTestState(paperId, type));
	}

	@ApiOperation(value = "派发考试", notes = "派发考试")
	@ApiImplicitParam(name = "paperId", value = "试卷id", dataTypeClass = Integer.class)
	@GetMapping("startExam/{paperId}")
	public RestResponse<String> startExam(@PathVariable Integer paperId) {
		boolean flag = false;
		if (Objects.nonNull(paperId)) {
			TestPaper testPaper = new TestPaper();
			testPaper.setId(paperId);
			testPaper.setIsSend(1);
			flag = testPaperService.updateById(testPaper);
		}
		if (flag) {
			return new RestResponse<String>().rel(flag).data("派发成功!");
		}
		return new RestResponse<String>().rel(flag).message("派发失败!");
	}

	@ApiOperation(value = "查询参考人员列表", notes = "查询参考人员列表")
	@GetMapping("queryPaperUser")
	public RestResponse<List<AdminVo>> queryPaperUser(@RequestHeader("token") String token) throws Exception {
		// 获取用户信息
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		return new RestResponse<List<AdminVo>>().rel(true).data(testPaperService.queryPaperUser(info.getCompanyId()));
	}

	@ApiOperation(value = "查询代办事项", notes = "查询代办事项")
	@ApiImplicitParams({ @ApiImplicitParam(name = "current", value = "当前页", defaultValue = "1", dataType = "Integer"),
			@ApiImplicitParam(name = "size", value = "每页行数", defaultValue = PAGE_SIZE, dataType = "Integer"),
			@ApiImplicitParam(name = "token", value = "token", dataTypeClass = String.class), })
	@GetMapping("queryTodo")
	public RestResponse<Page<TestPaperVo>> queryTodo(@RequestHeader(TOKEN) String token,
			@RequestParam(value = "current", defaultValue = "1", required = false) Integer current,
			@RequestParam(value = "size", defaultValue = "100", required = false) Integer size) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		Page<TestPaperVo> page = new Page<>(current, size);
		page = testPaperService.queryTodo(page, info.getUserId());
		//是否实名制
//		Integer count = testPaperService.countUserInfo(info.getUserId());
		page.setTotal(page.getTotal());
		return new RestResponse<Page<TestPaperVo>>().rel(true).data(page);
	}
}
