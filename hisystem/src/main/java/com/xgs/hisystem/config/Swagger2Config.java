/**
 * hxgy Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.xgs.hisystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 *  swagger2配置类
 */

/**
 * 1.@Configuration配置spring并启动spring容器
 * 1.2、@Configuration启动容器+@Bean注册Bean
 * 1.3、@Configuration启动容器+@Component注册Bean
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
	@Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).globalOperationParameters(new ArrayList<Parameter>() {
            private static final long serialVersionUID = 1L;
            {
                add(new ParameterBuilder().name("token")
                        .description("请求可能需要在HTTP header中加入token。\r\n请填写\"Bearer {token}\"").modelRef(new ModelRef("string"))
                        .parameterType("header").required(false).build());
            }
        }).select()
                .apis(RequestHandlerSelectors.basePackage("com.xgs.hisystem.controller")).paths(PathSelectors.any())
                .build();
    }
	
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Hisystem APIs").description("如需通过swagger测试，请先调/user/dologin接口登录")
                .termsOfServiceUrl("https://gitee.com/sensay/hisystem").contact(new Contact("sensay", "https://gitee.com/sensay/hisystem", "")).version("1.0").build();
    }
}
