package com.hengyu.system.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.activerecord.Model;
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
 * 水印表
 * </p>
 *
 * @author allnas
 * @since 2018-10-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jyq_watermark")
public class Watermark extends Model<Watermark> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 文字水印
     */
    @ApiModelProperty(value = "文字水印")
    private String words;
    
    /**
     * 图片路径
     */
    @ApiModelProperty(hidden = true)
    private String picture;
    
    /**
     * 公司id
     */
    @ApiModelProperty(hidden = true)
    private Integer companyId;
    
    /**
     * 透明度单位%
     */
    @ApiModelProperty(value = "透明度单位%")
    private Integer transparency;
    
    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createTime;
    
    @NotNull
    @ApiModelProperty(value = "1.文字水印 2图片水印")
    private Integer type;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
