package com.hengyu.cases.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.hengyu.cases.entity.Like;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
public interface LikeService extends IService<Like> {
	/**
	 * 查看案例点赞信息
	 */
	List<String> queryLikeInfoByCaseId(Integer caseId);

	void deleteByCaseId(Integer userId, Integer caseId);
}
