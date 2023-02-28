package TJV.fediayar_tjv_semestral.testService;

import TJV.fediayar_tjv_semestral.Exception.Excep;
import TJV.fediayar_tjv_semestral.FediayarTjvSemestralApplication;
import TJV.fediayar_tjv_semestral.domain.Agency;
import TJV.fediayar_tjv_semestral.domain.Client;
import TJV.fediayar_tjv_semestral.domain.Insurance;
import TJV.fediayar_tjv_semestral.repository.ClientRepository;
import TJV.fediayar_tjv_semestral.repository.InsuranceRepository;
import TJV.fediayar_tjv_semestral.service.ClientService;
import TJV.fediayar_tjv_semestral.service.InsuranceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = FediayarTjvSemestralApplication.class)
class InsunServiceTest {

    @Autowired
    private InsuranceService insuranceService;

    @MockBean
    private InsuranceRepository insuranceRepository;

    @MockBean
    private ClientRepository clientRepository;

    private Client client1;
    private Client client2;

    private Insurance savedInsurance;
    private Insurance updatedSavedInsurance;
    private Insurance anotherSavedInsurance;
    private Insurance unsavedInsurance;
    private Agency m1 = new Agency(1L, "Test1", "Praha",  null);
    private Agency m2 = new Agency(2L, "Test2", "Praha2",  null);
    private Agency m3 = new Agency(3L, "Test3", "Praha3",  null);

    @BeforeEach
    void setUp() {
        client1 = new Client(2L, "Vlad", "Hol", 21L, 1200L, "test", new HashSet<>());
        client2 = new Client(2L, "Kost", "Vol", 22L, 12000L, "test", new HashSet<>());
        savedInsurance = new Insurance(1L, LocalDate.parse("2012-12-10"), LocalDate.parse("2022-12-12"),12000L, "Car", client1, new HashSet<>(Collections.singletonList(m1)));

        unsavedInsurance = new Insurance(2L, LocalDate.parse("2013-12-10"), LocalDate.parse("2023-12-12"),13000L, "House", client2, new HashSet<>(Collections.singletonList(m2)));
        anotherSavedInsurance = new Insurance(3L, LocalDate.parse("2014-12-10"), LocalDate.parse("2023-12-12"),13000L, "HumanInsun", client2, new HashSet<>(Collections.singletonList(m2)));
        updatedSavedInsurance = new Insurance(1L, LocalDate.parse("2023-12-10"), LocalDate.parse("2030-12-12"),13000L, "HumanInsun", client2, new HashSet<>(Collections.singletonList(m2)));


        Mockito.when(insuranceRepository.existsById(savedInsurance.getInsurance_id())).thenReturn(true);
        Mockito.when(insuranceRepository.existsById(anotherSavedInsurance.getInsurance_id())).thenReturn(true);
        Mockito.when(insuranceRepository.findById(savedInsurance.getInsurance_id())).thenReturn(Optional.of(savedInsurance));
        Mockito.when(insuranceRepository.findById(unsavedInsurance.getInsurance_id())).thenReturn(Optional.empty());
        Mockito.when(insuranceRepository.findAll()).thenReturn(List.of(savedInsurance, anotherSavedInsurance));
    }




    @Test
    void create() throws Excep {
        Assertions.assertThrows(Excep.class, () -> insuranceService.create(savedInsurance));

        insuranceService.create(unsavedInsurance);
        Mockito.verify(insuranceRepository, Mockito.times(1)).save(unsavedInsurance);
    }

    @Test
    void readById() throws Excep {

        assertEquals(savedInsurance, insuranceService.readOne(savedInsurance.getInsurance_id()));
        assertThrows( Excep.class, () -> insuranceService.readOne(unsavedInsurance.getInsurance_id()));
    }

    @Test
    void readAll() {
        Assertions.assertEquals(List.of(savedInsurance, anotherSavedInsurance), insuranceService.readAll());
    }

    @Test
    void update() throws Excep {
            insuranceService.update(updatedSavedInsurance.getInsurance_id(),updatedSavedInsurance);
            Mockito.verify(insuranceRepository, Mockito.times(1)).save(updatedSavedInsurance);

            assertThrows(Excep.class, () -> insuranceService.update(unsavedInsurance.getInsurance_id(),unsavedInsurance));
    }



    @Test
    void deleteById() throws Excep {
        insuranceService.delete(savedInsurance.getInsurance_id());
        Mockito.verify(insuranceRepository, Mockito.times(1)).deleteById(savedInsurance.getInsurance_id());

        assertThrows(Excep.class, () -> insuranceService.delete(unsavedInsurance.getInsurance_id()));
    }
}


