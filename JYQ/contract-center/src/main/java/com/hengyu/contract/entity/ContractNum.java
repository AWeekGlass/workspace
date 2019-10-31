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
 * 中介合同编号配置表
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jyq_contract_num")
public class ContractNum extends Model<ContractNum> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 公司编号（引用公司表）
     */
    private Integer companyId;
    
    /**
     * 租房设置的编号
     */
    private String rentingNum;
    
    /**
     * 新房的编号设置
     */
    private String newHouseNum;
    
    /**
     * 存量房的编号设置
     */
    private String stockRoomNum;
    
    /**
     * “租房设置”自增长的起点
     */
    private String rentingSta;
    
    /**
     * “新房设置”自增长的起点
     */
    private String newHouseSta;
    
    /**
     * “存量房设置”设置自增长的起点
     */
    private String stockRoomSta;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
