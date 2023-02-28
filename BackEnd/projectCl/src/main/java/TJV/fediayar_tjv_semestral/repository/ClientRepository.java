package TJV.fediayar_tjv_semestral.repository;

import TJV.fediayar_tjv_semestral.domain.Client;
import TJV.fediayar_tjv_semestral.dto.ClientDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
