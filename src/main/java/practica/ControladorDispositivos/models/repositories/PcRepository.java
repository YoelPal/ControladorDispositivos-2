package practica.ControladorDispositivos.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practica.ControladorDispositivos.models.entities.Pc;

@Repository
public interface PcRepository extends JpaRepository<Pc,String> {
}
