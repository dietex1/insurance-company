package TJV.fediayar_tjv_semestral.converter;

import TJV.fediayar_tjv_semestral.Exception.Excep;
import TJV.fediayar_tjv_semestral.domain.Client;
import TJV.fediayar_tjv_semestral.domain.Insurance;
import TJV.fediayar_tjv_semestral.dto.ClientDto;
import TJV.fediayar_tjv_semestral.service.InsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

// eto kak @service.. toze komponent DI.
// ne srabotajet autowired.. (Error: No bean found for class ClientConverter)
@Component //considered as candidates for auto-detection when using annotation-based configuration and classpath scanning.
public class ClientConverter {


    private final InsuranceConverter insuranceConverter;

    private final InsuranceService insuranceService;

    public ClientConverter(InsuranceConverter insuranceConverter, InsuranceService insuranceService) {
        this.insuranceConverter = insuranceConverter;
        this.insuranceService = insuranceService;
    }

    public Client toDomain(ClientDto clientDto) throws Excep {
        Collection<Insurance> insurances = new ArrayList<>();
        for (Long insuranceid : clientDto.insurance_ids) {
            insurances.add(insuranceService.readOne(insuranceid));
        }
        return new Client(clientDto.client_id, clientDto.first_name, clientDto.last_name, clientDto.age, clientDto.income, clientDto.work_place, new HashSet<>(insurances));
    }


    public ClientDto fromDomain(Client client) {
        return new ClientDto(client.getClient_id(), client.getFirst_name(), client.getLast_name(), client.getAge(), client.getIncome(), client.getWork_place(), insuranceConverter.fromDomainToIdsMany(client.getInsurances()));
    }


    public Collection<ClientDto> fromDomainMany(Collection<Client> clients) {
        Collection<ClientDto> clientDtos = new ArrayList<>();
        clients.forEach((u) -> clientDtos.add(fromDomain(u)));
        return clientDtos;
    }
}

