package com.hengyu.contract.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 中介合同编号表
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jyq_contract_number")
public class ContractNumber extends Model<ContractNumber> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 合同编号
     */
    private String number;
    /**
     * 合同类型
     */
    private Integer type;
    /**
     * 公司id
     */
    @TableField("company_id")
    private Integer companyId;
    /**
     * 编号使用状态 0.未使用 1.已使用
     */
    private Integer status;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
