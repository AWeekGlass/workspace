package com.hengyu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author zhaojin
 *
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {
	@Bean
	public Docket restApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).enable(true).select()
				.apis(RequestHandlerSelectors.basePackage("com.hengyu.exam.web")).paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("捷易签 api文档").description("捷易签API接口")
				.termsOfServiceUrl("https://jieyiqian.com").version("1.0").build();
	}
}
