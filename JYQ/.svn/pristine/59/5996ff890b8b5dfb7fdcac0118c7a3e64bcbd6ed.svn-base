package com.hengyu.contract.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

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
 * 中介合同上传附件表
 * </p>
 *
 * @author allnas
 * @since 2018-08-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jyq_htenclosure")
public class Htenclosure extends Model<Htenclosure> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 附件路径
     */
    @ApiModelProperty(hidden = true)
    private String htpath;
    
    /**
     * 合同id（引用合同表）
     */
    @ApiModelProperty(value = "合同id")
    private Integer htId;
    
    @ApiModelProperty(value = "城市id")
    private Integer cityId;
    
    @ApiModelProperty(value = "省id")
    private Integer provinceId;
    
    /**
     * 文件名称
     */
    @ApiModelProperty(hidden = true)
    private String name;
    
    /**
     * 文件大小
     */
    @ApiModelProperty(hidden = true)
    private String size;
    
    /**
     * 创建者
     */
    @ApiModelProperty(hidden = true)
    private Integer createUser;
    
    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    
    
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
