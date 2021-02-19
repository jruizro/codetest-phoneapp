package es.codetest.phoneapp.infrastructure.handler;

import com.google.gson.Gson;
import es.codetest.phoneapp.application.getphonecatalog.GetPhoneCatalogService;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PhoneHandler implements Handler<RoutingContext> {

  private static final Logger LOGGER = LoggerFactory.getLogger(PhoneHandler.class);

  private final GetPhoneCatalogService getPhoneCatalogService;

  public PhoneHandler(GetPhoneCatalogService getPhoneCatalogService) {
    this.getPhoneCatalogService = getPhoneCatalogService;
  }

  @Override
  public void handle(RoutingContext event) {

    LOGGER.info("Handler {} {}", event.currentRoute().methods().toString(), event.currentRoute().getPath());

    try {

      String result = new Gson().toJson(getPhoneCatalogService.execute());

      LOGGER.info(result);

      event.response()
          .setChunked(true)
          .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
          .setStatusCode(200)
          .write(result)
          .end();

    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      event.response().setStatusCode(500).end();
    }

  }

}
