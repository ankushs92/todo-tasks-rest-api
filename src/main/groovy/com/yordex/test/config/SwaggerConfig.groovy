package com.yordex.test.config

import com.yordex.test.domain.auth.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.domain.Pageable
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import springfox.documentation.builders.ParameterBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.schema.ModelRef
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.BasicAuth
import springfox.documentation.service.Contact
import springfox.documentation.service.SecurityScheme
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2



import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Created by Ankush on 10/05/17.
 */
@Configuration
@EnableSwagger2
// Springfox was throwing error when running tests. This annotation disables it for tests altogether
@Profile("default")
class SwaggerConfig {

    @Value("\$version")
    String version

    @Bean
    Docket swagger() {
        new Docket(DocumentationType.SWAGGER_2)
                  .select()
                  .apis(RequestHandlerSelectors.any())
                  .paths(regex("/api.*"))
                  .paths(PathSelectors.any())

                  .build()
                  .apiInfo(apiInfo())
                  .securitySchemes([new BasicAuth("basic")])

                  .ignoredParameterTypes(User,Pageable)
                  .useDefaultResponseMessages(false)
                  .globalOperationParameters(
                        [new ParameterBuilder()
                        .name("Authorization")
                        .required(true)
                                 .description("Description of header")
                                 .modelRef(new ModelRef("string"))
                        .parameterType("header")
                        .build()]
            )
    }

    private ApiInfo apiInfo() {
        def apiInfo = new ApiInfo(
                "Yordex Todo List Rest API",
                "Documentation for Yordex Todo List app",
                version,
                "Terms of service",
                new Contact("Ankush Sharma", "http://ankushs92.github.io/", "ankush20058@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0");
        apiInfo
    }


}
