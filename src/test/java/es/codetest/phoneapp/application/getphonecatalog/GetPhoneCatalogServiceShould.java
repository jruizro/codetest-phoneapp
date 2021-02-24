package es.codetest.phoneapp.application.getphonecatalog;

import es.codetest.phoneapp.domain.model.CatalogPhone;
import es.codetest.phoneapp.domain.repository.CatalogPhoneRepository;
import es.codetest.phoneapp.mother.CatalogPhoneMother;
import io.reactivex.Single;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetPhoneCatalogServiceShould {

  @Mock
  private CatalogPhoneRepository catalogPhoneRepositoryMock;

  private GetPhoneCatalogService getPhoneCatalogService;

  @BeforeEach
  public void setUp() {
    getPhoneCatalogService = new GetPhoneCatalogService(catalogPhoneRepositoryMock);
  }

  @Test
  public void returnAllPhonesCatalog() {
    // given
    Single<List<CatalogPhone>> phoneCatalog = Single.just(CatalogPhoneMother.generatePhoneCatalog());
    when(catalogPhoneRepositoryMock.getPhoneCatalog())
        .thenReturn(phoneCatalog);

    // when
    Single<List<PhoneCatalogInfoDTO>> result = getPhoneCatalogService.execute();

    // then
    List<String> catalogPhoneNames = result.blockingGet().stream()
        .map(p -> p.getName())
        .collect(Collectors.toList());
    assertEquals(result.blockingGet().size(), catalogPhoneNames.size());
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
    Single<List<PhoneCatalogInfoDTO>> result = getPhoneCatalogService.execute();

    // then
    assertEquals(result.blockingGet().size(), 0);

  }

}
