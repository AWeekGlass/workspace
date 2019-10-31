package com.hengyu.contract.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 中介出卖人表（甲方）
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jyq_seller")
public class Seller extends Model<Seller> {

    private static final long serialVersionUID = 1L;

    /**
     * 出卖人编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 出卖人姓名
     */
    private String cmname;
    /**
     * 联系电话
     */
    private String cmphone;
    /**
     * 证件号码
     */
    private String cmcard;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
