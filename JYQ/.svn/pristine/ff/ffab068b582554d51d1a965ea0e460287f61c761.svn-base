package com.hengyu.contract.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
 * 中介合同表
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jyq_contract")
public class Contract extends Model<Contract> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(hidden = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 房屋坐落
     */
    @ApiModelProperty(value = "房屋坐落")
    private String houseAddress;
    
    /**
     * 签约备案时间：精确到时分秒
     */
    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recordDate;
    
    /**
     * 买受人编号：多个用逗号隔开（乙方）
     */
    @ApiModelProperty(hidden = true)
    private String msrId;
    
    /**
     * 出卖人编号：多个用逗号隔开（甲方）
     */
    @ApiModelProperty(hidden = true)
    private String cmrId;
    
    /**
     * 审批 0 待审批 1通过 2未通过
     */
    @ApiModelProperty(hidden = true)
    private Integer approval;
    
    /**
     * 审批人
     */
    @ApiModelProperty(hidden = true)
    private Integer approvalUser;
    
    /**
     * 操作员名称(合同页面获取)
     */
    @ApiModelProperty(value = "操作员名称(合同页面获取)")
    private String operator;
    
    /**
     * 删除合同是否存在：0、存在  1、不存在
     */
    @ApiModelProperty(hidden = true)
    private Integer delFlag;
    
    /**
     * 主用户的编号(引用主用户表)（当前登陆系统的人）
     */
    @ApiModelProperty(hidden = true)
    private Integer mainUserId;
    
    /**
     * 合同路径
     */
    @ApiModelProperty(hidden = true)
    private String path;
    
    /**
     * 门店编号
     */
    @ApiModelProperty(value = "门店编号")
    private Integer htmdId;
    
    /**
     * 合同编号
     */
    @NotBlank(message="合同编号不能为空")
    @ApiModelProperty(hidden = true)
    private String contractNumber;
    
    /**
     * 合同类型   1：租赁合同 2：一手房合同  3：二手房合同
     */
    @ApiModelProperty(value = "合同类型   1：租赁合同 2：一手房合同  3：二手房合同")
    @NotNull(message="合同类型不能为空")
    private Integer type;
    
    /**
     * 当前用户的公司（引用公司表）
     */
    @ApiModelProperty(hidden = true)
    private Integer companyId;
    
    /**
     * 录入方式 4：在线录入  5：补录
     */
    @ApiModelProperty(value = "录入方式 4：在线录入  5：补录")
    private Integer entryMode;
    
    /**
     * 签约价格
     */
    @ApiModelProperty(value = "签约价格")
    private double signPrice;
    
    /**
     * 引用的模板
     */
    @ApiModelProperty(value = "引用的模板")
    private Integer templateId;
    
    
    /**
     * 买方姓名
     */
    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private String  msname ;
    
    /**
     * 卖方姓名
     */
    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private String  cmname ;
    
    /**
     * 类型名称
     */
    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private String typeName;
    
    /**
     * 录入方式
     */
    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
  	private String 	entryName;
    
    /**
     * 下载地址
     */
    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private String 	downloadPath;
    
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
