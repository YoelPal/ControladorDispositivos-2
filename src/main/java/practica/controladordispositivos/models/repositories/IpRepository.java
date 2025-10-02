package practica.controladordispositivos.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import practica.controladordispositivos.models.entities.Ip;

import java.util.Optional;

public interface IpRepository extends JpaRepository<Ip,Long>, JpaSpecificationExecutor<Ip> {

    Optional<Ip> findByIpAddress(String ipAddress);
}
