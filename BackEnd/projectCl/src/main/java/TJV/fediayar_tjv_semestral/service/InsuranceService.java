package TJV.fediayar_tjv_semestral.service;

import TJV.fediayar_tjv_semestral.Exception.Excep;
import TJV.fediayar_tjv_semestral.domain.Insurance;
import TJV.fediayar_tjv_semestral.repository.InsuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;


@Service // registrace tridy jako Service komponenty (Bean) pro DI kontejner // error no beans found
public class InsuranceService {


    private final InsuranceRepository insuranceRepository;

    @Autowired // injection by constructor (injektaz zavislosti pomoci konstruktoru )
    public InsuranceService(InsuranceRepository insuranceRepository) {
        this.insuranceRepository = insuranceRepository;
    }

    public Insurance create(Insurance insurance) throws Excep {
        if (insurance.getStart_date() == null || insurance.getEnd_date() == null || insurance.getType_of_insurance() == null || insurance.getPrice() == null)
            throw new Excep("Fill all fields");
        if (insuranceRepository.existsById(insurance.getInsurance_id()))
            throw new Excep("Insurance already exists");
        return insuranceRepository.save(insurance);
    }

    public Insurance readOne(Long id) throws Excep {
        if (insuranceRepository.findById(id).isEmpty())
            throw new Excep("Insurance with this id wasn't found");
        return insuranceRepository.findById(id).get();
    }

    public Collection<Insurance> readAll() {
        return (Collection<Insurance>) insuranceRepository.findAll();
    }

    public Insurance update(Long id, Insurance insurance) throws Excep {
        if (insuranceRepository.findById(id).isEmpty())
            throw new Excep("Insurance wasn't found");
        Insurance insuranceModify = insuranceRepository.findById(id).get();

        if (insurance.getStart_date() != null)
            insuranceModify.setStart_date(insurance.getStart_date());
        else
            throw new Excep("You should fill all fields");

        if (insurance.getEnd_date() != null)
            insuranceModify.setEnd_date(insurance.getEnd_date());
        else
            throw new Excep("You should fill all fields");

        if (insurance.getType_of_insurance() == null)
            throw new Excep("You should fill all fields");
        else
            insuranceModify.setType_of_insurance(insurance.getType_of_insurance());

        if (insurance.getPrice() == null)
            throw new Excep("You should fill all fields");
        else
            insuranceModify.setPrice(insurance.getPrice());
        if (insurance.getAgencies() == null)
            throw new Excep("You should fill all fields");
        else
            insuranceModify.setAgencies(insurance.getAgencies());

        return insuranceRepository.save(insuranceModify);
    }

    public String delete(Long id) throws Excep {

        if (insuranceRepository.findById(id).isEmpty())
            throw new Excep("Insurance with this id wasn't found");

        insuranceRepository.deleteById(id);

        return "Insurance with id: " + id.toString() + " was  deleted";

    }

}