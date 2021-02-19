package es.codetest.phoneapp.infrastructure.verticle;

import es.codetest.phoneapp.infrastructure.handler.CostumerOrderHandler;
import es.codetest.phoneapp.infrastructure.handler.PhoneHandler;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(VertxExtension.class)
public class RESTVerticleShould {

  private static final Integer HTTP_PORT = 15000;

  @Mock
  private PhoneHandler phoneHandler;
  @Mock
  private CostumerOrderHandler costumerOrderHandler;

  @BeforeEach
  void deployVerticle(Vertx vertx, VertxTestContext testContext) {
    vertx.deployVerticle(new RESTVerticle(HTTP_PORT, phoneHandler, costumerOrderHandler),
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
