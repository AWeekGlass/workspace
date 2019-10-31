package com.hengyu.user.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hengyu.common.util.RedisUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
	@Autowired
	private RedisUtils<String> redisUtils;
	
	@Test
	public void add() {
		redisUtils.setValue("CONTRACT:TEMPLATE:397", "gerngenrg");
	}
}
