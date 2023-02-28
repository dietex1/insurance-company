package TJV.fediayar_tjv_semestral.domain;


import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Agency {


    @Id // mapping property to DB table Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agency_generator")
    @SequenceGenerator(name = "agency_generator", sequenceName = "Agency_seq", allocationSize = 1)

    private /*@Id @GeneratedValue*/ Long agency_id;


    private String agency_name;

    private String city;


    // cascade REMOVE  для удаліня
    //cascade persist для сильноі связі
    //EAGER data z insurance se nactou spolu s Agency s repozitory (vzdy rovnou pri dotazu na Agency)
    //LAZY data z insurance se nenactou hned (ale pouze pri vyvolani dane property jako agency.insurances)
    //kdyz neni automat lazy vrati prazdy array
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "insurance_z_agence", joinColumns = @JoinColumn(name = "agency_id"),
            inverseJoinColumns = @JoinColumn(name = "insurance_id"))
    private Set<Insurance> insurances;


    public Agency(Long agency_id, String agency_name, String city, Set<Insurance> insurances) {
        this.agency_id = agency_id;
        this.agency_name = agency_name;
        this.city = city;
        this.insurances = insurances;
    }

    public Agency() {

    }

    public Long getAgency_id() {
        return agency_id;
    }

    public void setAgency_id(Long agency_id) {
        this.agency_id = agency_id;
    }

    public String getAgency_name() {
        return agency_name;
    }

    public void setAgency_name(String agency_name) {
        this.agency_name = agency_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Set<Insurance> getInsurances() {
        return insurances;
    }

    public void setInsurances(Set<Insurance> insurances) {
        this.insurances = insurances;
    }

    public void addInsurance(Insurance insurance) {
        if (insurances.contains(insurance))
            return;
        insurances.add(insurance);
    }

    public void deleteInsurance(Insurance insurance) {
        insurances.remove(insurance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agency agency = (Agency) o;
        return Objects.equals(agency_id, agency.agency_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(agency_id);
    }

    @Override
    public String toString() {
        return "Agency{" +
                "agency_id=" + agency_id +
                ", agency_name='" + agency_name + '\'' +
                ", city='" + city + '\'' +
                ", insurances=" + insurances +
                '}';
    }
}
