package com.tradespace.tradebid.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
//@EnableSwagger2
public class SpringFoxConfig {
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//          .select()
//          .apis(RequestHandlerSelectors.any())
//          .paths(PathSelectors.any())
//          .build();
//    }

    private ApiInfo apiInfo() {
        return new ApiInfo("Tradebid Rest APIs",
                "APIs for Tradebid.",
                "1.0",
                "Terms of service",
                new Contact("test", "www.org.com", "test@emaildomain.com"),
                "License of API",
                "API license URL",
                Collections.emptyList());
    }

//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.OAS_30)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build();
//    }

//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build()
//                .pathMapping("/");
//    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("public-api")
                .apiInfo(apiInfo()).select().paths(PathSelectors.any()).build();
    }

//    private Predicate<String> postPaths() {
//        return or(regex("/api/posts.*"), regex("/api/javainuse.*"));
//    }
}