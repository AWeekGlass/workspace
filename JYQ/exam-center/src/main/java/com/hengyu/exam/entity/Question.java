package com.hengyu.exam.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author allnas
 * @since 2018-09-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("edu_question")
public class Question extends Model<Question> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    @ApiModelProperty(value = "公司id")
    private Integer companyId;
    
    @ApiModelProperty(value = "")
    private String code;
    
    @ApiModelProperty(value = "题目类型  1单选 2多选 3判断 4填空 5简答")
    private Integer type;
    
    @ApiModelProperty(value = "题干")
    private String question;

    @ApiModelProperty(value = "备注")
    private String mark;

    @ApiModelProperty(value = "答案")
    private String result;

    @ApiModelProperty(value = "选项 : 选择题用")
    private String options;

    @ApiModelProperty(value = "题目种类")
    private Integer category;

    @ApiModelProperty(value = "城市id")
    private String cityId;

    @ApiModelProperty(value = "是否分享")
    private Integer share;

    @ApiModelProperty(value = "出题人")
    private Integer adminId;

    @ApiModelProperty(value = "审核人")
    private Integer approveUser;

    @ApiModelProperty(value = "审核意见")
    private String remark;

    @ApiModelProperty(value = "状态 1通过 2 未通过 3驳回")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
