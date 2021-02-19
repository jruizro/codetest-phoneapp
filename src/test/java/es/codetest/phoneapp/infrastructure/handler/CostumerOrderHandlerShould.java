package es.codetest.phoneapp.infrastructure.handler;

import es.codetest.phoneapp.application.createcostumerorder.CostumerOrderRequestDTO;
import es.codetest.phoneapp.application.createcostumerorder.CostumerOrderResponseDTO;
import es.codetest.phoneapp.application.createcostumerorder.CreateCostumerOrderService;
import es.codetest.phoneapp.infrastructure.verticle.RESTVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.junit5.Timeout;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.TimeUnit;

import static io.netty.handler.codec.http.HttpHeaderValues.APPLICATION_JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(VertxExtension.class)
public class CostumerOrderHandlerShould {

  private static final Integer HTTP_PORT = 15000;
  private static final String COSTUMER_ORDER_PATH = "/v1/order";

  private CostumerOrderHandler costumerOrderHandler;

  private PhoneHandler phoneHandlerMock = mock(PhoneHandler.class);
  private CreateCostumerOrderService createCostumerOrderServiceMock = mock(CreateCostumerOrderService.class);

  @BeforeEach
  void deployVerticle(Vertx vertx, VertxTestContext testContext) {
    costumerOrderHandler = new CostumerOrderHandler(createCostumerOrderServiceMock);
    vertx.deployVerticle(new RESTVerticle(HTTP_PORT, phoneHandlerMock, costumerOrderHandler),
        testContext.succeeding(ar -> testContext.completeNow()));
  }

  @Test
  @Timeout(value = 5, timeUnit = TimeUnit.SECONDS)
  void return201WhenHandleRequestSuccessfully(Vertx vertx, VertxTestContext testContext) {

    // given
    CostumerOrderRequestDTO requestOrder = new CostumerOrderRequestDTO(
        "User", "Surname", "email@email.es",
        Lists.newArrayList("Nokia", "iPhone"));
    JsonObject jsonBodyRequest = JsonObject.mapFrom(requestOrder);

    CostumerOrderResponseDTO costumerOrder = new CostumerOrderResponseDTO(
        requestOrder.getName(),
        requestOrder.getSurname(),
        requestOrder.getEmail(),
        requestOrder.getPhonesNames(),
        300
    );
    when(createCostumerOrderServiceMock.execute(any(CostumerOrderRequestDTO.class)))
        .thenReturn(costumerOrder);

    // when - then
    WebClient.create(vertx).post(HTTP_PORT, "localhost", COSTUMER_ORDER_PATH)
        .putHeader(HttpHeaders.CONTENT_TYPE.toString(), APPLICATION_JSON.toString())
        .sendJson(jsonBodyRequest, response -> {
          testContext.verify(() -> {
            assertEquals(response.result().statusCode(), 201);
            assertTrue(response.result().body().toString().contains("Nokia"));
            assertTrue(response.result().body().toString().contains("iPhone"));
            assertTrue(response.result().body().toString().contains("300"));
            testContext.completeNow();
          });
        });
  }

  @Test
  @Timeout(value = 5, timeUnit = TimeUnit.SECONDS)
  void return500WhenHandleRequestFails(Vertx vertx, VertxTestContext testContext) {
    // given
    CostumerOrderRequestDTO requestOrder = new CostumerOrderRequestDTO(
        "User", "Surname", "email@email.es",
        Lists.newArrayList("Nokia", "iPhone"));
    JsonObject jsonBodyRequest = JsonObject.mapFrom(requestOrder);
    when(createCostumerOrderServiceMock.execute(any(CostumerOrderRequestDTO.class)))
        .thenThrow(new RuntimeException("Something Fails"));

    // when - then
    WebClient.create(vertx).post(HTTP_PORT, "localhost", COSTUMER_ORDER_PATH)
        .putHeader(HttpHeaders.CONTENT_TYPE.toString(), APPLICATION_JSON.toString())
        .sendJson(jsonBodyRequest, response -> {
          testContext.verify(() -> {
            assertEquals(response.result().statusCode(), 500);
            testContext.completeNow();
          });
        });
  }

}
