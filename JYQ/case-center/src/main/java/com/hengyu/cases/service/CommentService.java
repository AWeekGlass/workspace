package com.hengyu.cases.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.hengyu.cases.entity.Comment;
import com.hengyu.cases.vo.CommentVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
public interface CommentService extends IService<Comment> {
	
	/**
	 * 查看案例回复信息
	 * @param id
	 * @return
	 */
	List<CommentVo> queryCommentByCaseId(Integer caseId);
}
