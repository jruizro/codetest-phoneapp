package es.codetest.phoneapp.application.createcostumerorder;

import es.codetest.phoneapp.domain.repository.CatalogPhoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateCostumerOrderServiceShould {

  @Mock
  private CatalogPhoneRepository catalogPhoneRepositoryMock;

  private CreateCostumerOrderService createCostumerOrderService;

  @BeforeEach
  public void setUp() {
    createCostumerOrderService = new CreateCostumerOrderService(catalogPhoneRepositoryMock);
  }
  /*

  @Test
  public void returnCostumerOrderCreatedSuccessfully() {
    // given
    List<CatalogPhone> phoneCatalog = CatalogPhoneMother.generatePhoneCatalog();
    when(catalogPhoneRepositoryMock.getPhoneCatalog())
        .thenReturn(phoneCatalog);
    CostumerOrderRequestDTO orderRequest = new CostumerOrderRequestDTO(
        "User",
        "Surname",
        "email@email.es",
        Lists.newArrayList("Nokia", "iPhone")
    );

    // when
    CostumerOrderResponseDTO result = createCostumerOrderService.execute(orderRequest);

    // then
    assertEquals(result.getPhonesNames().size(), 2);
    assertTrue(result.getPhonesNames().contains("Nokia"));
    assertTrue(result.getPhonesNames().contains("iPhone"));
    assertEquals(result.getTotalPrice(), 2);
  }

  @Test
  public void throwExceptionIfCostumerOrderFails() {
    // given
    List<CatalogPhone> phoneCatalog = CatalogPhoneMother.generatePhoneCatalog();
    when(catalogPhoneRepositoryMock.getPhoneCatalog())
        .thenReturn(phoneCatalog);
    CostumerOrderRequestDTO orderRequest = new CostumerOrderRequestDTO(
        "User",
        "Surname",
        "email@email.es",
        Lists.emptyList()
    );

    // when
    Exception exception = assertThrows(
        IllegalArgumentException.class,
        () -> createCostumerOrderService.execute(orderRequest));

    // then
    assertEquals("Ordered Phone's names should not be empty", exception.getMessage());


  }
*/
}
