package es.codetest.phoneapp.application.getphonecatalog;

import es.codetest.phoneapp.domain.repository.CatalogPhoneRepository;

import java.util.List;
import java.util.stream.Collectors;

public class GetPhoneCatalogService {

  private final CatalogPhoneRepository catalogPhoneRepository;

  public GetPhoneCatalogService(CatalogPhoneRepository catalogPhoneRepository) {
    this.catalogPhoneRepository = catalogPhoneRepository;
  }

  public List<PhoneCatalogInfoDTO> execute() {

    return catalogPhoneRepository.getPhoneCatalog().stream()
        .map(cp -> new PhoneCatalogInfoDTO(
            cp.getName(),
            cp.getDescription(),
            cp.getImage(),
            cp.getPrice()
        )).collect(Collectors.toList());

  }

}
