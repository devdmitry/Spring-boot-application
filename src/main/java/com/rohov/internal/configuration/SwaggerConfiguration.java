package com.rohov.internal.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket productApi() {
        List<ResponseMessage> defaultServerErrorResponse = createDefaultServerErrorResponses();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rohov.internal.controller"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.POST, defaultServerErrorResponse)
                .globalResponseMessage(RequestMethod.GET, defaultServerErrorResponse)
                .globalResponseMessage(RequestMethod.PUT, defaultServerErrorResponse)
                .globalResponseMessage(RequestMethod.PATCH, defaultServerErrorResponse)
                .globalResponseMessage(RequestMethod.DELETE, defaultServerErrorResponse)
                .globalResponseMessage(RequestMethod.HEAD, defaultServerErrorResponse)
                .globalResponseMessage(RequestMethod.OPTIONS, defaultServerErrorResponse)
                .globalResponseMessage(RequestMethod.PUT, defaultServerErrorResponse);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot Application")
                .description("This page lists all the rest apis")
                .version("1.0.0")
                .build();
    }

    private List<ResponseMessage> createDefaultServerErrorResponses() {
        ArrayList<ResponseMessage> defaultServerErrorResponses = new ArrayList<>();
        defaultServerErrorResponses.add(composeErrorResponse(500,
                "Failure. Unexpected condition was encountered."));
        defaultServerErrorResponses.add(composeErrorResponse(502,
                "Failure. The server, while acting as a gateway or proxy, received an invalid response " +
                           "from an inbound server it accessed while attempting to fulfill the request."));
        defaultServerErrorResponses.add(composeErrorResponse(401,
                "Failure. Indicates the access token is invalid."));
        return defaultServerErrorResponses;
    }

    private ResponseMessage composeErrorResponse(int code, String description) {
        return new ResponseMessageBuilder()
                .code(code)
                .message(description)
                .responseModel(new ModelRef("string"))
                .build();
    }
}
