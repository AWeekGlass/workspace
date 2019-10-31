package com.hengyu.system.service.impl;

import com.hengyu.system.entity.Config;
import com.hengyu.system.dao.ConfigDAO;
import com.hengyu.system.service.ConfigService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 公用的端口路径表 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-08-22
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigDAO, Config> implements ConfigService {

}
