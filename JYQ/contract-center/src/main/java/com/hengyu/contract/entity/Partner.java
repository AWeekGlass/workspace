package com.hengyu.contract.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 中介签约附件表
 * </p>
 *
 * @author allnas
 * @since 2018-08-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jyq_partner")
public class Partner extends Model<Partner> {

    private static final long serialVersionUID = 1L;

    /**
     * 附件id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(hidden = true)
    private Integer id;
    
    /**
     * 附件路径
     */
    @ApiModelProperty(hidden = true)
    private String attachmentAddress;
    
    /**
     * 关联模板id
     */
    @ApiModelProperty(value = "关联模板id")
    private Integer contractType;
    
    /**
     * 上传时间
     */
    @ApiModelProperty(hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private Date uploadTime;
    
    /**
     * 公司id（引用公司表）
     */
    @ApiModelProperty(hidden = true)
    private Integer companyId;
    
    /**
     * 文件名称
     */
    @ApiModelProperty(hidden = true)
    private String fileName;
    
    /**
     * 文件大小
     */
    @ApiModelProperty(hidden = true)
    private String size;
    
    /**
     * 行业id
     */
    @ApiModelProperty(value = "行业id")
    private Integer industryId;
    
    /**
     * 创建者
     */
    @ApiModelProperty(hidden = true)
    private Integer createUser;
    
    /**
     * 行业名称
     */
    @ApiModelProperty(hidden = true)
    @TableField(exist=false)
    private String industryName;
    
    /**
     * 公司名称
     */
    @ApiModelProperty(hidden = true)
    @TableField(exist=false)
    private String companyName;
    
    /**
     * 行业名称
     */
    @ApiModelProperty(hidden = true)
    @TableField(exist=false)
    private String templateName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
