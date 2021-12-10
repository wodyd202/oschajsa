package com.ljy.oschajsa.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private ApiInfo apiInfo(String title) {
        return new ApiInfoBuilder()
                .title(title)
                .description("oschajsa API DOCS")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .termsOfServiceUrl("")
                .version("1.0.0")
                .build();
    }

    @Bean
    public Docket api() {
        ParameterBuilder headerParam = new ParameterBuilder();
        headerParam.name("X-AUTH-TOKEN") //헤더 이름
                .description("Access Tocken") //설명
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();

        List<Parameter> headerList = new ArrayList<>();
        headerList.add(headerParam.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(headerList)
                .groupName("oschajsa")
                .apiInfo(this.apiInfo("oschajsa API"))
                .select()
                .apis(
                        Predicates.or(
                                RequestHandlerSelectors.basePackage("com.ljy.oschajsa.config.security"),
                                RequestHandlerSelectors.basePackage("com.ljy.oschajsa.services.user.command.presentation"),
                                RequestHandlerSelectors.basePackage("com.ljy.oschajsa.services.user.query.presentation"),
                                RequestHandlerSelectors.basePackage("com.ljy.oschajsa.services.store.command.presentation"),
                                RequestHandlerSelectors.basePackage("com.ljy.oschajsa.services.store.query.presentation"),
                                RequestHandlerSelectors.basePackage("com.ljy.oschajsa.services.interest.presentation")
                        )
                )
                .paths(PathSelectors.ant("/**"))
                .build();
    }
}