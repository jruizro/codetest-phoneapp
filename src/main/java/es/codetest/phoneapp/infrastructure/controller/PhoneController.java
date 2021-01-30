package es.codetest.phoneapp.infrastructure.controller;


import es.codetest.phoneapp.application.getphonecatalog.GetPhoneCatalogService;
import es.codetest.phoneapp.infrastructure.controller.apidoc.PhoneCatalogAPIDocumentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/v1")
@SuppressWarnings("unchecked")
public class PhoneController implements PhoneCatalogAPIDocumentation {

  private static final Logger LOGGER = LoggerFactory.getLogger(PhoneController.class);

  @Autowired
  private GetPhoneCatalogService getPhoneCatalogService;

  @Override
  @GetMapping(value = "/phones",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity getPhoneCatalog() {

    LOGGER.info("GET Phone Catalog");

    try {

      return status(HttpStatus.OK).body(getPhoneCatalogService.execute());

    } catch (RuntimeException e) {
      LOGGER.error("GET Phone Catalog fails -> Exception: {}", e.getMessage(), e);
      return status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

  }

}
