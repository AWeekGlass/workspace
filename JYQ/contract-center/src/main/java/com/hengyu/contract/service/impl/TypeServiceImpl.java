package com.hengyu.contract.service.impl;

import com.hengyu.contract.entity.Type;
import com.hengyu.contract.dao.TypeDAO;
import com.hengyu.contract.service.TypeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-08-31
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeDAO, Type> implements TypeService {

}
