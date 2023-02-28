package TJV.fediayar_tjv_semestral.testController;


import TJV.fediayar_tjv_semestral.Exception.Excep;
import TJV.fediayar_tjv_semestral.controller.InsuranceController;
import TJV.fediayar_tjv_semestral.converter.InsuranceConverter;
import TJV.fediayar_tjv_semestral.domain.Client;
import TJV.fediayar_tjv_semestral.domain.Insurance;
import TJV.fediayar_tjv_semestral.dto.ClientDto;
import TJV.fediayar_tjv_semestral.dto.InsuranceDto;
import TJV.fediayar_tjv_semestral.service.AgencyService;
import TJV.fediayar_tjv_semestral.service.ClientService;
import TJV.fediayar_tjv_semestral.service.InsuranceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class InsuranceTestController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InsuranceService insuranceService;
    @MockBean
    private ClientService clientService;
    @MockBean
    private AgencyService agencyService;
    @MockBean
    private InsuranceConverter insuranceConverter;



    //integracni

    @Test
    void testCreateInsurance() throws Exception {
        InsuranceDto dto = new InsuranceDto(1L, LocalDate.parse("2014-12-10"), LocalDate.parse("2022-12-10"), 1200L, "Car", 2L, new HashSet<>());

        mockMvc.perform(post("/insurance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        ArgumentCaptor<Insurance> captor = ArgumentCaptor.forClass(Insurance.class);
        Mockito.verify(insuranceService, times(1)).create(captor.capture());
        Assertions.assertEquals(Long.MAX_VALUE, captor.getValue().getInsurance_id());
    }

    @Test
    void testReadOne() throws Exception {
        Client client = new Client(2L, "Vlad", "Hol", 21L, 1200L, "test", new HashSet<>());

        Insurance insurance = new Insurance(1L, LocalDate.parse("2014-12-10"), LocalDate.parse("2022-12-10"), 1200L, "Car", client, new HashSet<>());
        Mockito.when(insuranceService.readOne(insurance.getInsurance_id())).thenReturn(insurance);

        Mockito.when(insuranceConverter.fromDomain(insurance)).thenReturn(
                new InsuranceDto(insurance.getInsurance_id(), insurance.getStart_date(), insurance.getEnd_date(), insurance.getPrice(), insurance.getType_of_insurance(), 2L, new HashSet<>()));


        //testing getting not existing menuItem - should not be found
        Mockito.when(insuranceService.readOne(not(eq(1L)))).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/insurance/1").accept("application/json"))
                .andExpect(status().is(200));

    }

    @Test
    void testReadAll() throws Exception {
        Client client1 = new Client(1L, "Vlad", "Hol", 21L, 1200L, "test", new HashSet<>());
        Insurance insurance1 = new Insurance(1L, LocalDate.parse("2014-12-10"), LocalDate.parse("2020-12-10"), 12000L, "Car", client1, new HashSet<>());
        Insurance insurance2 = new Insurance(2L, LocalDate.parse("2011-12-10"), LocalDate.parse("2012-12-10"), 22010L, "House", client1, new HashSet<>());
        Insurance insurance3 = new Insurance(3L, LocalDate.parse("2022-12-10"), LocalDate.parse("2026-12-10"), 32003L, "Human", client1, new HashSet<>());

        InsuranceDto dto1 = new InsuranceDto(insurance1.getInsurance_id(), insurance1.getStart_date(), insurance1.getEnd_date(), insurance1.getPrice(), insurance1.getType_of_insurance(), 1L, new HashSet<>());
        InsuranceDto dto2 = new InsuranceDto(insurance2.getInsurance_id(), insurance2.getStart_date(), insurance2.getEnd_date(), insurance2.getPrice(), insurance2.getType_of_insurance(), 1L, new HashSet<>());
        InsuranceDto dto3 = new InsuranceDto(insurance3.getInsurance_id(), insurance3.getStart_date(), insurance3.getEnd_date(), insurance3.getPrice(), insurance3.getType_of_insurance(), 1L, new HashSet<>());
        Mockito.when(insuranceService.readAll()).thenReturn(List.of(insurance1, insurance2, insurance3));
        Mockito.when(insuranceConverter.fromDomainMany(List.of(insurance1, insurance2, insurance3))).thenReturn(List.of(dto1, dto2, dto3));

        //vůbec nepotřebuju celou url, potřebuju jen to uri, to zajímavé urvnitř mé aplikace
        mockMvc.perform(MockMvcRequestBuilders.get("/insurance").accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].insurance_id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].insurance_id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].insurance_id", Matchers.is(3)));
    }

    @Test
    void testDeleteInsurance() throws Exception {
        Client client1 = new Client(1L, "Vlad", "Hol", 21L, 1200L, "test", new HashSet<>());

        Insurance insurance = new Insurance(1L, LocalDate.parse("2014-12-10"), LocalDate.parse("2020-12-10"), 12000L, "Car", client1, new HashSet<>());
        doThrow(new Excep("No such element.")).when(insuranceService).delete(not(eq(insurance.getInsurance_id())));

        //testing deleting existing entity
        mockMvc.perform(MockMvcRequestBuilders.delete("/insurance/1").accept("application/json"))
                .andExpect(status().isOk());
        Mockito.verify(insuranceService, times(1)).delete(insurance.getInsurance_id());



    }

    @Test
    void testAddInsurance() throws Exception {
        Client client = new Client(1L, "Vlad", "Hol", 21L, 1200L, "test", new HashSet<>());

        Insurance insurance = new Insurance(Long.MAX_VALUE, LocalDate.parse("2014-12-10"), LocalDate.parse("2020-12-10"), 12000L, "Car",client , new HashSet<>());
        InsuranceDto insuranceDto = new InsuranceDto(Long.MAX_VALUE, LocalDate.parse("2014-12-10"), LocalDate.parse("2020-12-10"), 12000L, "Car",1L , new HashSet<>());

        Mockito.when(insuranceService.readOne(insuranceDto.getInsurance_id())).thenReturn(insurance);
        Mockito.when(clientService.readOne(client.getClient_id())).thenReturn(client); //kak tolka obratishsa k servisu s takimto aidi -- verni takohoto klienta

        mockMvc.perform(post("/insurance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(insuranceDto)))
                .andExpect(status().isOk());

        ArgumentCaptor<Insurance> insuranceCaptor = ArgumentCaptor.forClass(Insurance.class);
        Mockito.verify(insuranceService, times(1)).create(insuranceCaptor.capture());
        Assertions.assertEquals(insuranceCaptor.getValue().getInsurance_id(), Long.MAX_VALUE);
        Assertions.assertEquals(insuranceCaptor.getValue().getStart_date(), LocalDate.parse("2014-12-10"));
        Assertions.assertEquals(insuranceCaptor.getValue().getEnd_date(), LocalDate.parse("2020-12-10"));
        Assertions.assertEquals(insuranceCaptor.getValue().getPrice(), 12000L);
        Assertions.assertEquals(insuranceCaptor.getValue().getType_of_insurance(), "Car");
        Assertions.assertEquals(insuranceCaptor.getValue().getClient(), client);
        Assertions.assertEquals(insuranceCaptor.getValue().getAgencies(), new HashSet<>());


    }



}
