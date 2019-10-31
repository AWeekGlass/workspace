package com.hengyu.exam.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.exam.dao.CategoryDAO;
import com.hengyu.exam.entity.Category;
import com.hengyu.exam.service.CategoryService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-10-17
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDAO, Category> implements CategoryService {


}
