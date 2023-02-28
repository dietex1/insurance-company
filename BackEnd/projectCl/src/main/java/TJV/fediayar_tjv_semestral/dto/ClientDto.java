package TJV.fediayar_tjv_semestral.dto;

import TJV.fediayar_tjv_semestral.domain.Insurance;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.Collection;
import java.util.List;

public class ClientDto {

    // otherwise not being included into JSON response
    @JsonView//Annotation used for indicating view(s) that the property that is defined by method or field annotated is part of JSON response
    public Long client_id;

    @JsonView
    public String first_name;
    @JsonView
    public String last_name;
    @JsonView
    public Long age;
    @JsonView
    public Long income;
    @JsonView
    public String work_place;

    @JsonView
    public Collection<Long> insurance_ids;

    public ClientDto() {
    }

    public ClientDto(Long client_id, String first_name, String last_name, Long age, Long income, String work_place, Collection<Long> insurance_ids) {
        this.client_id = client_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.income = income;
        this.work_place = work_place;
        this.insurance_ids = insurance_ids;
    }

    public Long getClient_id() {
        return client_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public Long getAge() {
        return age;
    }

    public Long getIncome() {
        return income;
    }

    public String getWork_place() {
        return work_place;
    }

    public Collection<Long> getInsurance_ids() {
        return insurance_ids;
    }

    public void setClient_id(Long client_id) {
        this.client_id = client_id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public void setIncome(Long income) {
        this.income = income;
    }

    public void setWork_place(String work_place) {
        this.work_place = work_place;
    }

    public void setInsurancy_id(Collection<Long> insurance_ids) {
        this.insurance_ids = insurance_ids;
    }
}
