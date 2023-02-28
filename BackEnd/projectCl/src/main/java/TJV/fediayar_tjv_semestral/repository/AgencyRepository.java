package TJV.fediayar_tjv_semestral.repository;

import TJV.fediayar_tjv_semestral.domain.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

public interface AgencyRepository extends JpaRepository<Agency, Long> {
    // Vypis všechny agency , kteří maji  neaktivní (expired) insurance.
    @Query("select distinct agency from Agency agency join agency.insurances insurances where " +
            "insurances.end_date <current_timestamp ")
    Collection<Agency> getWithExpiredInsurance();
}
