package com.jero.core.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
 * @Description swagger增强器knife4j的注解
 * @Author lixuetao
 * @Date 2020/4/23
 **/
@Configuration
@EnableSwagger2
@EnableKnife4j
@ConditionalOnProperty(value = {"knife4j.enable"}, matchIfMissing = true)
public class Knife4jConfig {

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jero"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Jero RESTful APIs")
                .description("# Jero RESTful APIs")
                .termsOfServiceUrl("https://github.com/Jero-framework")
                .version("1.0")
                .build();
    }
}
