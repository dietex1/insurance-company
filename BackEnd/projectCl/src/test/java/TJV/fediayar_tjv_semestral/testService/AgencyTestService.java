package TJV.fediayar_tjv_semestral.testService;


        import TJV.fediayar_tjv_semestral.Exception.Excep;
        import TJV.fediayar_tjv_semestral.FediayarTjvSemestralApplication;
        import TJV.fediayar_tjv_semestral.domain.Agency;
        import TJV.fediayar_tjv_semestral.repository.AgencyRepository;
        import TJV.fediayar_tjv_semestral.service.AgencyService;
        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import org.mockito.Mockito;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.context.SpringBootTest;
        import org.springframework.boot.test.mock.mockito.MockBean;

        import java.time.LocalDateTime;
        import java.util.*;

        import static org.junit.jupiter.api.Assertions.assertEquals;
        import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = FediayarTjvSemestralApplication.class)
public class AgencyTestService {


        @Autowired
        private AgencyService agencyService;

        @MockBean
        private AgencyRepository agencyRepository;

        private Agency savedAgency;
        private Agency updatedSavedAgency;
        private Agency anotherSavedAgency;
        private Agency unsavedAgency;

    @BeforeEach
    void setUp() {
        savedAgency = new Agency(1L, "Broker", "Praha", null);
        updatedSavedAgency = new Agency(1L, "InsuranceMe", "Praha",  new HashSet<>());
        unsavedAgency = new Agency(2L, "InSunLife", "Kyiv", null);
        anotherSavedAgency = new Agency(3L, "LifeSurance", "Odessa", null);

        Mockito.when(agencyRepository.existsById(savedAgency.getAgency_id())).thenReturn(true);
        Mockito.when(agencyRepository.existsById(anotherSavedAgency.getAgency_id())).thenReturn(true);
        Mockito.when(agencyRepository.findById(savedAgency.getAgency_id())).thenReturn(Optional.of(savedAgency));
        Mockito.when(agencyRepository.findById(unsavedAgency.getAgency_id())).thenReturn(Optional.empty());
        Mockito.when(agencyRepository.findAll()).thenReturn(List.of(savedAgency, anotherSavedAgency));
    }



    @Test
    void create() throws Excep {
        assertThrows(Excep.class, () -> agencyService.create(savedAgency));

        agencyService.create(unsavedAgency);
        Mockito.verify(agencyRepository, Mockito.times(1)).save(unsavedAgency);
    }

    @Test
    void readById() throws Excep {
        assertEquals(savedAgency, agencyService.readOne(savedAgency.getAgency_id()));
        assertThrows( Excep.class, () -> agencyService.readOne(unsavedAgency.getAgency_id()));

    }


    @Test
    void readAll() {
        Assertions.assertEquals(List.of(savedAgency, anotherSavedAgency), agencyService.readAll());
    }

    @Test
    void update() throws Excep {
        agencyService.update(updatedSavedAgency.getAgency_id(),updatedSavedAgency);
        Mockito.verify(agencyRepository, Mockito.times(1)).save(updatedSavedAgency);

        assertThrows(Excep.class, () -> agencyService.update(unsavedAgency.getAgency_id(),unsavedAgency));
    }




    @Test
    void deleteById() throws Excep {
        agencyService.delete(savedAgency.getAgency_id());
        Mockito.verify(agencyRepository, Mockito.times(1)).deleteById(savedAgency.getAgency_id());

        assertThrows(Excep.class, () -> agencyService.delete(unsavedAgency.getAgency_id()));
    }
}
