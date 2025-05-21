package practica.ControladorDispositivos.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import practica.ControladorDispositivos.models.entities.Ip;

public interface IpRepository extends JpaRepository<Ip,Long>, JpaSpecificationExecutor<Ip> {
}
