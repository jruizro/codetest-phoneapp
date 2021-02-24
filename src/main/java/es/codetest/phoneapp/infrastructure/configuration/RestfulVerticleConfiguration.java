package es.codetest.phoneapp.infrastructure.configuration;

import es.codetest.phoneapp.application.createcostumerorder.CreateCostumerOrderService;
import es.codetest.phoneapp.application.getphonecatalog.GetPhoneCatalogService;
import es.codetest.phoneapp.infrastructure.verticle.RESTfulVerticle;
import es.codetest.phoneapp.infrastructure.verticle.VerticleDeployerListener;
import es.codetest.phoneapp.infrastructure.verticle.handler.CostumerOrderRestHandler;
import es.codetest.phoneapp.infrastructure.verticle.handler.PhoneCatalogRestHandler;
import io.vertx.reactivex.core.Vertx;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestfulVerticleConfiguration {

  @Bean
  Vertx vertx() {
    return Vertx.vertx();
  }

  @Bean
  RESTfulVerticle restfulVerticle(@Value("${server.port}") Integer httpPort,
                               PhoneCatalogRestHandler phoneCatalogRestHandler,
                               CostumerOrderRestHandler costumerOrderRestHandler) {
    return new RESTfulVerticle(httpPort, phoneCatalogRestHandler, costumerOrderRestHandler);
  }

  @Bean
  VerticleDeployerListener verticleDeployerListener(Vertx vertx, RESTfulVerticle restVerticle) {
    return new VerticleDeployerListener(vertx, restVerticle);
  }

  @Bean
  PhoneCatalogRestHandler phoneHandler(GetPhoneCatalogService getPhoneCatalogService) {
    return new PhoneCatalogRestHandler(getPhoneCatalogService);
  }

  @Bean
  CostumerOrderRestHandler costumerOrderHandler(CreateCostumerOrderService createCostumerOrderService) {
    return new CostumerOrderRestHandler(createCostumerOrderService);
  }

}
