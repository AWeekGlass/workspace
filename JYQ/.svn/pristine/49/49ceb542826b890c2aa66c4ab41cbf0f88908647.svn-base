package com.hengyu.training.web;

import static com.hengyu.common.constant.CommonConstants.ADD_FAILURE;
import static com.hengyu.common.constant.CommonConstants.ADD_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.DELETE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.DELETE_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.UPDATE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.UPDATE_SUCCESS;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.plugins.Page;
import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.FileUtils;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.training.entity.Training;
import com.hengyu.training.service.TrainingService;
import com.hengyu.training.vo.QueryTrainPo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 培训前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-09-03
 */
@Api(value = "TrainingController", tags = "培训Controller")
@RestController
@RequestMapping("/training")
public class TrainingController {

	@Value("${training.upload-img-path}")
	private String uploadFilePath;
	
	@Autowired
	private TrainingService trainingService;

	@Autowired
	private JWTUtils jwtUtils;
	
	@Autowired
	private FileUtils fileUtils; 

	/**
	 * 新增培训
	 * 
	 * @param token
	 * @param training
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "新增培训", notes = "新增培训")
	@ApiImplicitParam(name = "training", value = "新增培训对象", dataTypeClass = Training.class)
	@PostMapping("/save")
	public RestResponse<String> save(@RequestHeader("token") String token,@Valid Training training,@RequestParam(value = "logoFile", required = false) MultipartFile logoFile) throws Exception {
		RestResponse<String> response = new RestResponse<String>();
		if(training.getSignInStartTime().after(training.getSignInEndTime())){
			response.rel(false).message("签到开始时间不得晚于签到结束时间！");
		}else if(training.getTrainingStartTime().after(training.getTrainingEndTime())){
			response.rel(false).message("培训开始时间不得晚于开始结束时间！");
		}else if(training.getSignInStartTime().after(training.getTrainingStartTime())) {
			response.rel(false).message("签到开始时间不得晚于活动开始时间！");
		}else if(training.getSignInEndTime().after(training.getTrainingStartTime())) {
			response.rel(false).message("签到结束时间不得晚于活动开始时间！");
		}
		//获取用户公司
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		//上传图片至ftp
		if(Objects.nonNull(logoFile)){
			String path = fileUtils.uploadFtpImg(logoFile, uploadFilePath);
			training.setCover(path);
		}
		training.setCompanyId(info.getCompanyId());
		boolean flag = trainingService.save(training);
		response.rel(flag);
		if (flag) {
			return response.data(ADD_SUCCESS);
		}
		return response.message(ADD_FAILURE);
	}

	/**
	 * 修改培训记录
	 * @param token
	 * @param training
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "修改培训记录", notes = "修改培训记录")
	@ApiImplicitParam(name = "training", value = "培训对象", dataTypeClass = Training.class)
	@PatchMapping("/update")
	public RestResponse<String> update(@RequestHeader("token") String token, Training training,@RequestParam(value = "logoFile", required = false) MultipartFile logoFile) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		//删除原有图片
		Training train = trainingService.selectById(training.getId());
		if(Objects.nonNull(train.getCover())){
			fileUtils.deleteFTPFile(train.getCover());
		}
		if(Objects.nonNull(logoFile)){
			//上传图片至ftp
			String path = fileUtils.uploadFtpImg(logoFile, uploadFilePath);
			training.setCover(path);
		}
		training.setCompanyId(info.getCompanyId());
		
		boolean flag = trainingService.update(training);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(UPDATE_SUCCESS);
		}
		return response.message(UPDATE_FAILURE);
	}
	
	/**
	 * 删除培训记录
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "删除培训", notes = "删除培训")
	@ApiImplicitParam(name = "id", value = "培训记录id", dataTypeClass = String.class)
	@DeleteMapping("/delete/{id}")
	public RestResponse<String> delete(@PathVariable String id){
		boolean flag = trainingService.delete(id);
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
	@ApiImplicitParam(name = "id", value = "培训记录id", dataTypeClass = Integer.class)
	@GetMapping("queryById/{id}")
	public RestResponse<Training> queryById(@PathVariable Integer id){
		Training training = trainingService.queryById(id);
		return new RestResponse<Training>().rel(true).data(training);
	}
	
	/**
	 * 分页查询
	 * @param po
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@ApiImplicitParam(name = "queryTrainPo", value = "分页查询参数", dataTypeClass = QueryTrainPo.class)
	@GetMapping("queryList")
	public RestResponse<Page<Training>> queryList(QueryTrainPo po){
		Page<Training> page = new Page<>(po.getCurrent(),po.getSize());
		page = trainingService.queryList(page, po);
		return new RestResponse<Page<Training>>().rel(true).data(page);
	}
	
	/**
	 * 签到
	 * @param trainingId
	 * @return 0 签到成功 1不在签到期间 2已经签到
	 * @throws Exception 
	 */
	@ApiOperation(value = "签到", notes = "签到")
	@ApiImplicitParam(name = "trainingId", value = "培训id", dataTypeClass = Integer.class)
	@GetMapping("/signIn/{id}")
	public RestResponse<Integer> signIn(@RequestHeader("token") String token,@PathVariable Integer trainingId) throws Exception{
		//获取用户公司
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		Integer code = trainingService.signIn(trainingId, info);
		return new RestResponse<Integer>().rel(true).data(code);
	}
}
