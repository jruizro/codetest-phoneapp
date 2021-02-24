package es.codetest.phoneapp.infrastructure.configuration;


import es.codetest.phoneapp.domain.repository.CatalogPhoneRepository;
import es.codetest.phoneapp.infrastructure.repository.PostgresCatalogPhoneRepository;
import es.codetest.phoneapp.infrastructure.repository.mapper.PostgresCatalogPhoneMapper;
import io.vertx.reactivex.sqlclient.Pool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryServiceConfiguration {

  @Bean
  public CatalogPhoneRepository phoneRepository(Pool jdbcPool) {
    return new PostgresCatalogPhoneRepository(jdbcPool, new PostgresCatalogPhoneMapper());
  }

}
