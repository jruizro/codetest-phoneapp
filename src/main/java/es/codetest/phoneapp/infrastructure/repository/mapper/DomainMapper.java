package es.codetest.phoneapp.infrastructure.repository.mapper;

import io.vertx.reactivex.sqlclient.Row;

public interface DomainMapper<T> {

  T mapToDomain(Row row);

}
