package com.hengyu.training.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
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
 * 培训心得

 * </p>
 *
 * @author allnas
 * @since 2018-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jyq_training_experience")
public class TrainingExperience extends Model<TrainingExperience> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    @ApiModelProperty("培训id")
    private String trainingId;

    @NotBlank(message="标题不能为空")
    @ApiModelProperty("标题")
    private String title;

    @NotNull(message="分类不能为空")
    @ApiModelProperty("分类")
    private Integer caseCategory;

    @NotBlank(message="内容不能为空")
    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("公司id")
    private Integer companyId;

    @ApiModelProperty("置顶时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date topTime;

    @ApiModelProperty("状态 0草稿 1正常")
    private Integer state;

    @ApiModelProperty("是否推荐 0不推荐 1推荐")
    private Integer recommend;

    @ApiModelProperty("创建时间")
	@TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    
    @ApiModelProperty("推荐人")
    private Integer refId;
    
    @ApiModelProperty("置顶人")
    private Integer topId;
    
    @ApiModelProperty("是否删除 0删除 1不删")
    private Integer delFlag;
    
    @ApiModelProperty("培训名称")
    private String trainingName;
    
    @ApiModelProperty("推荐人姓名")
    @TableField(exist=false)
    private String refName;
    
    @ApiModelProperty("发布人姓名")
    @TableField(exist=false)
    private String userName;
    
    @ApiModelProperty("置顶人姓名")
    @TableField(exist=false)
    private String topName;
    
    @ApiModelProperty("点赞人数")
    @TableField(exist=false)
    private Integer like;
    
    @ApiModelProperty("是否点赞 0没点赞1点赞")
    @TableField(exist=false)
    private Integer isLike;
    
    @ApiModelProperty("分类名称")
    @TableField(exist=false)
    private String categoryName;
    
    @ApiModelProperty("分类名称")
    @TableField(exist=false)
    private String roleName;
    
    @ApiModelProperty("店铺名称")
    @TableField(exist=false)
    private String storeName;
    
    @ApiModelProperty("店铺Id")
    @TableField(exist=false)
    private Integer storeId;
    
    @ApiModelProperty("回复数据量")
    @TableField(exist=false)
    private Integer commentNum;
    
    @ApiModelProperty("查询类型 1根据条件查询列表 2查询我的发表")
    @TableField(exist=false)
    private Integer type;
    
    @ApiModelProperty("用户头像")
    @TableField(exist=false)
    private String headPortrait;
    

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
