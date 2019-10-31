package com.hengyu.system.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户折扣券表
 * </p>
 *
 * @author allnas
 * @since 2018-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jyq_user_discount")
public class UserDiscount extends Model<UserDiscount> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("折扣id")
    private Integer discountId;

    @ApiModelProperty("创建（领取）时间")
    private Date createTime;

    @ApiModelProperty("过期时间")
    private Date endTime;
    
    @ApiModelProperty("状态 0未使用 1已使用 ")
    private Integer state;
    
    @ApiModelProperty("折扣信息")
    @TableField(exist=false)
    private Discount discount;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
