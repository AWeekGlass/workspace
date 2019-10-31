package com.hengyu.cases.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hengyu.cases.entity.Comment;
import com.hengyu.cases.vo.CommentVo;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
public interface CommentDAO extends BaseMapper<Comment> {
	/**
	 * 查看案例回复信息
	 * 
	 * @param id
	 * @return
	 */
	List<CommentVo> queryCommentByCaseId(@Param("caseId") Integer caseId);

	List<CommentVo> queryCommentByTopId(@Param("topId") Integer topId);
}
