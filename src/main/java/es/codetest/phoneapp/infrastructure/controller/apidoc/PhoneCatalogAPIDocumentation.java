package es.codetest.phoneapp.infrastructure.controller.apidoc;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

@Api("Phone Catalog API v1")
public interface PhoneCatalogAPIDocumentation {

    @ApiOperation(
        value = "Get Phone Catalog",
        notes = "Endpoint for get phone's catalog information",
        httpMethod = "GET")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully return phone's catalog"),
        @ApiResponse(code = 400, message = "Bad Request -> invalid input data"),
        @ApiResponse(code = 500, message = "Internal failure")
    })
    ResponseEntity getPhoneCatalog();

}
