package es.codetest.phoneapp.infrastructure.controller.apidoc;

import es.codetest.phoneapp.application.createcostumerorder.CostumerOrderRequestDTO;
import es.codetest.phoneapp.application.createcostumerorder.CostumerOrderResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

@Api("Phone Catalog API Costumer Order v1")
public interface CostumerOrderAPIDocumentation {

  @ApiOperation(
      value = "Create Costumer Phone Order",
      notes = "Endpoint for create a costumer order",
      httpMethod = "POST",
      response = CostumerOrderResponseDTO.class)
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Successfully created cosumer order"),
      @ApiResponse(code = 400, message = "Bad Request -> invalid input data)"),
      @ApiResponse(code = 500, message = "Internal failure")})
  ResponseEntity createCostumerOrder(
      @ApiParam("costumerOrderRequestDTO") CostumerOrderRequestDTO requestOrder
  );

}
