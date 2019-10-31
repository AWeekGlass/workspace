package com.hengyu.system.dao;

import com.hengyu.system.entity.Dictionary;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-09-25
 */
public interface DictionaryDAO extends BaseMapper<Dictionary> {
	List<Integer> querySubject();
}
