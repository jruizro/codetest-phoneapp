package es.codetest.phoneapp.infrastructure.controller;

import es.codetest.phoneapp.application.createcostumerorder.CostumerOrderRequestDTO;
import es.codetest.phoneapp.application.createcostumerorder.CostumerOrderResponseDTO;
import es.codetest.phoneapp.application.createcostumerorder.CreateCostumerOrderService;
import es.codetest.phoneapp.infrastructure.controller.apidoc.CostumerOrderAPIDocumentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("unchecked")
public class CostumerOrderController implements CostumerOrderAPIDocumentation {

  private static final Logger LOGGER = LoggerFactory.getLogger(CostumerOrderController.class);

  private final CreateCostumerOrderService createCostumerOrderService;

  public CostumerOrderController(CreateCostumerOrderService createCostumerOrderService) {
    this.createCostumerOrderService = createCostumerOrderService;
  }

  @Override
  @PostMapping(value = "/order",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity createCostumerOrder(@RequestBody CostumerOrderRequestDTO requestOrder) {

    LOGGER.info("POST Costumer Order: {}", requestOrder.toString());

    try {
      CostumerOrderResponseDTO costumerOrderResponse = createCostumerOrderService.execute(requestOrder);
      LOGGER.info("POST Costumer Order Creataed: {}", costumerOrderResponse.toString());
      return status(HttpStatus.OK).body(costumerOrderResponse);

    } catch (RuntimeException e) {
      LOGGER.error("POST Costumer Order fails -> Exception: {}", e.getMessage(), e);
      return status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

  }

}
