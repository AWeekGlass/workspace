package com.hengyu.user.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
 * 人员调动
 * </p>
 *
 * @author allnas
 * @since 2018-09-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jyq_transfer")
public class Transfer extends Model<Transfer> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 原部门id
     */
    @NotNull(message="原部门id不能为空")
    @ApiModelProperty(value = "原部门id")
    private Integer divisionOld;
    
    /**
     * 原部门名称
     */
    @NotBlank(message="原部门名称不能为空")
    @TableField(exist = false)
    @ApiModelProperty(value = "原部门名称")
    private String divisionOldName;
    
    /**
     * 原职位id
     */
    @NotNull(message="原职位id不能为空")
    @ApiModelProperty(value = "原职位id")
    private Integer positionOld;
    
    /**
     * 原职位名称
     */
    @NotBlank(message="原职位名称不能为空")
    @TableField(exist = false)
    @ApiModelProperty(value = "原职位名称")
    private String positionOldName;
    
    /**
     * 调动部门id
     */
    @NotNull(message="调动部门id不能为空")
    @ApiModelProperty(value = "调动部门id")
    private Integer division;
    
    /**
     * 调动部门名称
     */
    @NotBlank(message="调动部门名称不能为空")
    @TableField(exist = false)
    @ApiModelProperty(value = "调动部门名称")
    private String divisionName;
    
    /**
     * 调动职位id
     */
    @NotNull(message="调动职位id不能为空")
    @ApiModelProperty(value = "调动职位id")
    private Integer position;
    
    /**
     * 调动职位名称
     */
    @NotBlank(message="调动职位名称不能为空")
    @TableField(exist = false)
    @ApiModelProperty(value = "调动职位名称")
    private String positionName;
    
    /**
     * 调动人id
     */
    @NotNull(message="调动人id不能为空")
    @ApiModelProperty(value = "调动人id")
    private Integer userId;
    
    /**
     * 操作员id
     */
    @ApiModelProperty(value = "操作员id")
    private Integer operator;
    
    /**
     * 操作员名称
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "操作员名称")
    private String operatorName;
    
    /**
     * 被调动人员名称
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "被调动人员名称")
    private String userName;
    
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    
    /**
     * 数据调动情况
     */
    @ApiModelProperty(value = "数据调动情况")
    private String data;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
