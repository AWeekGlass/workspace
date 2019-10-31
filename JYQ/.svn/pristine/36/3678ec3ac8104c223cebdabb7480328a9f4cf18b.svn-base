package com.hengyu.exam.po;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class QuerPaperDetailPo {
	
    @ApiModelProperty(value = "用户id")
	private Integer userId; 
    
    @NotBlank
    @ApiModelProperty(value = "试卷id")
    private Integer paperId;
    
    /**
     * 试卷id
     */
    @ApiModelProperty(hidden = true)
    private Integer id;
    
    @ApiModelProperty(value = "查询类型 1 查询未批阅试卷  2 查询已经批阅试卷 3阅卷中")
    private Integer type;

}
