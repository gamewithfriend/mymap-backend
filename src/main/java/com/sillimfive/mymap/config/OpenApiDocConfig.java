package com.sillimfive.mymap.config;

import com.sillimfive.mymap.common.JSONBuilder;
import com.sillimfive.mymap.web.dto.Error;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "MyMap REST API Document",
        version = "${mymap.api.version}",
        contact = @Contact(
            name = "Sillim Five", url = "https://github.com/sillimfive"
        ),
        description = "description"
    )
)
@SecurityScheme(
    name = "Bearer Authentication",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "Bearer"
)
public class OpenApiDocConfig {

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
                ApiResponses responses = operation.getResponses();
//                ApiResponse default200 = new ApiResponse()
//                        .description("OK")
//                        .content(new Content().addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
//                                new MediaType().example(
//                                        JSONBuilder.create()
//                                                .put("error", null)
//                                                .put("data", new HashMap<>())
//                                                .put("result", "SUCCESS")
//                                                .build())));

                ApiResponse default500 = new ApiResponse()
                        .description("Internal Server Error")
                        .content(new Content().addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                                new MediaType().example(
                                        JSONBuilder.create()
                                            .put("error", new Error(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR"))
                                            .put("data", new HashMap<>())
                                            .put("result", "ERROR")
                                            .build())));

//                responses.addApiResponse("200", default200);
                responses.addApiResponse("500", default500);
            }));

            // schema
            Schema idSchema = new ObjectSchema()
                    .name("id-schema")
                    .title("id-schema")
                    .description("MyMapResponse for ID response")
                    .addProperty("error", new ObjectSchema().example(null))
                    .addProperty("data", new NumberSchema().example(1L))
                    .addProperty("result", new StringSchema().example("SUCCESS"));

            openApi.getComponents().getSchemas()
                    .put(idSchema.getName(), idSchema);
        };
    }
}
