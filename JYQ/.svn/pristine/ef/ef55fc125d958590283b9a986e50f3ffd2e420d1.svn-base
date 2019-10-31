package com.hengyu.system.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 公司申请表
 * </p>
 *
 * @author allnas
 * @since 2018-10-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jyq_apply")
public class Apply extends Model<Apply> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 审批编号
     */
    private String number;
    
    /**
     * 状态 1 未审核 2 通过 3未通过
     */
    private Integer state;
    
    /**
     * 公司id
     */
    private Integer companyId;
    
    /**
     * 申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 驳回原因
     */
    private String reason;
    
    /**
     * 申请人
     */
    private Integer userId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
