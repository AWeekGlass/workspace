package com.hengyu.cases.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.cases.dao.LikeDAO;
import com.hengyu.cases.entity.Like;
import com.hengyu.cases.service.LikeService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
@Service
public class LikeServiceImpl extends ServiceImpl<LikeDAO, Like> implements LikeService {
	@Autowired
	LikeDAO likeDao;

	@Override
	public List<String> queryLikeInfoByCaseId(Integer caseId) {
		return likeDao.queryLikeInfoByCaseId(caseId);
	}

	@Override
	public void deleteByCaseId(Integer userId, Integer caseId) {
		likeDao.deleteByCaseId(userId,caseId);
	}
}
