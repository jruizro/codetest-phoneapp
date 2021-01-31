package es.codetest.phoneapp.infrastructure.controller;

import es.codetest.phoneapp.application.getphonecatalog.GetPhoneCatalogService;
import es.codetest.phoneapp.application.getphonecatalog.PhoneCatalogInfoDTO;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {PhoneController.class, GetPhoneCatalogService.class})
@WebMvcTest
public class PhoneControllerShould {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private GetPhoneCatalogService getPhoneCatalogService;

  @Test
  public void returnOKWithCatalogPhones() throws Exception {

    when(getPhoneCatalogService.execute())
        .thenReturn(Lists.newArrayList(
            new PhoneCatalogInfoDTO("Nokia", "3030", "http:://image.jpg", 100),
            new PhoneCatalogInfoDTO("iPhone", "Version 12", "http:://image.png", 200)
        ));

    MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/v1/phones")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();

    String result = response.getResponse().getContentAsString();
    assertTrue(result.contains("Nokia"));
    assertTrue(result.contains("iPhone"));
  }

  @Test
  public void return500WhenFails() throws Exception {

    when(getPhoneCatalogService.execute())
        .thenThrow(new RuntimeException("Something Fails"));

    MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/v1/phones")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is5xxServerError())
        .andReturn();

    String result = response.getResponse().getContentAsString();
    assertEquals(result, "Something Fails");

  }

}
