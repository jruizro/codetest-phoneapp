package es.codetest.phoneapp.infrastructure.configuration;

import io.vertx.pgclient.PgConnectOptions;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.pgclient.PgPool;
import io.vertx.reactivex.sqlclient.Pool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.SqlConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class DataBaseConnectionConfiguration {

  @Bean
  SqlConnectOptions jdbcPGConnectOptions(@Value("${spring.datasource.url}") String databaseUrl,
                                         @Value("${spring.datasource.username}") String databaseUser,
                                         @Value("${spring.datasource.password}") String databasePass) {
    return new PgConnectOptions()
        .setDatabase(databaseUrl.substring(databaseUrl.lastIndexOf('/') + 1, databaseUrl.lastIndexOf('?')))
        .setUser(databaseUser)
        .setPassword(databasePass)
        .setProperties(Map.of("search_path", databaseUrl.substring(databaseUrl.lastIndexOf('=') + 1)));
  }

  @Bean
  PoolOptions poolOptions(@Value("${spring.datasource.hikari.maximum-pool-size}") Integer maxSize) {
    return new PoolOptions().setMaxSize(maxSize);
  }

  @Bean
  Pool jdbcPGPool(Vertx vertx, SqlConnectOptions jdbcPGConnectOptions,
                  PoolOptions poolOptions
  ) {
    return PgPool.pool(vertx, jdbcPGConnectOptions, poolOptions);
  }


}
