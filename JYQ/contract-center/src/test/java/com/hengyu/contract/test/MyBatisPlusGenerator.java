package com.hengyu.contract.test;

import java.io.File;

import org.junit.Test;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class MyBatisPlusGenerator {
	@Test
	public void generator() {
		File file = new File("");
		String path = file.getAbsolutePath();

		AutoGenerator mpg = new AutoGenerator();
		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		gc.setOutputDir(path + "/src/main/java");// 需要生成的目录
		gc.setFileOverride(false);// 是否覆盖
		gc.setActiveRecord(true);
		gc.setEnableCache(false);// XML 二级缓存
		gc.setBaseResultMap(false);// XML ResultMap
		gc.setBaseColumnList(false);// XML columList
		gc.setMapperName("%sDAO");
		gc.setServiceName("%sService");
		gc.setAuthor("allnas");
		mpg.setGlobalConfig(gc);

		// 数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setDbType(DbType.MYSQL);
		dsc.setDriverName("org.gjt.mm.mysql.Driver");
		dsc.setUsername("root");
		dsc.setPassword("SAok123");
		dsc.setUrl("jdbc:mysql://47.97.185.156:3306/jyq?useSSL=false&characterEncoding=utf8");
		mpg.setDataSource(dsc);

		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
		strategy.setTablePrefix(new String[] { "jyq_" });
		strategy.setInclude("jyq_approval","jyq_approval_file","jyq_approval_record");
		strategy.setEntityLombokModel(true);
		mpg.setStrategy(strategy);

		// 包配置
		PackageConfig pc = new PackageConfig();
		pc.setParent(null);
		pc.setEntity("com.hengyu.contract.entity");
		pc.setMapper("com.hengyu.contract.dao");
		pc.setXml("mappings");
		pc.setController("com.hengyu.contract.web");
		pc.setService("com.hengyu.contract.service");
		pc.setServiceImpl("com.hengyu.contract.service.impl");

		mpg.setPackageInfo(pc);

		// 执行生成
		mpg.execute();
	}
}
