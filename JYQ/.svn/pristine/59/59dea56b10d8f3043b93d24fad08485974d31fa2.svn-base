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
 * 中介买受人表（乙方）
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jyq_buyer")
public class Buyer extends Model<Buyer> {

    private static final long serialVersionUID = 1L;

    /**
     * 买受人编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 买受人姓名
     */
    private String msname;
    /**
     * 买受人联系电话
     */
    private String msphone;
    /**
     * 证件号码
     */
    private String mscard;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
