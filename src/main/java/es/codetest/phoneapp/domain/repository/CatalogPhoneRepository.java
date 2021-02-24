package es.codetest.phoneapp.domain.repository;

import es.codetest.phoneapp.domain.model.CatalogPhone;
import io.reactivex.Single;

import java.util.List;

public interface CatalogPhoneRepository {

  Single<List<CatalogPhone>> getPhoneCatalog();

}
