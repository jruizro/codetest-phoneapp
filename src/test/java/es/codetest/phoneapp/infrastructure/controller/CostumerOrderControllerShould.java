package es.codetest.phoneapp.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.codetest.phoneapp.application.createcostumerorder.CostumerOrderRequestDTO;
import es.codetest.phoneapp.application.createcostumerorder.CostumerOrderResponseDTO;
import es.codetest.phoneapp.application.createcostumerorder.CreateCostumerOrderService;
import org.apache.logging.log4j.util.Strings;
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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {CostumerOrderController.class, CreateCostumerOrderService.class})
@WebMvcTest
public class CostumerOrderControllerShould {

  @Autowired
  private MockMvc mockMvc;

  ObjectMapper objectMapper = new ObjectMapper();

  @MockBean
  private CreateCostumerOrderService createCostumerOrderService;

  @Test
  public void returnOKCostumerOrder() throws Exception {
    CostumerOrderRequestDTO requestOrder = new CostumerOrderRequestDTO(
        "User", "Surname", "email@email.es",
        Lists.newArrayList("Nokia", "iPhone"));
    CostumerOrderResponseDTO costumerOrder = new CostumerOrderResponseDTO(
        requestOrder.getName(),
        requestOrder.getSurname(),
        requestOrder.getEmail(),
        requestOrder.getPhonesNames(),
        300
    );
    when(createCostumerOrderService.execute(any(CostumerOrderRequestDTO.class)))
        .thenReturn(costumerOrder);

    MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/v1/order")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(objectMapper.writeValueAsString(requestOrder))
        .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andReturn();

    String result = response.getResponse().getContentAsString();
    assertEquals(result, objectMapper.writeValueAsString(costumerOrder));

  }

  @Test
  public void return500WhenFails() throws Exception {
    CostumerOrderRequestDTO requestOrder = new CostumerOrderRequestDTO(
        "User", "Surname", "email@email.es",
        Lists.newArrayList("Nokia", "iPhone"));
    when(createCostumerOrderService.execute(any(CostumerOrderRequestDTO.class)))
        .thenThrow(new RuntimeException("Something Fails"));

    MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/v1/order")
        .content(objectMapper.writeValueAsString(requestOrder))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is5xxServerError())
        .andReturn();

    String result = response.getResponse().getContentAsString();
    assertEquals(result, "Something Fails");

  }

}
