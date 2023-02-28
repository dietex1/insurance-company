package TJV.fediayar_tjv_semestral.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Insurance {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "insurance_generator")
    @SequenceGenerator(name = "insurance_generator", sequenceName = "Insurance_seq", allocationSize = 1)

    private /*@Id @GeneratedValue*/ Long insurance_id;


    private LocalDate start_date;

    private LocalDate end_date;

    private Long price;

    private String type_of_insurance;

    @ManyToOne
    // @JoinColumn (name = "client_id")
    private Client client;

    @ManyToMany(mappedBy = "insurances", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Agency> agencies;


//@GeneratedValue( strategy = GenerationType.IDENTITY )


    public Insurance(Long insurance_id, LocalDate start_date, LocalDate end_date, Long price, String type_of_insurance, Client client, Set<Agency> agencies) {
        this.insurance_id = insurance_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.price = price;
        this.type_of_insurance = type_of_insurance;
        this.client = client;
        this.agencies = agencies;
    }

    public Insurance(Long insurance_id, LocalDate start_date, LocalDate end_date, Long price, String type_of_insurance) {
        this.insurance_id = insurance_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.price = price;
        this.type_of_insurance = type_of_insurance;
        agencies = new HashSet<>();
    }

    public Insurance() {

    }

    public Long getInsurance_id() {
        return insurance_id;
    }

    public void setInsurance_id(Long insurance_id) {
        this.insurance_id = insurance_id;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setAgencies(Set<Agency> agencies) {

        this.agencies = agencies;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Agency> getAgencies() {
        return agencies;
    }

    public Client getClient() {
        return client;
    }


    public String getType_of_insurance() {
        return type_of_insurance;
    }

    public void setType_of_insurance(String type_of_insurance) {
        this.type_of_insurance = type_of_insurance;
    }

    public void addAgency(Agency agency) {
        if (agencies.contains(agency))
            return;
        agencies.add(agency);
    }

    public void deleteAgency(Agency agency) {
        agencies.remove(agency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Insurance insurance = (Insurance) o;
        return Objects.equals(insurance_id, insurance.insurance_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(insurance_id);
    }

    @Override
    public String toString() {
        return "Insurance{" +
                "insurance_id=" + insurance_id +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", price=" + price +
                ", type_of_insurance='" + type_of_insurance + '\'' +
                ", client=" + client +
                ", agencies=" + agencies +
                '}';
    }
}
