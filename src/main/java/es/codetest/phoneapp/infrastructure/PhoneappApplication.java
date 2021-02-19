package es.codetest.phoneapp.infrastructure;

import es.codetest.phoneapp.infrastructure.verticle.RESTVerticle;
import io.vertx.core.Vertx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class PhoneappApplication {

  private static final Logger LOGGER = LoggerFactory.getLogger(PhoneappApplication.class);

  @Autowired
  private RESTVerticle RESTVerticle;

  public static void main(String[] args) {

    try {
      SpringApplication.run(PhoneappApplication.class, args);
    } catch (Exception e) {
      LOGGER.error("Spring Application launch failed. Terminating...");
      System.exit(-1);
    }
  }

  @PostConstruct
  public void deployVerticles() {
    final Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(RESTVerticle, asyncResult -> {
      if (asyncResult.failed()) {
        LOGGER.error("Phoneapp REST Verticles deploy failed. Terminating...");
        System.exit(-1);
      }
    });
  }

}
