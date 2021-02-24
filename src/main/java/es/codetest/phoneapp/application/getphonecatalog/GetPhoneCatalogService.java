package es.codetest.phoneapp.application.getphonecatalog;

import es.codetest.phoneapp.domain.repository.CatalogPhoneRepository;
import io.reactivex.Single;

import java.util.List;

public class GetPhoneCatalogService {

  private final CatalogPhoneRepository catalogPhoneRepository;

  public GetPhoneCatalogService(CatalogPhoneRepository catalogPhoneRepository) {
    this.catalogPhoneRepository = catalogPhoneRepository;
  }

  public Single<List<PhoneCatalogInfoDTO>> execute() {

    return catalogPhoneRepository.getPhoneCatalog()
        .flattenAsObservable(phone -> phone)
        .map(phone -> new PhoneCatalogInfoDTO(phone.getName(), phone.getDescription(), phone.getImage(), phone.getPrice()))
        .toList();
  }

}
