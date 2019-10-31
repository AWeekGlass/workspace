package com.hengyu.exam.web;

import static com.hengyu.common.constant.CommonConstants.ADD_FAILURE;
import static com.hengyu.common.constant.CommonConstants.ADD_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.DELETE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.DELETE_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.TOKEN;
import static com.hengyu.common.constant.CommonConstants.UPDATE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.UPDATE_SUCCESS;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

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
import com.baomidou.mybatisplus.plugins.Page;
import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.exam.entity.Question;
import com.hengyu.exam.po.SearchQuestionPo;
import com.hengyu.exam.po.UpdateStatePo;
import com.hengyu.exam.service.QuestionService;
import com.hengyu.exam.vo.CountVo;
import com.hengyu.exam.vo.QuestionVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 题库前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-09-04
 */
@Api(value = "QuestionController", tags = "题库Controller")
@Slf4j
@RestController
@RequestMapping("/question")
public class QuestionController {
	@Autowired
	private QuestionService questionService;

	@Autowired
	private JWTUtils jwtUtils;

	@ApiOperation(value = "根据公司获取题库", notes = "获取题库（请求头添加token）")
	@ApiImplicitParam(name = "searchQuestionPo", value = "查询参数", dataTypeClass= SearchQuestionPo.class)
	@PostMapping("queryQuestions")
	public RestResponse<Page<QuestionVo>> queryQuestions(@RequestHeader(TOKEN) String token,@RequestBody @Valid SearchQuestionPo po) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		po.setCompanyId(info.getCompanyId());
		//如果是查询我的出题则获取我的id
		if(Objects.equals(2, po.getStatus()) || Objects.nonNull(po.getSearchType()) && po.getSearchType() ==2){
			po.setUserId(info.getUserId());
		}
		return new RestResponse<Page<QuestionVo>>().rel(true)
				.data(questionService.queryQuestionsByCompanyId(new Page<QuestionVo>(po.getCurrent(), po.getSize()), po));
	}

	@ApiOperation(value = "获取题目详情", notes = "获取题目详情")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "题目ID", required = true, dataTypeClass = Integer.class),
		@ApiImplicitParam(name = "state", value = "题目状态", defaultValue = "1", dataTypeClass = Integer.class)
	})
	@GetMapping("detail/{id}/{state}")
	public RestResponse<QuestionVo> detail(@PathVariable Integer id,@PathVariable Integer state) {
		log.info("id:{}", id);
		return new RestResponse<QuestionVo>().rel(true).data(questionService.queryDetailById(id,state));
	}

	@ApiOperation(value = "新增题目", notes = "新增题目（请求头添加token）")
	@ApiImplicitParam(name = "question", value = "题目对象", dataTypeClass = Question.class)
	@PostMapping("add")
	public RestResponse<String> add(@RequestHeader(TOKEN) String token,@RequestBody Question question) throws Exception {
		log.info("question:{}", question);
		//获取公司id和员工id
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		question.setCompanyId(info.getCompanyId());
		question.setAdminId(info.getUserId());
		boolean flag = questionService.add(question);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(ADD_SUCCESS);
		} else {
			return response.message(ADD_FAILURE);
		}
	}

	@ApiOperation(value = "更新题目", notes = "更新题目")
	@ApiImplicitParam(name = "question", value = "题目对象", dataTypeClass = Question.class)
	@PostMapping("update")
	public RestResponse<String> update(@RequestHeader(TOKEN) String token,@RequestBody Question question) throws Exception {
		log.info("question:{}", question);
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		question.setCompanyId(info.getCompanyId());
		RestResponse<String> response = new RestResponse<String>();
		if(Objects.equals(question.getStatus(), "1")){
			return response.rel(false).message("已经通过审核,无法修改!");
		}
		boolean flag = questionService.update(question);
		response.rel(flag);
		if (flag) {
			return response.data(UPDATE_SUCCESS);
		} else {
			return response.message(UPDATE_FAILURE);
		}
	}

	@ApiOperation(value = "删除题目", notes = "删除题目")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "题目ID", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "cityId", value = "城市ID(传修改后的城市id)", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "type", value = "删除类型: 1删除未通过审核题目 2 删除通过审核题目", required = true, dataTypeClass = Integer.class)
	})
	@DeleteMapping("delete")
	public RestResponse<String> delete(@RequestHeader(TOKEN) String token,String id,String cityId,Integer type) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		boolean flag = questionService.delete(id,cityId,type,info.getCompanyId());
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(DELETE_SUCCESS);
		} else {
			return response.message(DELETE_FAILURE);
		}
	}

	@ApiOperation(value = "审核题目", notes = "审核题目")
	@ApiImplicitParam(name = "question", value = "题目对象", dataTypeClass = Question.class)
	@PostMapping("audit")
	public RestResponse<String> audit(@RequestHeader(TOKEN) String token,@Valid  @RequestBody Question question) throws Exception {
		log.info("question:{}", question);
		synchronized (this) {
			IJWTInfo info = jwtUtils.getInfoFromToken(token);
			question.setApproveUser(info.getUserId());
			if(question.getStatus()==1){
				//设置题目编号
				Integer count = 1;
				Question ques = questionService.selectOne(new EntityWrapper<Question>().setSqlSelect("MAX(CAST(`code` AS SIGNED)) as code").eq("status", 1).eq("company_id", info.getCompanyId()));
				if(Objects.nonNull(ques)&&Objects.nonNull(ques.getCode())){
					count = Integer.valueOf(ques.getCode())+1;
				}
				question.setCode((count).toString());
			}
			boolean flag = questionService.updateById(question);
			RestResponse<String> response = new RestResponse<String>().rel(flag);
			if (flag) {
				return response.data("审核成功");
			} else {
				return response.message("审核失败");
			}
		}
	}
	
	@ApiOperation(value = "查询题目数量", notes = "查询题目数量")
	@GetMapping("count")
	public RestResponse<List<CountVo>> count(@RequestHeader(TOKEN) String token,String cityIds) throws Exception{
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		List<CountVo> list = questionService.count(info.getCompanyId(),cityIds);
		return  new RestResponse<List<CountVo>>().rel(true).data(list);
	}
	
	@ApiOperation(value = "根据公司获取回收站内的题目", notes = "获取题库（请求头添加token）")
	@ApiImplicitParam(name = "searchQuestionPo", value = "查询参数", dataTypeClass= SearchQuestionPo.class)
	@PostMapping("queryRecovery")
	public RestResponse<Page<QuestionVo>> queryRecovery(@RequestHeader(TOKEN) String token,@RequestBody SearchQuestionPo po) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		po.setCompanyId(info.getCompanyId());
		Page<QuestionVo> pageList = questionService.queryRecovery(new Page<QuestionVo>(po.getCurrent(), po.getSize()), po);
		if(Objects.isNull(pageList)){
			pageList = new Page<>();
			return new RestResponse<Page<QuestionVo>>().rel(true).data(pageList);
		}
		return new RestResponse<Page<QuestionVo>>().rel(true).data(pageList);
	}
	
	@ApiOperation(value = "恢复收站内的题目", notes = "恢复收站内的题目（请求头添加token）")
	@ApiImplicitParam(name = "updateStatePo", value = "恢复回收站内的题目", dataTypeClass= UpdateStatePo.class)
	@PostMapping("updateState")
	public RestResponse<String> updateState(@RequestHeader(TOKEN) String token,@Valid @RequestBody UpdateStatePo po) throws Exception{
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		po.setCompanyId(info.getCompanyId());
		boolean flag = questionService.updateState(po);
		RestResponse<String> response = new RestResponse<>();
		response.rel(flag);
		if (flag) {
			return response.data(UPDATE_SUCCESS);
		} else {
			return response.message(UPDATE_FAILURE);
		}
	}
}
