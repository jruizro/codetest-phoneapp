package es.codetest.phoneapp.application.getphonecatalog;

import es.codetest.phoneapp.domain.repository.CatalogPhoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GetPhoneCatalogServiceShould {

  @Mock
  private CatalogPhoneRepository catalogPhoneRepositoryMock;

  private GetPhoneCatalogService getPhoneCatalogService;

  @BeforeEach
  public void setUp() {
    getPhoneCatalogService = new GetPhoneCatalogService(catalogPhoneRepositoryMock);
  }
/*
  @Test
  public void returnAllPhonesCatalog() {
    // given
    Single<List<CatalogPhone>> phoneCatalog = CatalogPhoneMother.generatePhoneCatalog();
    when(catalogPhoneRepositoryMock.getPhoneCatalog())
        .thenReturn(phoneCatalog);

    // when
    List<PhoneCatalogInfoDTO> result = getPhoneCatalogService.execute();

    // then
    List<String> catalogPhoneNames = result.stream()
        .map(p -> p.getName())
        .collect(Collectors.toList());
    assertEquals(result.size(), phoneCatalog.size());
    assertTrue(catalogPhoneNames.contains("Ericsson"));
    assertTrue(catalogPhoneNames.contains("Nokia"));
    assertTrue(catalogPhoneNames.contains("iPhone"));
    assertTrue(catalogPhoneNames.contains("Blackberry"));
    assertTrue(catalogPhoneNames.contains("Samsung"));
    assertTrue(catalogPhoneNames.contains("Xiaomi"));

  }

  @Test
  public void returnAnyPhonesCatalog() {

    // given
    List<CatalogPhone> phoneCatalog = Lists.emptyList();
    when(catalogPhoneRepositoryMock.getPhoneCatalog())
        .thenReturn(Single.just(phoneCatalog));

    // when
    List<PhoneCatalogInfoDTO> result = getPhoneCatalogService.execute();

    // then
    assertEquals(result.size(), 0);

  }
*/
}
