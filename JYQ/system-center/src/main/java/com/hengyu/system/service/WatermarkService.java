package com.hengyu.system.service;

import com.hengyu.system.entity.Watermark;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 水印表 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-10-09
 */
public interface WatermarkService extends IService<Watermark> {
	
	/**
	 * 根据公司id查询公司水印
	 * @param id
	 * @return
	 */
	Watermark queryWatermark(Integer id);

}
