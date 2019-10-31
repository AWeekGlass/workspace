package com.hengyu.training.entity;

import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 培训心得回复表
 * </p>
 *
 * @author allnas
 * @since 2018-12-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jyq_train_comment")
public class TrainComment extends Model<TrainComment> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NotNull(message="培训心得id不能为空")
    @ApiModelProperty("培训心得id")
    private Integer trainingExpId;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("父级id 根节点为0或者Null")
    private Integer parentId;

    @NotBlank(message="回复内容不能为空")
    @ApiModelProperty("回复内容")
    private String content;

    @ApiModelProperty("发表时间")
	@TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("回复类型 1 根节点 2子节点")
    private Integer type;
    
    @ApiModelProperty("公司id")
    private Integer companyId;
    
    @ApiModelProperty("顶级回复id")
    private Integer commentId;
    
    @ApiModelProperty("子回复")
    @TableField(exist = false)
    List<TrainComment> comments;
    
    @ApiModelProperty("用户名称")
    @TableField(exist = false)
    private String userName;
    
    @ApiModelProperty("父级用户名称")
    @TableField(exist = false)
    private String puserName;
    
    @ApiModelProperty("头像")
    @TableField(exist = false)
    private String headPortrait;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
