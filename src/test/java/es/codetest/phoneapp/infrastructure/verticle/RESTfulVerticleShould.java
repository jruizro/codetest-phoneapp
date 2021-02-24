package es.codetest.phoneapp.infrastructure.verticle;

import es.codetest.phoneapp.infrastructure.verticle.handler.CostumerOrderRestHandler;
import es.codetest.phoneapp.infrastructure.verticle.handler.PhoneCatalogRestHandler;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(VertxExtension.class)
public class RESTfulVerticleShould {

  private static final Integer HTTP_PORT = 15000;

  @Mock
  private PhoneCatalogRestHandler phoneCatalogRestHandler;
  @Mock
  private CostumerOrderRestHandler costumerOrderRestHandler;

  @BeforeEach
  void deployVerticle(Vertx vertx, VertxTestContext testContext) {
    vertx.deployVerticle(new RESTfulVerticle(HTTP_PORT, phoneCatalogRestHandler, costumerOrderRestHandler),
        testContext.succeeding(ar -> testContext.completeNow()));
  }

  @Test
  void deployVerticleSuccessfully(Vertx vertx, VertxTestContext testContext) {
    // given

    // then
    assertThat(vertx.deploymentIDs()).isNotEmpty().hasSize(1);
    // end
    testContext.completeNow();
  }

}
