package es.codetest.phoneapp.infrastructure.verticle;

import es.codetest.phoneapp.infrastructure.PhoneappApplication;
import es.codetest.phoneapp.infrastructure.verticle.handler.CostumerOrderRestHandler;
import es.codetest.phoneapp.infrastructure.verticle.handler.PhoneCatalogRestHandler;
import io.reactivex.Completable;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.LoggerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTfulVerticle extends AbstractVerticle {

  private static final Logger LOGGER = LoggerFactory.getLogger(PhoneappApplication.class);

  private static final String PHONES_PATH = "/v1/phones";
  private static final String COSTUMER_ORDER_PATH = "/v1/order";

  private Integer httpPort;

  private PhoneCatalogRestHandler phoneCatalogRestHandler;
  private CostumerOrderRestHandler costumerOrderRestHandler;

  public RESTfulVerticle(Integer httpPort, PhoneCatalogRestHandler phoneCatalogRestHandler, CostumerOrderRestHandler costumerOrderRestHandler) {
    this.httpPort = httpPort;
    this.phoneCatalogRestHandler = phoneCatalogRestHandler;
    this.costumerOrderRestHandler = costumerOrderRestHandler;
  }

  @Override
  public Completable rxStart() {

    LOGGER.info("Starting RESTful Verticles deploy...");

    HttpServerOptions serverOptions = new HttpServerOptions()
        .setPort(httpPort);

    Router router = Router.router(vertx);
    router.route().handler(LoggerHandler.create());

    router.route(HttpMethod.GET, PHONES_PATH).handler(phoneCatalogRestHandler);
    LOGGER.info("Deployed Verticle: {} {}", HttpMethod.GET, PHONES_PATH);
    router.route(HttpMethod.POST, COSTUMER_ORDER_PATH).handler(costumerOrderRestHandler);
    LOGGER.info("Deployed Verticle: {} {}", HttpMethod.POST, COSTUMER_ORDER_PATH);

    return Completable.fromSingle(vertx.createHttpServer(serverOptions)
        .requestHandler(router)
        .rxListen()
        .doOnSuccess(server -> {
          LOGGER.info("Vertx RESTful Server launched on {}", serverOptions.getPort());
        })
        .doOnError(throwable -> {
          LOGGER.error("Launch Vertx RESTful Server FAILED cause {}", throwable.getMessage());
        })
    );
  }


}
