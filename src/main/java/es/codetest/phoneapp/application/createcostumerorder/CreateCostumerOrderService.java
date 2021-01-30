package es.codetest.phoneapp.application.createcostumerorder;

import es.codetest.phoneapp.domain.model.CostumerOrder;
import es.codetest.phoneapp.domain.repository.CatalogPhoneRepository;

public class CreateCostumerOrderService {

  private CatalogPhoneRepository catalogPhoneRepository;

  public CreateCostumerOrderService(CatalogPhoneRepository catalogPhoneRepository) {
    this.catalogPhoneRepository = catalogPhoneRepository;
  }

  public CostumerOrderResponseDTO execute(CostumerOrderRequestDTO requestOrder) {

    CostumerOrder costumerOrder = CostumerOrder.create()
        .withName(requestOrder.getName())
        .withSurname(requestOrder.getSurname())
        .withEmail(requestOrder.getEmail())
        .withPhonesNames(requestOrder.getPhonesNames())
        .withPhoneCatalog(catalogPhoneRepository.getPhoneCatalog())
        .build();

    return new CostumerOrderResponseDTO(
        costumerOrder.getName(),
        costumerOrder.getSurname(),
        costumerOrder.getEmail(),
        costumerOrder.getPhoneNames(),
        costumerOrder.getTotalPrice()
    );

  }

}
