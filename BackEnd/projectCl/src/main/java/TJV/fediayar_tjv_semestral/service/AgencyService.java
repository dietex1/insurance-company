package TJV.fediayar_tjv_semestral.service;


import TJV.fediayar_tjv_semestral.domain.Agency;
import TJV.fediayar_tjv_semestral.repository.AgencyRepository;
import org.springframework.stereotype.Service;
import TJV.fediayar_tjv_semestral.Exception.Excep;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Collection;


@Service
public class AgencyService {


    private final AgencyRepository agencyRepository;

    @Autowired
    public AgencyService(AgencyRepository agencyRepository) {
        this.agencyRepository = agencyRepository;
    }

    public Agency create(Agency agency) throws Excep {
        if (agency.getAgency_name() == null || agency.getCity() == null)
            throw new Excep("Fill all fields");
        if (agencyRepository.existsById(agency.getAgency_id()))
            throw new Excep("Agency already exists");
        return agencyRepository.save(agency);
    }

    public Agency readOne(Long id) throws Excep {
        if (agencyRepository.findById(id).isEmpty())
            throw new Excep("Agency with this id wasn't found");
        return agencyRepository.findById(id).get();
    }

    public Collection<Agency> readAll() {
        return (Collection<Agency>) agencyRepository.findAll();
    }

    public Agency update(Long id, Agency agency) throws Excep {
        if (agencyRepository.findById(id).isEmpty())
            throw new Excep("Agency wasn't found");
        Agency agencyModify = agencyRepository.findById(id).get();

        if (agency.getAgency_name() != null)
            agencyModify.setAgency_name(agency.getAgency_name());
        else
            throw new Excep("You should fill all fields");

        if (agency.getCity() != null)
            agencyModify.setCity(agency.getCity());
        else
            throw new Excep("You should fill all fields");

        if (agency.getInsurances() != null)
            agencyModify.setInsurances(agency.getInsurances());
        else
            throw new Excep("You should fill all fields");

        return agencyRepository.save(agencyModify);
    }

    public String delete(Long id) throws Excep {

        if (agencyRepository.findById(id).isEmpty())
            throw new Excep("Agency with this id wasn't found");

        agencyRepository.deleteById(id);

        return "Agency with id: " + id.toString() + " was  deleted";

    }

    public Collection<Agency> readAllWithExpiredInsurance() {
        return agencyRepository.getWithExpiredInsurance();
    }

}