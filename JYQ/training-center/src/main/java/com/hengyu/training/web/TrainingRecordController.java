package com.hengyu.training.web;


import static com.hengyu.common.constant.CommonConstants.ADD_FAILURE;
import static com.hengyu.common.constant.CommonConstants.ADD_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.DELETE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.DELETE_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.UPDATE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.UPDATE_SUCCESS;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.training.entity.TrainingRecord;
import com.hengyu.training.service.TrainingRecordService;
import com.hengyu.training.vo.QueryTRecordPo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  培训关系前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-09-03
 */
@Api(value = "TrainingRecordController", tags = "培训关系Controller")
@RestController
@RequestMapping("/trainingRecord")
public class TrainingRecordController {

	@Autowired
	private TrainingRecordService trainingRecordService;
	
	/**
	 * 新增培训关系
	 * @param trainingRecord
	 * @return
	 */
	@ApiOperation(value = "新增培训关系", notes = "新增培训关系")
	@ApiImplicitParam(name = "trainingRecord", value = "培训关系对象", dataTypeClass = TrainingRecord.class)
	@PostMapping("/save")
	public RestResponse<String> save(TrainingRecord trainingRecord){
		boolean flag = trainingRecordService.save(trainingRecord);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(ADD_SUCCESS);
		}
		return response.message(ADD_FAILURE);
	}
	
	/**
	 * 修改培训关系
	 * @param trainingRecord
	 * @return
	 */
	@ApiOperation(value = "修改培训关系", notes = "修改培训关系")
	@ApiImplicitParam(name = "trainingRecord", value = "培训对象", dataTypeClass = TrainingRecord.class)
	@PatchMapping("/update")
	public RestResponse<String> update(TrainingRecord trainingRecord){
		boolean flag = trainingRecordService.update(trainingRecord);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(UPDATE_SUCCESS);
		}
		return response.message(UPDATE_FAILURE);
	}
	
	/**
	 * 删除培训关系
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "删除培训关系", notes = "删除培训关系")
	@ApiImplicitParam(name = "id", value = "培训关系id", dataTypeClass = String.class)
	@DeleteMapping("/delete/{id}")
	public RestResponse<String> delete(String id){
		boolean flag = trainingRecordService.delete(id);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(DELETE_SUCCESS);
		} 
		return response.message(DELETE_FAILURE);
	}
	
	/**
	 * 查询详情
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "查询详情", notes = "查询详情")
	@ApiImplicitParam(name = "id", value = "培训关系id", dataTypeClass = Integer.class)
	@GetMapping("queryById/{id}")
	public RestResponse<TrainingRecord> queryById(Integer id){
		TrainingRecord trainingRecord = trainingRecordService.queryById(id);
		return new RestResponse<TrainingRecord>().rel(true).data(trainingRecord);
	}
	
	/**
	 * 根据条件查询全部
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据条件查询全部", notes = "根据条件查询全部")
	@ApiImplicitParam(name = "trainingRecord", value = "培训关系id", dataTypeClass = TrainingRecord.class)
	@GetMapping("queryAll")
	public RestResponse<List<TrainingRecord>> queryAll(TrainingRecord trainingRecord){
		List<TrainingRecord> list = trainingRecordService.queryAll(trainingRecord);
		return new RestResponse<List<TrainingRecord>>().rel(true).data(list);
	}
	
	/**
	 * 根据条件分页查询
	 * @param trainingRecord
	 * @return
	 */
	@ApiOperation(value = "根据条件分页查询", notes = "根据条件分页查询")
	@ApiImplicitParam(name = "trainingRecord", value = "培训关系id", dataTypeClass = TrainingRecord.class)
	@GetMapping("queryList")
	public RestResponse<Page<TrainingRecord>> queryList(QueryTRecordPo po){
		Page<TrainingRecord> page = new Page<>(po.getCurrent(),po.getSize());
		page = trainingRecordService.queryList(page, po);
		return new RestResponse<Page<TrainingRecord>>().rel(true).data(page);
	}
}

