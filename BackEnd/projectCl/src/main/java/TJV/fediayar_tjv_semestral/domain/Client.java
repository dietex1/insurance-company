package TJV.fediayar_tjv_semestral.domain;


import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Entity //Specifies that the class is an entity
public class Client {


    @Id// mapping property to DB table Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_generator")
    @SequenceGenerator(name = "client_generator", sequenceName = "Client_seq", allocationSize = 1)

    private Long client_id;
    private String first_name;
    private String last_name;
    private Long age;
    private Long income;
    private String work_place;


    @OneToMany(mappedBy = "client")
    private Set<Insurance> insurances;


    public Client(Long client_id, String first_name, String last_name, Long age, Long income, String work_place, Set<Insurance> insurances) {
        this.client_id = client_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.income = income;
        this.work_place = work_place;
        this.insurances = insurances;
    }

    public Client() {

    }

    public Long getClient_id() {
        return client_id;
    }

    public void setClient_id(Long client_id) {
        this.client_id = client_id;
    }

    public Set<Insurance> getInsurances() {
        return insurances;
    }

    public void setInsurances(Set<Insurance> insurances) {
        this.insurances = insurances;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Long getIncome() {
        return income;
    }

    public void setIncome(Long income) {
        this.income = income;
    }

    public String getWork_place() {
        return work_place;
    }

    public void setWork_place(String work_place) {
        this.work_place = work_place;
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
        Client client = (Client) o;
        return Objects.equals(client_id, client.client_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client_id);
    }

    @Override
    public String toString() {
        return "Client{" +
                "client_id=" + client_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", age=" + age +
                ", income=" + income +
                ", work_place='" + work_place + '\'' +
                ", insurances=" + insurances +
                '}';
    }
}


