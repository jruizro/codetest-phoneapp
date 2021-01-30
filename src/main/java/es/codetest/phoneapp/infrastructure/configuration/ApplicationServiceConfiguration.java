package es.codetest.phoneapp.infrastructure.configuration;


import es.codetest.phoneapp.application.createcostumerorder.CreateCostumerOrderService;
import es.codetest.phoneapp.application.getphonecatalog.GetPhoneCatalogService;
import es.codetest.phoneapp.domain.repository.CatalogPhoneRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationServiceConfiguration {

  @Bean
  public GetPhoneCatalogService getPhoneCatalogService(final CatalogPhoneRepository catalogPhoneRepository) {
    return new GetPhoneCatalogService(catalogPhoneRepository);
  }

  @Bean
  public CreateCostumerOrderService createCostumerOrderService(final CatalogPhoneRepository catalogPhoneRepository) {
    return new CreateCostumerOrderService(catalogPhoneRepository);
  }

}
