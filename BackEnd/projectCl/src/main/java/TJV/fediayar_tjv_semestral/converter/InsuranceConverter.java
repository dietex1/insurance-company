package TJV.fediayar_tjv_semestral.converter;

import TJV.fediayar_tjv_semestral.Exception.Excep;
import TJV.fediayar_tjv_semestral.domain.Agency;
import TJV.fediayar_tjv_semestral.domain.Insurance;
import TJV.fediayar_tjv_semestral.dto.InsuranceDto;
import TJV.fediayar_tjv_semestral.service.AgencyService;
import TJV.fediayar_tjv_semestral.service.ClientService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Component
public class InsuranceConverter {

    private final AgencyService agencyService;

    private final ClientService clientService;


    public InsuranceConverter(AgencyService agencyService, ClientService clientService) {
        this.agencyService = agencyService;
        this.clientService = clientService;
    }

    public Insurance toDomain(InsuranceDto insuranceDto) throws Excep {
        Collection<Agency> agencies = new ArrayList<>();
        for (Long agencyId : insuranceDto.getAgency_id()) {
            agencies.add(agencyService.readOne(agencyId));
        }
        return new Insurance(insuranceDto.insurance_id, insuranceDto.start_date, insuranceDto.end_date, insuranceDto.price, insuranceDto.type_of_insurance, clientService.readOne(insuranceDto.client), new HashSet<>(agencies));
    }


    public InsuranceDto fromDomain(Insurance insurance) {
        Collection<Long> agencies = new ArrayList<>();
        for (Agency agency : insurance.getAgencies()) {
            agencies.add(agency.getAgency_id());
        }
        return new InsuranceDto(insurance.getInsurance_id(), insurance.getStart_date(), insurance.getEnd_date(), insurance.getPrice(), insurance.getType_of_insurance(), insurance.getClient().getClient_id(), agencies);
    }

    public Collection<Long> fromDomainToIdsMany(Collection<Insurance> insurances) {
        Collection<Long> insunId = new ArrayList<>();
        for (Insurance insurance : insurances) {
            insunId.add(insurance.getInsurance_id());
        }
        return insunId;
    }


    public Collection<InsuranceDto> fromDomainMany(Collection<Insurance> insurances) {
        Collection<InsuranceDto> insuranceDtos = new ArrayList<>();
        insurances.forEach((u) -> insuranceDtos.add(fromDomain(u)));
        return insuranceDtos;
    }
}
