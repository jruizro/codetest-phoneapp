package es.codetest.phoneapp.domain.repository;

import es.codetest.phoneapp.domain.model.CatalogPhone;

import java.util.List;

public interface CatalogPhoneRepository {

  List<CatalogPhone> getPhoneCatalog();

}
