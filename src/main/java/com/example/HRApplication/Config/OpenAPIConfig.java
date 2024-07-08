package com.example.HRApplication.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

import java.security.Security;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name="Hammami Mohamed Amine",
                        email="medaminehammami08@gmail.com"
                ),
                description = "OpenAPI documentation for HR administration backend",
                title = "OpenAPI specification"
        ),
        servers ={
                @Server(
                        description = "Local ENV",
                        url="http://localhost:8081"
                )
        },
        security = {
                @SecurityRequirement(
                        name="BearerAuth"
                )
        }
)
@SecurityScheme(
        name = "BearerAuth",
        description = "JWT Authentification desc",
        scheme = "bearer",
        type= SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in= SecuritySchemeIn.HEADER
)
public class OpenAPIConfig {
}

