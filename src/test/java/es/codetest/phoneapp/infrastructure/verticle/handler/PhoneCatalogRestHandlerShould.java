package es.codetest.phoneapp.infrastructure.verticle.handler;

import com.google.common.collect.Lists;
import es.codetest.phoneapp.application.getphonecatalog.GetPhoneCatalogService;
import es.codetest.phoneapp.application.getphonecatalog.PhoneCatalogInfoDTO;
import es.codetest.phoneapp.infrastructure.verticle.RESTfulVerticle;
import io.reactivex.Single;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.junit5.Timeout;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(VertxExtension.class)
public class PhoneCatalogRestHandlerShould {

  private static final Integer HTTP_PORT = 15000;
  private static final String PHONES_PATH = "/v1/phones";

  private PhoneCatalogRestHandler phoneCatalogRestHandler;

  private GetPhoneCatalogService getPhoneCatalogServiceMock = mock(GetPhoneCatalogService.class);
  private CostumerOrderRestHandler costumerOrderRestHandlerMock = mock(CostumerOrderRestHandler.class);

  @BeforeEach
  void deployVerticle(Vertx vertx, VertxTestContext testContext) {
    phoneCatalogRestHandler = new PhoneCatalogRestHandler(getPhoneCatalogServiceMock);
    vertx.deployVerticle(new RESTfulVerticle(HTTP_PORT, phoneCatalogRestHandler, costumerOrderRestHandlerMock),
        testContext.succeeding(ar -> testContext.completeNow()));
  }

  @Test
  @Timeout(value = 5, timeUnit = TimeUnit.SECONDS)
  void return200WhenHandleRequestSuccessfully(Vertx vertx, VertxTestContext testContext) {
    // given
    List<PhoneCatalogInfoDTO> catalog = Lists.newArrayList(
        new PhoneCatalogInfoDTO("Nokia", "3030", "http:://image.jpg", 100),
        new PhoneCatalogInfoDTO("iPhone", "Version 12", "http:://image.png", 200)
    );
    when(getPhoneCatalogServiceMock.execute())
        .thenReturn(Single.just(catalog));

    // when - then
    WebClient.create(vertx).get(HTTP_PORT, "localhost", PHONES_PATH)
        .as(BodyCodec.string())
        .send(testContext.succeeding(response -> {
          testContext.verify(() -> {
            assertEquals(response.statusCode(), 200);
            assertTrue(response.body().contains("Nokia"));
            assertTrue(response.body().contains("iPhone"));
            testContext.completeNow();
          });
        }));
  }

  @Test
  @Timeout(value = 5, timeUnit = TimeUnit.SECONDS)
  void return500WhenHandleRequestFails(Vertx vertx, VertxTestContext testContext) {
    // given
    when(getPhoneCatalogServiceMock.execute())
        .thenThrow(new RuntimeException("Something Fails"));

    // when - then
    WebClient.create(vertx).get(HTTP_PORT, "localhost", PHONES_PATH)
        .as(BodyCodec.string())
        .send(testContext.succeeding(response -> {
          testContext.verify(() -> {
            assertEquals(response.statusCode(), 500);
            testContext.completeNow();
          });
        }));
  }

}
