package TJV.fediayar_tjv_semestral.dto;

import TJV.fediayar_tjv_semestral.domain.Agency;
import TJV.fediayar_tjv_semestral.domain.Client;
import com.fasterxml.jackson.annotation.JsonView;


import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public class InsuranceDto {

    // otherwise not being included into JSON response
    @JsonView //Annotation used for indicating view(s) that the property that is defined by method or field annotated is part of JSON response
    public Long insurance_id;


    @JsonView
    public LocalDate start_date;

    @JsonView
    public LocalDate end_date;

    @JsonView
    public Long price;

    @JsonView
    public String type_of_insurance;

    @JsonView
    public Long client;

    @JsonView
    public Collection<Long> agency_id;

    public InsuranceDto() {
    }

    public InsuranceDto(Long insurance_id, LocalDate start_date, LocalDate end_date, Long price, String type_of_insurance, Long client, Collection<Long> agency_id) {
        this.insurance_id = insurance_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.price = price;
        this.type_of_insurance = type_of_insurance;
        this.client = client;
        this.agency_id = agency_id;
    }

    public Long getInsurance_id() {
        return insurance_id;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public Long getPrice() {
        return price;
    }

    public String getType_of_insurance() {
        return type_of_insurance;
    }

    public Long getClient() {
        return client;
    }

    public Collection<Long> getAgency_id() {
        return agency_id;
    }

    public void setInsurance_id(Long insurance_id) {
        this.insurance_id = insurance_id;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setType_of_insurance(String type_of_insurance) {
        this.type_of_insurance = type_of_insurance;
    }

    public void setClient(Long client) {
        this.client = client;
    }

    public void setAgency_id(Collection<Long> agencies) {
        this.agency_id = agencies;
    }
}
