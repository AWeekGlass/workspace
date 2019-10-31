package com.hengyu.cases.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hengyu.cases.entity.Like;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-08-28
 */
public interface LikeDAO extends BaseMapper<Like> {
	/**
	 * 查看案例点赞信息
	 */
	List<String> queryLikeInfoByCaseId(@Param("caseId") Integer caseId);

	void deleteByCaseId(@Param("userId") Integer userId,@Param("caseId")  Integer caseId);
}
