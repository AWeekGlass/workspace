package com.hengyu.cases.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.cases.dao.CommentDAO;
import com.hengyu.cases.entity.Comment;
import com.hengyu.cases.service.CommentService;
import com.hengyu.cases.vo.CommentVo;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
@Slf4j
@Service
public class CommentServiceImpl extends ServiceImpl<CommentDAO, Comment> implements CommentService {
	@Autowired
	private CommentDAO commentDao;

	@Override
	public List<CommentVo> queryCommentByCaseId(Integer caseId) {
		List<CommentVo> commentVoList = commentDao.queryCommentByCaseId(caseId);

		commentVoList.forEach(commentVo -> commentVo.setChilds(commentDao.queryCommentByTopId(commentVo.getId())));
		log.info("commentVoList:{}", commentVoList);

		return commentVoList;
	}
}
