package TJV.fediayar_tjv_semestral.testController;


import TJV.fediayar_tjv_semestral.Exception.Excep;
import TJV.fediayar_tjv_semestral.controller.ClientController;
import TJV.fediayar_tjv_semestral.controller.InsuranceController;
import TJV.fediayar_tjv_semestral.converter.ClientConverter;
import TJV.fediayar_tjv_semestral.domain.Client;
import TJV.fediayar_tjv_semestral.domain.Insurance;
import TJV.fediayar_tjv_semestral.dto.ClientDto;
import TJV.fediayar_tjv_semestral.dto.InsuranceDto;
import TJV.fediayar_tjv_semestral.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.mockito.AdditionalMatchers.not;

import static org.mockito.ArgumentMatchers.eq;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class СlientСontrollerTest {


    @Autowired
    private MockMvc mockMvc; //tady ten mock není mock našeho kódu, ale simulujeme tím http rozhraní

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientService clientService;
    @MockBean
    private ClientConverter clientConverter;
    @MockBean
    private InsuranceController insuranceController;


    @Test
    void testCreateClient() throws Exception {
        ClientDto dto = new ClientDto(2L, "Vlad", "Hol", 21L, 1200L, "test", new HashSet<>());

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        ArgumentCaptor<Client> captor = ArgumentCaptor.forClass(Client.class);
        Mockito.verify(clientService, times(1)).create(captor.capture());
        Assertions.assertEquals(Long.MAX_VALUE, captor.getValue().getClient_id());
    }

    @Test
    void testReadOne() throws Exception {
        Client client = new Client(2L, "Vlad", "Hol", 21L, 1200L, "test", new HashSet<>());
        Mockito.when(clientService.readOne(client.getClient_id())).thenReturn(client);

        Mockito.when(clientConverter.fromDomain(client)).thenReturn(
                new ClientDto(client.getClient_id(), client.getFirst_name(), client.getLast_name(), client.getAge(), client.getIncome(), client.getWork_place(), new HashSet<>()));


        //testing getting not existing menuItem - should not be found
            Mockito.when(clientService.readOne(not(eq(1L)))).thenReturn(null);
            mockMvc.perform(MockMvcRequestBuilders.get("/clients/2").accept("application/json"))
                    .andExpect(status().is(200));

    }

    @Test
    void testReadAll() throws Exception {
        Client client1 = new Client(1L, "Vlad", "Hol", 21L, 1200L, "test", new HashSet<>());
        Client client2 = new Client(2L, "Kost", "Vol", 22L, 12000L, "test", new HashSet<>());
        Client client3 = new Client(3L, "Mazat", "Gol", 32L, 4400L, "test", new HashSet<>());

        ClientDto dto1 = new ClientDto(client1.getClient_id(), client1.getFirst_name(), client1.getLast_name(), client1.getAge(), client1.getIncome(), client1.getWork_place(), new HashSet<>());
        ClientDto dto2 = new ClientDto(client2.getClient_id(), client2.getFirst_name(), client2.getLast_name(), client2.getAge(), client2.getIncome(), client2.getWork_place(), new HashSet<>());
        ClientDto dto3 = new ClientDto(client3.getClient_id(), client3.getFirst_name(), client3.getLast_name(), client3.getAge(), client3.getIncome(), client3.getWork_place(), new HashSet<>());
        Mockito.when(clientService.readAll()).thenReturn(List.of(client1, client2, client3));
        Mockito.when(clientConverter.fromDomainMany(List.of(client1, client2, client3))).thenReturn(List.of(dto1, dto2, dto3));

        //vůbec nepotřebuju celou url, potřebuju jen to uri, to zajímavé urvnitř mé aplikace
        mockMvc.perform(MockMvcRequestBuilders.get("/clients").accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].client_id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].client_id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].client_id", Matchers.is(3)));
    }

    @Test
    void testDeleteClient() throws Exception {
        Client client = new Client(1L, "Vlad", "Hol", 21L, 1200L, "test", new HashSet<>());
        doThrow(new Excep("No such element.")).when(clientService).delete(not(eq(client.getClient_id())));

        //testing deleting existing entity
        mockMvc.perform(MockMvcRequestBuilders.delete("/clients/1").accept("application/json"))
                .andExpect(status().isOk());
        Mockito.verify(clientService, times(1)).delete(client.getClient_id());



    }

    @Test
    void testAddClient() throws Exception {
        ClientDto clientDto = new ClientDto(Long.MAX_VALUE, "Vlad", "Hol", 21L, 1200L, "test", new HashSet<>());
        Client client = new Client(Long.MAX_VALUE, "Vlad", "Hol", 21L, 1200L, "test", new HashSet<>());

        Mockito.when(clientService.readOne(clientDto.getClient_id())).thenReturn(client);

        mockMvc.perform(post("/clients", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDto)))
                .andExpect(status().isOk());

        ArgumentCaptor<Client> clientcaptor = ArgumentCaptor.forClass(Client.class);
        Mockito.verify(clientService, times(1)).create(clientcaptor.capture());
        Assertions.assertEquals(clientcaptor.getValue().getClient_id(), Long.MAX_VALUE);
        Assertions.assertEquals(clientcaptor.getValue().getFirst_name(), "Vlad");
        Assertions.assertEquals(clientcaptor.getValue().getLast_name(), "Hol");
        Assertions.assertEquals(clientcaptor.getValue().getAge(), 21L);
        Assertions.assertEquals(clientcaptor.getValue().getIncome(), 1200L);
        Assertions.assertEquals(clientcaptor.getValue().getWork_place(), "test");
        Assertions.assertEquals(clientcaptor.getValue().getInsurances(), new HashSet<>());


    }


}
