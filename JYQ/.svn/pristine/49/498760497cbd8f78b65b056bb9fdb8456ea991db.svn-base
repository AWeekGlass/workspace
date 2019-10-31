package com.hengyu.exam.po;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SubmitAnswerPo {

    @ApiModelProperty(value = "考试记录id")
	private	Integer testUserId;
    
    @NotNull
    @ApiModelProperty(value = "试卷id")
    private Integer paperId;
    
    
    @ApiModelProperty(value = "答案")
    private String  answerJson;
    
    @ApiModelProperty(value = "考生id")
    private Integer userId;
    
    
}
