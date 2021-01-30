package es.codetest.phoneapp.infrastructure.configuration;


import es.codetest.phoneapp.domain.repository.CatalogPhoneRepository;
import es.codetest.phoneapp.infrastructure.repository.RandomCatalogPhoneRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainServiceConfiguration {

  @Bean
  public CatalogPhoneRepository phoneRepository() {
    return new RandomCatalogPhoneRepositoryImpl();
  }

}
