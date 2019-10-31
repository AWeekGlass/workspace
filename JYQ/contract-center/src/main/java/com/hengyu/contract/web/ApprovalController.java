package com.hengyu.contract.web;

import static com.hengyu.common.constant.CommonConstants.ADD_FAILURE;
import static com.hengyu.common.constant.CommonConstants.ADD_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.DELETE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.DELETE_SUCCESS;
import static com.hengyu.common.constant.CommonConstants.UPDATE_FAILURE;
import static com.hengyu.common.constant.CommonConstants.UPDATE_SUCCESS;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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

import com.baomidou.mybatisplus.plugins.Page;
import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.FileUtils;
import com.hengyu.common.util.JWTUtils;
import com.hengyu.common.util.JsonUtils;
import com.hengyu.common.util.SmsUtils;
import com.hengyu.contract.entity.Approval;
import com.hengyu.contract.entity.ApprovalFile;
import com.hengyu.contract.po.AddcontractPo;
import com.hengyu.contract.po.SearchApprovalPo;
import com.hengyu.contract.service.ApprovalFileService;
import com.hengyu.contract.service.ApprovalService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 申请审批表 前端控制器
 * </p>
 *
 * @author allnas
 * @since 2018-09-19
 */
@Api(value = "ApprovalController", tags = "审批Controller")
@RestController
@RequestMapping("/approval")
public class ApprovalController {

	@Autowired
	private ApprovalService approvalService;

	@Autowired
	private JWTUtils jwtUtils;

	@Autowired
	private FileUtils fileUtils;

	@Value("${contract.upload-file-path}")
	private String uploadFilePath;

	@Value("${contract.send_message_tpl_id}")
	private String messageTplId;
	
	@Value("${contract.send_message}")
	private boolean sendMessage;

	@Autowired
	private ContractController contractController;

	@Autowired
	private ApprovalFileService fileService;

	@Autowired
	private SmsUtils smsUtils;

	/**
	 * 新增审批
	 * 
	 * @param token
	 * @param addcontractPo
	 * @param files
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "新增审批", notes = "新增审批")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "addcontractPo", value = "新增合同实体", dataTypeClass = AddcontractPo.class),
			@ApiImplicitParam(name = "files", value = "上传附件数组", dataTypeClass = MultipartFile.class) })
	@PostMapping("/save")
	public RestResponse<String> save(@RequestHeader("token") String token,
			@RequestParam(value = "files", required = false) MultipartFile[] files, String params) throws Exception {
		AddcontractPo addcontractPo = JsonUtils.getObject4JsonString(params, AddcontractPo.class);
		Approval approval = addcontractPo.getApproval();
		// 新增合同
		addcontractPo.getContract().setApproval(3);
		contractController.save(token, addcontractPo);
		// 获取公司和个人信息
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		approval.setContractId(addcontractPo.getContract().getId());
		approval.setCompanyId(info.getCompanyId());
		approval.setApply(info.getUserId());
		boolean flag = approvalService.save(approval);
		// 上传审批文件
		if (Objects.nonNull(files) && files.length > 0) {
			for (MultipartFile file : files) {
				ApprovalFile approvalFile = new ApprovalFile();
				approvalFile.setCreateTime(new Date());
				approvalFile.setApprovalId(approval.getId());
				// 获取文件名
				approvalFile.setName(file.getOriginalFilename());
				// 获取文件保存路径
				String path = fileUtils.uploadFtpFile(file, uploadFilePath);
				approvalFile.setPath(path);
				// 新增审批文件
				fileService.insert(approvalFile);
			}
		}
		if(sendMessage){
			// 查询审批人手机号
			String phone = approvalService.getPhone(approval.getOperator());
			if (Objects.nonNull(phone)) {
				// 发送短信
				smsUtils.sendSms(phone, messageTplId, null);
			}
		}

		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(ADD_SUCCESS);
		}
		return response.message(ADD_FAILURE);
	}

	/**
	 * 查询列表
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "查询列表", notes = "查询列表")
	@ApiImplicitParam(name = "po", value = "查询条件", dataTypeClass = SearchApprovalPo.class)
	@GetMapping("getRecord")
	public RestResponse<Page<Approval>> getRecord(@RequestHeader("token") String token, @Valid SearchApprovalPo po)
			throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		po.setUserId(info.getUserId());
		Page<Approval> page = new Page<>(po.getCurrent(),po.getSize());
		page = approvalService.getRecord(page,po);
		return new RestResponse<Page<Approval>>().rel(true).data(page);
	}

	/**
	 * id查询审批详情
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据id查询审批详情", notes = "根据id查询审批详情")
	@ApiImplicitParam(name = "id", value = "审批id", dataTypeClass = Integer.class)
	@GetMapping("getById/{id}")
	public RestResponse<Approval> getById(@PathVariable Integer id) {
		Approval approval = approvalService.getById(id);
		return new RestResponse<Approval>().rel(true).data(approval);
	}

	/**
	 * 删除审批记录
	 * 
	 * @param contractId
	 * @return
	 */
	@ApiOperation(value = "删除审批记录", notes = "删除审批记录")
	@ApiImplicitParam(name = "id", value = "审批id", dataTypeClass = Integer.class)
	@DeleteMapping("delete/{id}")
	public RestResponse<String> delete(@PathVariable Integer id) {
		boolean flag = approvalService.delete(id);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(DELETE_SUCCESS);
		}
		return response.message(DELETE_FAILURE);
	}

	/**
	 * 更新审批(发布者更新)
	 * 
	 * @param approval
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "更新审批(发布者更新)", notes = "更新审批(发布者更新)")
	@ApiImplicitParams({ @ApiImplicitParam(name = "approval", value = "审批实体", dataTypeClass = Approval.class),
			@ApiImplicitParam(name = "files", value = "上传附件数组", dataTypeClass = MultipartFile.class) })
	@PostMapping("update")
	public RestResponse<String> update(Approval approval,
			@RequestParam(value = "files", required = true) MultipartFile[] files) {
		boolean flag = approvalService.update(approval, files);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(UPDATE_SUCCESS);
		}
		return response.data(UPDATE_FAILURE);
	}

	/**
	 * 更新审状态(批准/驳回/撤销/转交)
	 * 
	 * @param approval
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "更新审批(批准/驳回/撤销/转交)", notes = "更新审批(批准/驳回/撤销/转交)")
	@ApiImplicitParam(name = "approval", value = "审批对象", dataTypeClass = Approval.class)
	@PostMapping("updateState")
	public RestResponse<String> updateState(@RequestBody Approval approval) throws Exception {
		boolean flag = approvalService.updateState(approval);
		RestResponse<String> response = new RestResponse<String>().rel(flag);
		if (flag) {
			return response.data(UPDATE_SUCCESS);
		}
		return response.data(UPDATE_FAILURE);
	}
	
	/**
	 * 催办
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	@ApiOperation(value = "催办", notes = "催办")
	@ApiImplicitParam(name = "userId", value = "用户id", dataTypeClass = Integer.class)
	@PostMapping("urge/{userId}")
	public RestResponse<String> urge(@PathVariable @NotNull Integer userId) throws IOException{
		String phone = approvalService.getPhone(userId);
		RestResponse<String> response = new RestResponse<>();
		if (Objects.nonNull(phone)) {
			boolean flag = true;
			if(sendMessage){
				// 发送短信
				flag = smsUtils.sendSms(phone, messageTplId, null);
				if(flag){
					return response.rel(flag).data("催办成功!");
				}
			}else {
				return response.rel(flag).data("催办成功!");
			}
			
//			
		}
		return response.rel(false).message("催办失败！");
	}

}
