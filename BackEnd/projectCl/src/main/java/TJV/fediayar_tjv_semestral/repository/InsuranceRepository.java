package TJV.fediayar_tjv_semestral.repository;

import TJV.fediayar_tjv_semestral.domain.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {


}
