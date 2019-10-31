package com.hengyu.system.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 申请加入企业表
 * </p>
 *
 * @author allnas
 * @since 2018-12-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jyq_join_company")
public class JoinCompany extends Model<JoinCompany> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    
    /**
     * 用户id
     */
    private Integer userId;
    
    /**
     * 用户名
     */
    private String userName;
    
    /**
     * 申请加入的公司
     */
    private Integer companyId;
    
    /**
     * 审批人
     */
    private Integer approveUser;
    
    /**
     * 是否通过审核 0 未审核 1 通过 2 未通过
     */
    private Integer state;
    
    /**
     * 申请加入时间
     */
	@TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    /**
     * 审批时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date approveTime;
    
    /**
     * 消息状态 0未读 1已读
     */
    private Integer status;
    
    /**
     * 手机号
     */
    @TableField(exist=false)
    private String phone;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
