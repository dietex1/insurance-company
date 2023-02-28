package TJV.fediayar_tjv_semestral.converter;


import TJV.fediayar_tjv_semestral.Exception.Excep;
import TJV.fediayar_tjv_semestral.domain.Agency;
import TJV.fediayar_tjv_semestral.domain.Client;
import TJV.fediayar_tjv_semestral.domain.Insurance;
import TJV.fediayar_tjv_semestral.dto.AgencyDto;
import TJV.fediayar_tjv_semestral.dto.ClientDto;
import TJV.fediayar_tjv_semestral.service.InsuranceService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;


@Component
public class AgencyConverter { // contains methods to convert domain objects (from Database) to DTO (= data transfer objects) for REST API

    private final InsuranceService insuranceService;

    private final InsuranceConverter insuranceConverter;

    public AgencyConverter(InsuranceService insuranceService, InsuranceConverter insuranceConverter) {
        this.insuranceService = insuranceService;
        this.insuranceConverter = insuranceConverter;
    }


    //z dto do database
    public Agency toDomain(AgencyDto agencyDto) throws Excep {
        Collection<Insurance> insurances = new ArrayList<>();

        for (Long insunId : agencyDto.getInsurance_ids()) {
            insurances.add(insuranceService.readOne(insunId));
        }
        return new Agency(agencyDto.agency_id, agencyDto.agency_name, agencyDto.city, new HashSet<>(insurances));
    }

    //z db do dto
    public AgencyDto fromDomain(Agency agency) {
        Collection<Long> insunId = new ArrayList<>();
        for (Insurance insurance : agency.getInsurances()) {
            insunId.add(insurance.getInsurance_id());
        }
        return new AgencyDto(agency.getAgency_id(), agency.getAgency_name(), agency.getCity(), insunId);
    }


    //pro kolekce
    public Collection<AgencyDto> fromDomainMany(Collection<Agency> agencies) {
        Collection<AgencyDto> agencyDtos = new ArrayList<>();
        agencies.forEach((u) -> agencyDtos.add(fromDomain(u)));
        return agencyDtos;
    }
}
