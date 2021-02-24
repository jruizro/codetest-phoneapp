package es.codetest.phoneapp.infrastructure.verticle.handler;

import com.google.gson.Gson;
import es.codetest.phoneapp.application.createcostumerorder.CostumerOrderRequestDTO;
import es.codetest.phoneapp.application.createcostumerorder.CostumerOrderResponseDTO;
import es.codetest.phoneapp.application.createcostumerorder.CreateCostumerOrderService;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.reactivex.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;


public class CostumerOrderRestHandler implements Handler<RoutingContext> {

  private static final Logger LOGGER = LoggerFactory.getLogger(CostumerOrderRestHandler.class);

  private final CreateCostumerOrderService createCostumerOrderService;

  public CostumerOrderRestHandler(CreateCostumerOrderService createCostumerOrderService) {
    this.createCostumerOrderService = createCostumerOrderService;
  }

  @Override
  public void handle(RoutingContext event) {

    LOGGER.info("Handler {} {}", event.currentRoute().methods().toString(), event.currentRoute().getPath());

    AtomicReference<CostumerOrderResponseDTO> orderCreated = new AtomicReference<>();

    try {

      event.request().bodyHandler(body -> {
        LOGGER.info("Request {}", body.toJsonObject().toString());
        try {
          orderCreated.set(createCostumerOrderService.execute(new Gson().fromJson(body.toString(), CostumerOrderRequestDTO.class)));
        } catch (Exception e) {
          event.response().setStatusCode(500).end();
        }
        event.response()
            .setChunked(true)
            .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
            .setStatusCode(201)
            .end(new Gson().toJson(orderCreated));
      });

    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      event.response().setStatusCode(500).end();
    }

  }

}
