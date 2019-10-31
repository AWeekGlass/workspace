package com.hengyu.user.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 更换手机号记录
 * </p>
 *
 * @author allnas
 * @since 2018-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jyq_change_phone")
public class ChangePhone extends Model<ChangePhone> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 用户id
     */
    private Integer userId;
    
    /**
     * 旧手机号
     */
    private String oldPhone;
    
    /**
     * 新手机号
     */
    private String newPhone;
    
	@TableField(fill = FieldFill.INSERT)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
	
    /**
     * 消息状态 0未读 1已读
     */
    private Integer status;
    
    /**
     * 是否通过审核 0 未审核 1 通过 2 未通过
     */
    private Integer state;
    
    /**
     * 公司id
     */
    private Integer companyId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
