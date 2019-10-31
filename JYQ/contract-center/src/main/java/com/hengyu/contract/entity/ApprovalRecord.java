package com.hengyu.contract.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 审批记录表
 * </p>
 *
 * @author allnas
 * @since 2018-09-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jyq_approval_record")
public class ApprovalRecord extends Model<ApprovalRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 审批id
     */
    private Integer approvalId;
    
    /**
     * 审批人
     */
    private Integer operator;
    
    /**
     * 审核状态 1通过 2驳回
     */
    private Integer status;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    
    /**
     * 公司id
     */
    private Integer companyId;
    
    /**
     * 状态名称
     */
    @TableField(exist = false)
    private String 	sname;
    
    /**
     * 审批人姓名
     */
    @TableField(exist = false)
    private String userName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
