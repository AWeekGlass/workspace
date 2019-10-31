package com.hengyu.system.service.impl;

import com.hengyu.system.entity.Watermark;
import com.hengyu.system.dao.WatermarkDAO;
import com.hengyu.system.service.WatermarkService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 水印表 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-10-09
 */
@Service
public class WatermarkServiceImpl extends ServiceImpl<WatermarkDAO, Watermark> implements WatermarkService {

	@Override
	public Watermark queryWatermark(Integer id) {
		Watermark watermark = selectOne(new EntityWrapper<Watermark>().eq("company_id", id));
		return watermark;
	}

}
