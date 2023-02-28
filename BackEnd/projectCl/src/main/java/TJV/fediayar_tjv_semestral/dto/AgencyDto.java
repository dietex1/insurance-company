package TJV.fediayar_tjv_semestral.dto;

import TJV.fediayar_tjv_semestral.domain.Insurance;
import com.fasterxml.jackson.annotation.JsonView;


import java.util.Collection;
import java.util.List;

public class AgencyDto {

    @JsonView
    public Long agency_id;

    @JsonView
    public String agency_name;

    @JsonView
    public String city;

    @JsonView
    public Collection<Long> insurance_ids;
    //List

    //private List<InsuranceDto> insurances;


    public AgencyDto() {
    }

    public AgencyDto(Long agency_id, String agency_name, String city, Collection<Long> insurance_ids) {
        this.agency_id = agency_id;
        this.agency_name = agency_name;
        this.city = city;
        this.insurance_ids = insurance_ids;
    }

    public Long getAgency_id() {
        return agency_id;
    }

    public String getAgency_name() {
        return agency_name;
    }

    public String getCity() {
        return city;
    }

    public Collection<Long> getInsurance_ids() {
        return insurance_ids;
    }

    public void setAgency_id(Long agency_id) {
        this.agency_id = agency_id;
    }

    public void setAgency_name(String agency_name) {
        this.agency_name = agency_name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setInsurance_ids(Collection<Long> insurance_ids) {
        this.insurance_ids = insurance_ids;
    }

    @Override
    public String toString() {
        return "AgencyDto{" +
                "agency_id=" + agency_id +
                ", agency_name='" + agency_name + '\'' +
                ", city='" + city + '\'' +
                ", insurance_ids=" + insurance_ids +
                '}';
    }
}
