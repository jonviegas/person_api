package com.jonservices.personapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(info =
@Info(title = "Person API",
        version = "v1",
        description = "This REST API provides a small system " +
                "for managing people, offering the functionality " +
                "to register a person by their first name, last name, " +
                "CPF (Brazillian individual registry identification), " +
                "date of birth, and personal phone numbers. In addition, " +
                "it is also possible to query a record or list all records " +
                "according to a sorting criterion."))
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Person API")
                        .version("V1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));

    }

}
