package es.codetest.phoneapp.infrastructure.repository;

import es.codetest.phoneapp.domain.model.CatalogPhone;
import es.codetest.phoneapp.domain.repository.CatalogPhoneRepository;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class RandomCatalogPhoneRepositoryImpl implements CatalogPhoneRepository {

  @Override
  public List<CatalogPhone> getPhoneCatalog() {
    return Arrays.asList(generateRandomPhone("Ericsson"),
        generateRandomPhone("Nokia"),
        generateRandomPhone("iPhone"),
        generateRandomPhone("Blackberry"),
        generateRandomPhone("Samsung"),
        generateRandomPhone("Xiaomi"));
  }

  private CatalogPhone generateRandomPhone(String name) {

    return CatalogPhone.of(
        UUID.randomUUID(),
        name,
        RandomStringUtils.randomAlphabetic(50),
        "http://www." + RandomStringUtils.randomAlphabetic(8) + ".com/image.png",
        new Random().ints(1, 999).findFirst().getAsInt()
    );

  }

}
