package TJV.fediayar_tjv_semestral.testService;



import TJV.fediayar_tjv_semestral.Exception.Excep;
import TJV.fediayar_tjv_semestral.FediayarTjvSemestralApplication;
import TJV.fediayar_tjv_semestral.domain.Agency;
import TJV.fediayar_tjv_semestral.domain.Client;
import TJV.fediayar_tjv_semestral.domain.Insurance;
import TJV.fediayar_tjv_semestral.dto.ClientDto;
import TJV.fediayar_tjv_semestral.repository.ClientRepository;
import TJV.fediayar_tjv_semestral.repository.InsuranceRepository;
import TJV.fediayar_tjv_semestral.service.AgencyService;
import TJV.fediayar_tjv_semestral.service.ClientService;
import TJV.fediayar_tjv_semestral.service.InsuranceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;

import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = FediayarTjvSemestralApplication.class)
class ClientTestService {

        @Autowired
        private ClientService clientService;

        @MockBean
        private ClientRepository clientRepositoryMock;

        @MockBean
        private Client client;

      //  private Client client;
        private Agency agency;

        private Client savedClient;
        private Client updatedSavedClient;
        private Client unsavedClient;
        private Client anotherSavedClient;


        @BeforeEach
        void setUp() {
                savedClient = new Client(2L, "Vlad", "Hol", 21L, 1200L, "test", new HashSet<>());
            updatedSavedClient = new Client(2L, "Kost", "Vol", 22L, 12000L, "test", new HashSet<>());
                unsavedClient = new Client(4L, "Mazat", "Gol", 32L, 4400L, "test", new HashSet<>());
            anotherSavedClient = new Client(5L, "Test", "Tol", 44L, 6200L, "test", new HashSet<>());

                Mockito.when(clientRepositoryMock.existsById(savedClient.getClient_id())).thenReturn(true);
                Mockito.when(clientRepositoryMock.existsById(anotherSavedClient.getClient_id())).thenReturn(true);
                Mockito.when(clientRepositoryMock.findById(savedClient.getClient_id())).thenReturn(Optional.of(savedClient));
                Mockito.when(clientRepositoryMock.findById(unsavedClient.getClient_id())).thenReturn(Optional.empty());
                Mockito.when(clientRepositoryMock.findAll()).thenReturn(List.of(savedClient, anotherSavedClient));
        }





        @Test
        void create() throws Excep {
                assertThrows(Excep.class, () -> clientService.create(savedClient));

                clientService.create(unsavedClient);
                Mockito.verify(clientRepositoryMock, Mockito.times(1)).save(unsavedClient);
        }




    @Test
        void readById() throws Excep {
                assertEquals(savedClient, clientService.readOne(savedClient.getClient_id()));
                assertThrows( Excep.class, () -> clientService.readOne(unsavedClient.getClient_id()));

        }

        @Test
        void readAll() {
                Assertions.assertEquals(List.of(savedClient, anotherSavedClient), clientService.readAll());
        }

        @Test
        void update() throws Excep {
                clientService.update(updatedSavedClient.getClient_id(),updatedSavedClient);
                Mockito.verify(clientRepositoryMock, Mockito.times(1)).save(updatedSavedClient);

                assertThrows(Excep.class, () -> clientService.update(unsavedClient.getClient_id(),unsavedClient));
        }

        @Test
        void deleteById() throws Excep {
                clientService.delete(savedClient.getClient_id());
                Mockito.verify(clientRepositoryMock, Mockito.times(1)).deleteById(savedClient.getClient_id());

                assertThrows(Excep.class, () -> clientService.delete(unsavedClient.getClient_id()));
        }
}


