package com.hengyu.contract.service.impl;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.common.util.FileUtils;
import com.hengyu.common.util.SmsUtils;
import com.hengyu.contract.dao.ApprovalDAO;
import com.hengyu.contract.entity.Approval;
import com.hengyu.contract.entity.ApprovalFile;
import com.hengyu.contract.entity.ApprovalRecord;
import com.hengyu.contract.entity.Contract;
import com.hengyu.contract.po.SearchApprovalPo;
import com.hengyu.contract.service.ApprovalFileService;
import com.hengyu.contract.service.ApprovalRecordService;
import com.hengyu.contract.service.ApprovalService;
import com.hengyu.contract.service.ContractService;
import com.hengyu.contract.vo.ApprovalVo;

/**
 * <p>
 * 审批记录表 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-09-19
 */
@Service
public class ApprovalServiceImpl extends ServiceImpl<ApprovalDAO, Approval> implements ApprovalService {

	@Autowired
	private ApprovalDAO approvalDAO;
	
	@Value("${contract.upload-file-path}")
	private String uploadFilePath;

	@Autowired
	private ContractService contractService;

	@Autowired
	private ApprovalRecordService recordService;

	@Autowired
	private ApprovalFileService fileService;
	
	@Autowired
	private FileUtils fileUtils;
	
	@Autowired
	private SmsUtils smsUtils;
	
	@Value("${contract.send_message_tpl_id}")
	private String messageTplId;

	@Override
	public boolean save(Approval approval) {
		approval.setCreateTime(new Date());
		// 生成审批编号
		approval.setNumber(getNumber(approval.getCompanyId()));
		approval.setStatus(4);
		boolean flag = insert(approval);
		// 新增审批记录
		ApprovalRecord record = new ApprovalRecord();
		record.setApprovalId(approval.getId());
		//待审批
		record.setStatus(5);
		record.setCreateTime(new Date());
		record.setOperator(approval.getOperator());
		recordService.insert(record);
		return flag;
	}

	@Override
	public Page<Approval> getRecord(Page<Approval> page,SearchApprovalPo po) {
		List<Approval> list = approvalDAO.getRecord(page,po);
		for (Approval approval : list) {
			ApprovalVo approvalVo = new ApprovalVo();
			List<ApprovalFile> files = fileService
					.selectList(new EntityWrapper<ApprovalFile>().eq("approval_id", approval.getId()));
			List<ApprovalRecord> records = recordService.selectList(new EntityWrapper<ApprovalRecord>().eq("approval_id", approval.getId()));
			approval.setFiles(files);
			approval.setRecords(records);
		}
		return page.setRecords(list);
	}

	@Override
	public Approval getById(Integer id) {
		Approval approval = approvalDAO.queryById(id);
		//读取审批的合同内容
		if(Objects.nonNull(approval.getContractUrl())){
			String path = approval.getContractUrl();
			if (Objects.nonNull(path)) {
				String fileName = path.substring(path.lastIndexOf("/") + 1);
				String ftpPath = path.substring(0, path.lastIndexOf("/"));
				try {
					path = fileUtils.getFileByName(ftpPath, fileName);
					approval.setContractUrl(path);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//查询审批附件
		List<ApprovalFile> files = fileService.selectList(new EntityWrapper<ApprovalFile>().eq("approval_id", id));
		//查询审批记录
		List<ApprovalRecord> records = recordService.getList(id);
		approval.setFiles(files);
		approval.setRecords(records);
		return approval;
	}

	@Override
	public boolean delete(Integer id) {
		//删除审批记录
		boolean flag = delete(new EntityWrapper<Approval>().eq("contract_id", id));
		List<ApprovalFile> approvalFiles = fileService
				.selectList(new EntityWrapper<ApprovalFile>().eq("approval_id", id));
		//删除文件
		for (ApprovalFile approvalFile : approvalFiles) {
			fileUtils.deleteFTPFile(approvalFile.getPath());
		}
		fileService.delete(new EntityWrapper<ApprovalFile>().eq("approval_id", id));
		//删除审批记录
		recordService.delete(new EntityWrapper<ApprovalRecord>().eq("approval_id", id));
		return flag;
	}

	/**
	 * 生成审批编号
	 * 
	 * @param companyId
	 * @return
	 */
	private String getNumber(Integer companyId) {
		// 查询今天已经有几个审批
		Integer count = selectCount(new EntityWrapper<Approval>().eq("company_id", companyId).ge("create_time",
				DateFormatUtils.format(new Date(), "yyyy-MM-dd 00:00:00")));
		// 获取当天日期
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateNowStr = sdf.format(d);
		// 格式化
		DecimalFormat df = new DecimalFormat("0000");
		String str = df.format(count + 1);
		dateNowStr = dateNowStr + str;
		return dateNowStr;
	}

	@Override
	public boolean update(Approval approval,MultipartFile[] files) {
		//更新
		Approval approval1 = selectById(approval.getId());
		if(!Objects.equals(approval1.getOperator(), approval.getOperator())){
			// 查询审批人手机号
			String phone = getPhone(approval.getOperator());
			if (Objects.nonNull(phone)) {
				// 发送短信
				try {
					smsUtils.sendSms(phone, messageTplId, null);
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		boolean flag = updateById(approval);
		//删除原有文件
		List<ApprovalFile> approvalFiles = fileService
				.selectList(new EntityWrapper<ApprovalFile>().eq("approval_id", approval.getId()));
		//删除文件
		for (ApprovalFile approvalFile : approvalFiles) {
			fileUtils.deleteFTPFile(approvalFile.getPath());
		}
		fileService.delete(new EntityWrapper<ApprovalFile>().eq("approval_id", approval.getId()));
		//如果文件不为空则新增文件
		if(Objects.nonNull(files)){
			for (MultipartFile file : files) {
				ApprovalFile approvalFile = new ApprovalFile();
				approvalFile.setCreateTime(new Date());
				approvalFile.setApprovalId(approval.getId());
				//获取文件名
				approvalFile.setName(file.getName());
				//获取文件保存路径
				String path = fileUtils.uploadFtpFile(file,uploadFilePath);
				approvalFile.setPath(path);
				//新增审批文件
				fileService.insert(approvalFile);
			}
		}
		return flag;
	}

	@Override
	public boolean updateState(Approval approval) {
		if(Objects.isNull(approval.getStatus())){
			return false;
		}
		boolean flag = false;
		switch (approval.getStatus()) {
		case 1://通过
		case 2://驳回
		case 3://撤销
			flag = updateById(approval);
			if(flag){
				List<ApprovalRecord> records = recordService.selectList(new EntityWrapper<ApprovalRecord>().eq("approval_id", approval.getId()).eq("operator", approval.getOperator()));
				if(Objects.nonNull(records) && records.size()>0){
					ApprovalRecord record = records.get(0);
					record.setStatus(approval.getStatus());
					recordService.updateById(record);
					Contract contract = new Contract();
					contract.setId(approval.getContractId());
					contract.setApproval(approval.getStatus());
					contract.setApprovalUser(approval.getOperator());
					contractService.updateById(contract);
				}
			}
			break;
		case 4://转交
			List<ApprovalRecord> records = recordService.selectList(new EntityWrapper<ApprovalRecord>().eq("approval_id", approval.getId()).eq("operator", approval.getOperator()));
			if(Objects.nonNull(records) && records.size()>0){
				ApprovalRecord record = records.get(0);
				record.setStatus(approval.getStatus());
				recordService.updateById(record);
				ApprovalRecord record2 = record;
				//设置处理人
				record2.setOperator(approval.getTransfer());
				//设置处理状态为5
				record2.setStatus(5);
				recordService.insert(record2);
				// 查询审批人手机号
				String phone = getPhone(approval.getTransfer());
				if (Objects.nonNull(phone)) {
					// 发送短信
					try {
						smsUtils.sendSms(phone, messageTplId, null);
					} catch (IOException e) {
						e.printStackTrace();
						return false;
					}
				}
			}
			break;
		default:
			break;
		}
		return flag;
	}

	@Override
	public String getPhone(Integer userId) {
		String phone = approvalDAO.getPhone(userId);
		return phone;
	}
}
