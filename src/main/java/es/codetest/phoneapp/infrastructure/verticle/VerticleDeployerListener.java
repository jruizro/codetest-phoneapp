package es.codetest.phoneapp.infrastructure.verticle;

import io.vertx.reactivex.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

public class VerticleDeployerListener implements ApplicationListener<ApplicationReadyEvent> {

  private static final Logger LOGGER = LoggerFactory.getLogger(VerticleDeployerListener.class);

  private final Vertx vertx;

  private final RESTfulVerticle restfulVerticle;

  public VerticleDeployerListener(Vertx vertx, RESTfulVerticle restfulVerticle) {
    this.vertx = vertx;
    this.restfulVerticle = restfulVerticle;
  }

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {

    vertx.deployVerticle(restfulVerticle, handler -> {
      LOGGER.info("Vertx Verticles deploy state [{}]", handler.succeeded());
      if (handler.failed()) {
        LOGGER.error("Vertx Verticles deploy failed message: [{}]", handler.result());
      }
    });

  }

}

