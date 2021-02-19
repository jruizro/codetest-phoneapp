package es.codetest.phoneapp.infrastructure.verticle;

import es.codetest.phoneapp.infrastructure.PhoneappApplication;
import es.codetest.phoneapp.infrastructure.handler.CostumerOrderHandler;
import es.codetest.phoneapp.infrastructure.handler.PhoneHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.LoggerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTVerticle extends AbstractVerticle {

  private static final Logger LOGGER = LoggerFactory.getLogger(PhoneappApplication.class);

  private static final String PHONES_PATH = "/v1/phones";
  private static final String COSTUMER_ORDER_PATH = "/v1/order";

  private Integer httpPort;

  private PhoneHandler phoneHandler;
  private CostumerOrderHandler costumerOrderHandler;

  public RESTVerticle(Integer httpPort, PhoneHandler phoneHandler, CostumerOrderHandler costumerOrderHandler) {
    this.httpPort = httpPort;
    this.phoneHandler = phoneHandler;
    this.costumerOrderHandler = costumerOrderHandler;
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    LOGGER.info("Starting Verticles deploy...");

    HttpServerOptions serverOptions = new HttpServerOptions()
        .setPort(httpPort);

    Router router = Router.router(vertx);
    router.route().handler(LoggerHandler.create());

    router.route(HttpMethod.GET, PHONES_PATH).handler(phoneHandler);
    LOGGER.info("Deployed Verticle: {} {}", HttpMethod.GET, PHONES_PATH);
    router.route(HttpMethod.POST, COSTUMER_ORDER_PATH).handler(costumerOrderHandler);
    LOGGER.info("Deployed Verticle: {} {}", HttpMethod.POST, COSTUMER_ORDER_PATH);

    vertx.createHttpServer(serverOptions).requestHandler(router).listen(asyncResult -> {
      if (asyncResult.succeeded()) {
        startPromise.complete();
        LOGGER.info("Vertx Web-Server launched on {}", serverOptions.getPort());
      } else {
        LOGGER.error("Launch Vertx Web-Server FAILED cause {}", asyncResult.cause().toString());
        startPromise.fail(asyncResult.cause());
      }
    });
  }


}
