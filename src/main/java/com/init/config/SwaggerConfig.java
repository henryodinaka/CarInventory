package com.init.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
//@Profile({"dev","test"})
@EnableSwagger2
public class SwaggerConfig {

//    public static final String CLIENT_CODE = Constants.CLIENT_CODE;
    private static final String AUTHORIZATION = EncryptionHeader.AUTHORIZATION.getName();
    private static final String CODE_CODE = EncryptionHeader.CHANNEL_CODE.getName();
//    public static final String SIGNATURE_METH = EncryptionHeader.SIGNATURE_METH.getName();

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.init.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Arrays.asList(channelCode(), apiKeyAuthorization()/*, apiKeySignature(), apiKeySignatureMeth()*/))
                /*.securitySchemes(Arrays.asList(apiKeyAuthorization()))*/
                .securityContexts(Arrays.asList(securityContext()));
    }

    private ApiInfo getApiInfo(){
        return new ApiInfoBuilder()
                .title("Car Inventory Services API")
                .description("This is a micro service for car inventory ")
                .version("2.0")
                .build();
    }

//    private ApiKey apiKeyClientCode() {
//        return new ApiKey(CLIENT_CODE, CLIENT_CODE, "header");
//    }

    private ApiKey apiKeyAuthorization() {
        return new ApiKey(AUTHORIZATION, AUTHORIZATION, "header");
    }

    private ApiKey channelCode() {
        return new ApiKey(CODE_CODE, CODE_CODE, "header");
    }

//    private ApiKey apiKeySignatureMeth() {
//        return new ApiKey(SIGNATURE_METH, SIGNATURE_METH, "header");
//    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
//                .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
                new SecurityReference(CODE_CODE, authorizationScopes),
                new SecurityReference(AUTHORIZATION, authorizationScopes));
//                new SecurityReference(SIGNATURE, authorizationScopes),
//                new SecurityReference(SIGNATURE_METH, authorizationScopes));
    }

}