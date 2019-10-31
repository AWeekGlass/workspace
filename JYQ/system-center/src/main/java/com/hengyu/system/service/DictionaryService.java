package com.hengyu.system.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.hengyu.system.entity.Dictionary;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-09-25
 */
public interface DictionaryService extends IService<Dictionary> {
	List<Integer> querySubject();
}
