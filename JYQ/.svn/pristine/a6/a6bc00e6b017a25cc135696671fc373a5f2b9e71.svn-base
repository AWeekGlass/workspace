package com.hengyu.contract.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 审批记录表
 * </p>
 *
 * @author allnas
 * @since 2018-09-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jyq_approval")
public class Approval extends Model<Approval> {

    private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 合同id
     */
    @ApiModelProperty(value = "合同id")
    private Integer contractId;
    
    /**
     * 审批人
     */
    @ApiModelProperty(value = "审批人")
    private Integer operator;
    
    /**
     * 申请人
     */
    @ApiModelProperty(value = "申请人")
    private Integer apply;
    
    /**
     * 审核状态 1通过 2驳回 3撤销 4待审批
     */
    @ApiModelProperty(value = "审核状态 1通过 2驳回 3撤销 4待审批")
    private Integer status;
    
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    
    /**
     * 审批意见(审批时传)
     */
    @ApiModelProperty(value = "审批意见")
    private String opinion;
    
    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    /**
     * 公司id
     */
    @ApiModelProperty(hidden = true)
    private Integer companyId;
    
    /**
     * 申请事由
     */
    @ApiModelProperty(value = "申请事由")
    private String cause;
    
    /**
     * 期望解决时间(单位分钟)
     */
    @ApiModelProperty(value = "期望解决时间(单位分钟)")
    private String time; 
    
    /**
     * 审批编号
     */
    @ApiModelProperty(value = "审批编号")
    private String number;
    
    /**
     * 转交人
     */
    @ApiModelProperty(value = "转交(转交时传值)")
    @TableField(exist=false)
    private Integer transfer;
    
    /**
     * 合同内容
     */
    @ApiModelProperty(value = "合同内容")
    @TableField(exist=false)
    private String  contractUrl;
    
    /**
     * 审批文件
     */
    @ApiModelProperty(value = "审批文件")
    @TableField(exist=false)
    private List<ApprovalFile> files;
    
    /**
     * 审批记录
     */
    @ApiModelProperty(value = "审批记录")
    @TableField(exist=false)
    private List<ApprovalRecord> records;
    
    /**
     * 审批时间
     */
    @ApiModelProperty(value = "审批时间")
    @TableField(exist=false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date approvalTime;
    
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
