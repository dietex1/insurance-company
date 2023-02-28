package TJV.fediayar_tjv_semestral.controller;

import TJV.fediayar_tjv_semestral.Exception.Excep;
import TJV.fediayar_tjv_semestral.converter.InsuranceConverter;
import TJV.fediayar_tjv_semestral.domain.*;
import TJV.fediayar_tjv_semestral.dto.ClientDto;
import TJV.fediayar_tjv_semestral.dto.InsuranceDto;
import TJV.fediayar_tjv_semestral.service.AgencyService;
import TJV.fediayar_tjv_semestral.service.ClientService;
import TJV.fediayar_tjv_semestral.service.InsuranceService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;


@RestController
@RequestMapping("/insurance")
@CrossOrigin
public class InsuranceController {


    private final InsuranceService insuranceService;

    private final ClientService clientService;


    private final AgencyService agencyService;

    private final InsuranceConverter insuranceConverter;

    @Autowired
    public InsuranceController(InsuranceService insuranceService, ClientService clientService, AgencyService agencyService, InsuranceConverter insuranceConverter) {
        this.insuranceService = insuranceService;
        this.clientService = clientService;
        this.agencyService = agencyService;
        this.insuranceConverter = insuranceConverter;
    }

    @PostMapping
    public InsuranceDto createInsurance(@RequestBody InsuranceDto insuranceDto) throws Excep {


        Client client = clientService.readOne(insuranceDto.client);

        Collection<Agency> agencies = new ArrayList<>();
        for (Long agencyId : insuranceDto.getAgency_id()) {
            agencies.add(agencyService.readOne(agencyId));
            // System.out.println("[createInsurance]" + agencyId + agencyService.readOne(agencyId).getAgency_id());
        }

        Insurance returnedInsurance = insuranceService.create(new Insurance(Long.MAX_VALUE, insuranceDto.start_date, insuranceDto.end_date, insuranceDto.price, insuranceDto.type_of_insurance, client, new HashSet<>(agencies)));
        return insuranceConverter.fromDomain(returnedInsurance);
    }

    // @JsonView
    @GetMapping("/{insurance_id}")
    InsuranceDto readOne(@PathVariable Long insurance_id) throws Excep {
        Insurance insuranceFromDB = insuranceService.readOne(insurance_id);
        return insuranceConverter.fromDomain(insuranceFromDB);
    }

    //@JsonView
    @GetMapping
    Collection<InsuranceDto> readAll() {
        return insuranceConverter.fromDomainMany(insuranceService.readAll());
    }

    @DeleteMapping("/{id}")
    void deleteInsurance(@PathVariable Long id) throws Excep {
        insuranceService.delete(id);
    }


    @PutMapping("/{id}")
    InsuranceDto updateInsun(@RequestBody InsuranceDto insuranceDto, @PathVariable Long id) throws Excep {
        insuranceService.readOne(id);
        Insurance insuranceDomain = insuranceConverter.toDomain(insuranceDto);
        insuranceService.update(id, insuranceDomain);

        return insuranceDto;
    }


}