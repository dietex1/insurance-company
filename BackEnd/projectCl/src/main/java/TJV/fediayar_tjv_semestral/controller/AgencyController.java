package TJV.fediayar_tjv_semestral.controller;


import TJV.fediayar_tjv_semestral.Exception.Excep;
import TJV.fediayar_tjv_semestral.converter.AgencyConverter;
import TJV.fediayar_tjv_semestral.domain.Agency;
import TJV.fediayar_tjv_semestral.dto.AgencyDto;
import TJV.fediayar_tjv_semestral.service.AgencyService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashSet;

@RestController// Registers endpoints for REST API ... cant define mapping outside @RestController
@RequestMapping("/agencies")// "/" by default
@CrossOrigin// Disable CORS (cross origin resource sharing) == chrome error on fetch from client (react)


public class AgencyController {
    private final AgencyService agencyService;
    private final AgencyConverter agencyConverter;

    //autowired иньекция зависимости  1 через поле через сетер или конструктор , менять поле ток в конструкторе  а в филде непраивльно нужно заново делать сборку
    //бинс это компоненты в которых удерживает обьект даного класа и даную компоненту за жихненим циклом переменный следит депенденси инжекшон контейнер котррых предоставляет спринг бут
    @Autowired
    public AgencyController(AgencyService agencyService, AgencyConverter agencyConverter) {
        this.agencyService = agencyService;
        this.agencyConverter = agencyConverter;
    }

    @PostMapping
    AgencyDto createAgency(@RequestBody AgencyDto agencyDto) throws Excep {
        Agency agencyDomain = agencyConverter.toDomain(agencyDto);

        agencyDomain.setAgency_id(Long.MAX_VALUE);
        Agency agency = agencyService.create(agencyDomain);
        return agencyConverter.fromDomain(agency);
    }

    @GetMapping("/{id}")
    AgencyDto readOne(@PathVariable Long id) throws Excep {
        Agency agencyFromDB = agencyService.readOne(id);
        return agencyConverter.fromDomain(agencyFromDB);
    }

    @GetMapping
    Collection<AgencyDto> readAll() {
        return agencyConverter.fromDomainMany(agencyService.readAll());
    }

    @PutMapping("/{id}")
    AgencyDto updateAgency(@RequestBody AgencyDto agencyDto, @PathVariable Long id) throws Excep {
        agencyService.readOne(id);
        Agency agencyDomain = agencyConverter.toDomain(agencyDto);
        agencyService.update(id, agencyDomain);

        return agencyDto;
    }

    @DeleteMapping("/{id}")
    void deleteAgency(@PathVariable Long id) throws Excep {
        agencyService.delete(id);
    }

    @GetMapping("/expired")
    Collection<AgencyDto> readAllWithExpiredInsurance() {
        return agencyConverter.fromDomainMany(agencyService.readAllWithExpiredInsurance());
    }

}
