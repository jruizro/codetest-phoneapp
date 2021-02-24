package es.codetest.phoneapp.infrastructure.repository;

import es.codetest.phoneapp.domain.model.CatalogPhone;
import es.codetest.phoneapp.infrastructure.PhoneappApplication;
import es.codetest.phoneapp.infrastructure.repository.mapper.PostgresCatalogPhoneMapper;
import io.reactivex.Single;
import io.vertx.junit5.VertxExtension;
import io.vertx.reactivex.sqlclient.Pool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;


@ExtendWith(VertxExtension.class)
@SpringBootTest(classes = PhoneappApplication.class, properties = "spring.profiles.active=integrationtest")
@AutoConfigureMockMvc
public class PostgresCatalogPhoneRepositoryShould {

  @Autowired
  private Pool jdbcPool;

  private PostgresCatalogPhoneRepository catalogPhoneRepository;

  @BeforeEach
  public void setUp() {
    catalogPhoneRepository = new PostgresCatalogPhoneRepository(jdbcPool, new PostgresCatalogPhoneMapper());
  }

  @Test
  @Sql(
      executionPhase = BEFORE_TEST_METHOD,
      statements = {
          "DELETE FROM PHONES_CATALOGUE",
          "INSERT INTO PHONES_CATALOGUE(PC_ID, PC_NAME, PC_PRICE, PC_IMAGE, PC_DESCRIPTION) VALUES " +
              "(uuid_generate_v4(), 'EricssonTest', 100, 'http://www.ericsson.com/image.png', 'Ericsson Mobile'), " +
              "(uuid_generate_v4(), 'NokiaTest', 100, 'http://www.nokia.com/image.png', 'Nokia Mobile'), " +
              "(uuid_generate_v4(), 'iPhoneTest', 100, 'http://www.apple.com/image.png', 'Apple Mobile'), " +
              "(uuid_generate_v4(), 'BlackberryTest', 100, 'http://www.blackberry.com/image.png', 'Blackberry Mobile'), " +
              "(uuid_generate_v4(), 'SamsungTest', 100, 'http://www.samsung.com/image.png', 'Samsung Mobile'), " +
              "(uuid_generate_v4(), 'XiaomiTest', 100, 'http://www.xiaomi.com/image.png', 'Xiaomi Mobile')"
      }
  )
  @Sql(
      executionPhase = AFTER_TEST_METHOD,
      statements = {
          "DELETE FROM PHONES_CATALOGUE"
      }
  )
  public void returnAllPhonesCatalog() {
    // given

    // when
    Single<List<CatalogPhone>> result = catalogPhoneRepository.getPhoneCatalog();

    // then
    List<CatalogPhone> resultList = result.blockingGet();
    assertEquals(resultList.size(), 6);
    List<String> phonesList = resultList.stream().map(CatalogPhone::getName).collect(Collectors.toList());
    assertTrue(phonesList.contains("EricssonTest"));
    assertTrue(phonesList.contains("NokiaTest"));
    assertTrue(phonesList.contains("iPhoneTest"));
    assertTrue(phonesList.contains("BlackberryTest"));
    assertTrue(phonesList.contains("SamsungTest"));
    assertTrue(phonesList.contains("XiaomiTest"));
  }

  @Test
  @Sql(
      executionPhase = BEFORE_TEST_METHOD,
      statements = {
          "DELETE FROM PHONES_CATALOGUE"
      }
  )
  public void returnAnyCatalogIfAnyExists() {
    // given

    // when
    Single<List<CatalogPhone>> result = catalogPhoneRepository.getPhoneCatalog();

    // then
    List<CatalogPhone> resultList = result.blockingGet();
    assertEquals(resultList.size(), 0);

  }

}
