package es.codetest.phoneapp.infrastructure.configuration;

import es.codetest.phoneapp.application.createcostumerorder.CreateCostumerOrderService;
import es.codetest.phoneapp.application.getphonecatalog.GetPhoneCatalogService;
import es.codetest.phoneapp.infrastructure.handler.CostumerOrderHandler;
import es.codetest.phoneapp.infrastructure.handler.PhoneHandler;
import es.codetest.phoneapp.infrastructure.verticle.RESTVerticle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VerticleHandlerConfiguration {

  @Bean
  PhoneHandler phoneHandler(GetPhoneCatalogService getPhoneCatalogService) {
    return new PhoneHandler(getPhoneCatalogService);
  }

  @Bean
  CostumerOrderHandler costumerOrderHandler(CreateCostumerOrderService createCostumerOrderService) {
    return new CostumerOrderHandler(createCostumerOrderService);
  }

  @Bean
  RESTVerticle restVerticle(@Value("${server.port}") Integer httpPort,
      PhoneHandler phoneHandler,
      CostumerOrderHandler costumerOrderHandler) {
    return new RESTVerticle(httpPort, phoneHandler, costumerOrderHandler);
  }

}
