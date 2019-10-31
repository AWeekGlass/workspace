package com.hengyu.training.dao;

import com.hengyu.training.entity.TrainComment;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 培训心得回复表 Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-12-14
 */
public interface TrainCommentDAO extends BaseMapper<TrainComment> {
	
	/**
	 * 查询评论父节点
	 * @param companyId
	 * @param id
	 * @return
	 */
	List<TrainComment> getTree(@Param("companyId")Integer companyId,@Param("id")Integer id,@Param("parentId")Integer parentId);
	
	/**
	 * 查询所有子回复
	 * @param companyId
	 * @param expId
	 * @return
	 */
	List<TrainComment> getChouBiList(@Param("companyId")Integer companyId,@Param("id")Integer expId);
}
