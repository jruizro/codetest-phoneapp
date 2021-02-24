package es.codetest.phoneapp.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PhoneappApplication {

  private static final Logger LOGGER = LoggerFactory.getLogger(PhoneappApplication.class);

  public static void main(String[] args) {

    try {
      SpringApplication.run(PhoneappApplication.class, args);
    } catch (Exception e) {
      LOGGER.error("Spring Application launch failed. Terminating...");
    }
  }

}
