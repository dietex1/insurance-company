package TJV.fediayar_tjv_semestral.testController;

import TJV.fediayar_tjv_semestral.Exception.Excep;
import TJV.fediayar_tjv_semestral.controller.InsuranceController;
import TJV.fediayar_tjv_semestral.converter.AgencyConverter;
import TJV.fediayar_tjv_semestral.converter.InsuranceConverter;
import TJV.fediayar_tjv_semestral.domain.Agency;
import TJV.fediayar_tjv_semestral.domain.Client;
import TJV.fediayar_tjv_semestral.domain.Insurance;
import TJV.fediayar_tjv_semestral.dto.AgencyDto;
import TJV.fediayar_tjv_semestral.dto.ClientDto;
import TJV.fediayar_tjv_semestral.dto.InsuranceDto;
import TJV.fediayar_tjv_semestral.service.AgencyService;
import TJV.fediayar_tjv_semestral.service.ClientService;
import TJV.fediayar_tjv_semestral.service.InsuranceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.aspectj.weaver.ast.Or;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class AgencyTestController {

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
    private AgencyConverter agencyConverter;



//    @Test
//    void testCreateAgency() throws Exception {
//        AgencyDto dto = new AgencyDto(1L, "Broker",   "Prague", new HashSet<>());
//
//        mockMvc.perform(post("/agencies")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isOk());
//
//        ArgumentCaptor<Agency> captor = ArgumentCaptor.forClass(Agency.class);
//        Mockito.verify(agencyService, times(1)).create(captor.capture());
//        Assertions.assertEquals(Long.MAX_VALUE, captor.getValue().getAgency_id());
//
//
//
//
//    }




    @Test
    void testReadOne() throws Exception {
        Agency agency = new Agency(1L, "Broker",   "Prague", new HashSet<>());

        Mockito.when(agencyService.readOne(agency.getAgency_id())).thenReturn(agency);

        Mockito.when(agencyConverter.fromDomain(agency)).thenReturn(
                new AgencyDto(agency.getAgency_id(), agency.getAgency_name(), agency.getCity(),  new HashSet<>()));


        //testing getting not existing menuItem - should not be found
        Mockito.when(agencyService.readOne(not(eq(1L)))).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/agencies/1").accept("application/json"))
                .andExpect(status().is(200));

    }

    @Test
    void testReadAll() throws Exception {

        Agency agency1 = new Agency(1L, "Broker",   "Prague", new HashSet<>());

        Agency agency2 = new Agency(2L, "InSunLife",   "Kyiv", new HashSet<>());

        Agency agency3 = new Agency(3L, "SuranceYou",   "Lviv", new HashSet<>());

        AgencyDto dto1 = new AgencyDto(agency1.getAgency_id(), agency1.getAgency_name(), agency1.getCity(),  new HashSet<>());
        AgencyDto dto2 = new AgencyDto(agency2.getAgency_id(), agency2.getAgency_name(), agency2.getCity(),  new HashSet<>());
        AgencyDto dto3 = new AgencyDto(agency3.getAgency_id(), agency3.getAgency_name(), agency3.getCity(),  new HashSet<>());
        Mockito.when(agencyService.readAll()).thenReturn(List.of(agency1, agency2, agency3));
        Mockito.when(agencyConverter.fromDomainMany(List.of(agency1, agency2, agency3))).thenReturn(List.of(dto1, dto2, dto3));

        //vůbec nepotřebuju celou url, potřebuju jen to uri, to zajímavé urvnitř mé aplikace
        mockMvc.perform(MockMvcRequestBuilders.get("/agencies").accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].agency_id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].agency_id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].agency_id", Matchers.is(3)));
    }

    @Test
    void testDeleteAgency() throws Exception {
        Agency agency1 = new Agency(1L, "Broker",   "Prague", new HashSet<>());
        doThrow(new Excep("No such element.")).when(agencyService).delete(not(eq(agency1.getAgency_id())));

        //testing deleting existing entity
        mockMvc.perform(MockMvcRequestBuilders.delete("/agencies/1").accept("application/json"))
                .andExpect(status().isOk());
        Mockito.verify(agencyService, times(1)).delete(agency1.getAgency_id());



    }

//    @Test
//    void testAddAgency() throws Exception {
//
//        Agency agency = new Agency(Long.MAX_VALUE, "Broker",   "Prague", new HashSet<>());
//        AgencyDto agencyDto = new AgencyDto(Long.MAX_VALUE, "Broker",   "Prague", new HashSet<>());
//
//        Mockito.when(agencyConverter.toDomain(agencyDto)).thenReturn(agency);
//
//        Mockito.when(agencyService.readOne(agency.getAgency_id())).thenReturn(agency);
////        Mockito.when(agencyService.create(agency)).thenReturn(agency);
//
//        mockMvc.perform(post("/agencies")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(agencyDto)))
//                .andExpect(status().isOk());
//
////        ArgumentCaptor<Agency> agencyCaptor = ArgumentCaptor.forClass(Agency.class);
////        Mockito.verify(agencyService, times(1)).create(agencyCaptor.capture());
////        Assertions.assertEquals(agencyCaptor.getValue().getAgency_id(), Long.MAX_VALUE);
////        Assertions.assertEquals(agencyCaptor.getValue().getAgency_name(), "Broker");
////        Assertions.assertEquals(agencyCaptor.getValue().getCity(), "Prague");
////        Assertions.assertEquals(agencyCaptor.getValue().getInsurances(), new HashSet<>());
//
//
//    }



}
